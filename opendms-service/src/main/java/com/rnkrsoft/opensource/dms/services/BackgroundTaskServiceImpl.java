package com.rnkrsoft.opensource.dms.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rnkrsoft.message.MessageFormatter;
import com.rnkrsoft.opensource.dms.enums.DataSourceTypeEnum;
import com.rnkrsoft.opensource.dms.enums.FileFormatEnum;
import com.rnkrsoft.opensource.dms.enums.TaskStatusEnum;
import com.rnkrsoft.opensource.dms.jdbc.dao.DataSourceDAO;
import com.rnkrsoft.opensource.dms.jdbc.dao.TaskDAO;
import com.rnkrsoft.opensource.dms.jdbc.dao.TemplateDAO;
import com.rnkrsoft.opensource.dms.jdbc.entity.DataSourceEntity;
import com.rnkrsoft.opensource.dms.jdbc.entity.TaskEntity;
import com.rnkrsoft.opensource.dms.jdbc.entity.TemplateEntity;
import com.rnkrsoft.opensource.orm.executor.JdbcQueryHandler;
import com.rnkrsoft.opensource.orm.metadata.Pagination;
import com.rnkrsoft.skeleton4j.spring.SpringContextHelper;
import com.rnkrsoft.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.web.data.sql.Row;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by rnkrsoft.com on 2019/7/1.
 */
@Service
@Slf4j
public class BackgroundTaskServiceImpl implements BackgroundTaskService, InitializingBean {
    final Map<Integer, Thread> tasks = new HashMap();
    ExecutorService threadPool;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    TemplateDAO templateDAO;
    @Autowired
    DataSourceDAO dataSourceDAO;

    static Gson GSON = new GsonBuilder().serializeNulls().create();

    @Override
    public void init() {
        this.threadPool = Executors.newFixedThreadPool(10);
    }

    @Override
    public void shutdown() {
        this.threadPool.shutdown();
    }

    @Override
    public void newTask(Integer taskId) {
        if (threadPool.isTerminated() || threadPool.isShutdown()) {
            return;
        }
        log.info("开始启动任务" + taskId);
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                Thread thread = Thread.currentThread();

                try {
                    register(taskId, thread);
                    generate(taskId);
                } finally {
                    remove(taskId);
                }
            }
        });
    }

    @Override
    public void cancel(Integer taskId) {
        try {
            Thread thread = tasks.get(taskId);
            thread.interrupt();
            tasks.remove(taskId);
        } catch (Exception e) {
            log.error("取消任务发生异常", e);
        }
    }

    @Override
    public void register(Integer taskId, Thread thread) {
        tasks.put(taskId, thread);
    }

    @Override
    public void remove(Integer taskId) {

    }

    @Override
    public void generate(Integer taskId) {
        TaskEntity taskEntity = taskDAO.selectByPrimaryKey(taskId);
        if (taskEntity == null) {
            return;
        }
        FileFormatEnum fileFormat = FileFormatEnum.valueOfCode(taskEntity.getFileFormat());
        TemplateEntity templateEntity = templateDAO.selectByPrimaryKey(taskEntity.getTemplateId());
        if (templateEntity == null) {
            return;
        }
        DataSourceEntity dataSourceEntity = dataSourceDAO.selectByPrimaryKey(templateEntity.getDataSourceId());
        if (dataSourceEntity == null) {
            return;
        }

        int maxRowSize = templateEntity.getMaxRowSize();
        DataSourceTypeEnum dataSourceType = DataSourceTypeEnum.valueOfCode(dataSourceEntity.getDataSourceType());
        //数据源名称
        String dataSourceName = dataSourceEntity.getDataSourceName();
        //数据库名
        String schema = dataSourceEntity.getSchemaName();
        //用户号
        String username = dataSourceEntity.getUsername();
        //密码
        String password = dataSourceEntity.getPassword();
        //地址
        String host = dataSourceEntity.getHost();
        //端口
        int port = dataSourceEntity.getPort();
        //驱动类
        String driverClassName = dataSourceEntity.getDriverClassName();
        //SQL 含有?占位符
        String sql = taskEntity.getSqlStatement();
        //参数数组
        List<Object> args = GSON.fromJson(taskEntity.getTaskArguments(), List.class);
        Connection connection = null;
        try {
            MDC.put("sessionId", Integer.toString(taskId));
            log.info("use datasource [{}]", dataSourceName);
            String url = MessageFormatter.format("jdbc:{}://{}:{}/{}", dataSourceType.getJdbc(), host, port, schema);
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, username, password);
            JdbcQueryHandler queryHandler = new JdbcQueryHandler(connection);
            int total = queryHandler.total(sql, args);
            if (total > maxRowSize) {
                log.warn("当前执行的SQL语句导出的数据行数'{}',超过最高上限'{}'，将只按照上限值'{}'导出", total, maxRowSize, maxRowSize);
                total = maxRowSize;
            }
            GenerateFileService generateFileService = SpringContextHelper.getBean(fileFormat.getSuffix() + ":" + GenerateFileService.class.getSimpleName());
            int pageSize = 100;
            int pageNum = Pagination.pageNum(total, pageSize);
            File fileName = new File(new File("./target/", DateUtils.getCurrDate()), taskId + "." + fileFormat.getSuffix());
            taskDAO.updateByPrimaryKeySelective(TaskEntity.builder()
                    .taskId(taskId)
                    .taskStatus(TaskStatusEnum.EXECUTING.getCode())
                    .generateFileName(fileName.getCanonicalPath())
                    .build());
            log.info("begin execute task [{}]", taskEntity.getTaskName());
            final int total_final = total;
            final String fileName_final = fileName.getCanonicalPath();
            //生成文件,将结果写入文件格式
            generateFileService.generate(fileName.getCanonicalPath(), pageNum, pageSize, new SegmentalCallback() {
                @Override
                public Pagination<Row> call(int pageIndex) {
                    TaskEntity taskEntity = taskDAO.selectByPrimaryKey(taskId);
                    if (taskEntity.getTaskStatus() == TaskStatusEnum.CANCELING.getCode()) {
                        TaskEntity updateEntity = TaskEntity.builder()
                                .taskId(taskId)
                                .taskStatus(TaskStatusEnum.FAILURE.getCode())
                                .failureCause("用户主动取消任务执行")
                                .build();
                        taskDAO.updateByPrimaryKeySelective(updateEntity);
                        return null;
                    }
                    int curPageNo = Pagination.curPageNo(total_final, pageSize, pageIndex + 1);
                    log.debug("query page no '{}'", curPageNo);
                    Pagination<Row> pagination = new Pagination<Row>(pageSize, curPageNo);
                    pagination.setTotal(total_final);
                    try {
                        queryHandler.records(sql, args, pagination);
                        int progress = (100 * (pageIndex + 1) / pageNum);
                        TaskEntity updateEntity = TaskEntity.builder().taskId(taskId).taskProgress(progress).build();
                        if (progress == 100) {
                            updateEntity.setTaskStatus(TaskStatusEnum.FINISH.getCode());
                            updateEntity.setFinishDate(DateUtils.getCurrFullTime());
                        }
                        taskDAO.updateByPrimaryKeySelective(updateEntity);
                        log.info("generate file '{}' progress {}", fileName_final, progress);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return pagination;
                }
            });

            //如果一页数据也没有，则也更新为成功
            if (pageNum == 0) {
                TaskEntity updateEntity = TaskEntity.builder().taskId(taskId).taskProgress(100).build();
                updateEntity.setTaskStatus(TaskStatusEnum.FINISH.getCode());
                updateEntity.setFinishDate(DateUtils.getCurrFullTime());
                taskDAO.updateByPrimaryKeySelective(updateEntity);
                log.info("generate file '{}' progress {}", fileName.getCanonicalPath(), 100);
            }
            log.info("finish execute task [{}]", taskEntity.getTaskName());

        } catch (Exception e) {
            log.error("execute task happens error!", e);
            TaskEntity updateEntity = TaskEntity.builder()
                    .taskId(taskId)
                    .taskStatus(TaskStatusEnum.FAILURE.getCode())
                    .failureCause(e.getMessage())
                    .build();
            taskDAO.updateByPrimaryKeySelective(updateEntity);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    //
                }
            }
            MDC.put("sessionId", "");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
