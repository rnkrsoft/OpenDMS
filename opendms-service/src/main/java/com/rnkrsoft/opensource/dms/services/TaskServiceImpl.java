package com.rnkrsoft.opensource.dms.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rnkrsoft.framework.orm.Pagination;
import com.rnkrsoft.message.MessageFormatter;
import com.rnkrsoft.message.VariableMessageFormatter;
import com.rnkrsoft.opensource.dms.config.DmsConfig;
import com.rnkrsoft.opensource.dms.enums.FileFormatEnum;
import com.rnkrsoft.opensource.dms.enums.TaskStatusEnum;
import com.rnkrsoft.opensource.dms.jdbc.bo.QueryTaskBO;
import com.rnkrsoft.opensource.dms.jdbc.dao.TaskDAO;
import com.rnkrsoft.opensource.dms.jdbc.dao.TemplateDAO;
import com.rnkrsoft.opensource.dms.jdbc.entity.TaskEntity;
import com.rnkrsoft.opensource.dms.jdbc.entity.TemplateEntity;
import com.rnkrsoft.opensource.dms.utils.CodeUtils;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.domains.*;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.services.TaskService;
import com.rnkrsoft.time.DateStyle;
import com.rnkrsoft.utils.DateUtils;
import com.rnkrsoft.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.web.doc.enums.Skeleton4jRspCode;
import java.util.*;

/**
 * Created by rnkrsoft.com on 2019/6/30.
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    TemplateDAO templateDAO;
    @Autowired
    BackgroundTaskService backgroundTaskService;
    @Autowired
    DmsConfig dmsConfig;

    static Gson GSON = new GsonBuilder().serializeNulls().create();

    @Override
    public ViewTaskResponse view(ViewTaskRequest request) {
        ViewTaskResponse response = new ViewTaskResponse();
        TaskEntity taskEntity = taskDAO.selectByPrimaryKey(request.getTaskId());
        if (taskEntity == null) {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            response.setRspDesc("不存在记录");
            return response;
        }
        response.setTaskId(taskEntity.getTaskId());
        response.setTaskName(taskEntity.getTaskName());
        response.setSqlStatement(taskEntity.getSqlStatement());
        response.setProgress(taskEntity.getTaskProgress());
        response.setCreateUser(taskEntity.getCreateUserName() + "(" + taskEntity.getCreateUserId() + ")");
        response.setFinishDate(taskEntity.getFinishDate());
        response.setLatestDownloadDate(DateUtils.toString(taskEntity.getLatestDownloadDate(), DateStyle.SECOND_FORMAT2));
        response.setAllowDownloadCount(taskEntity.getAllowDownloadCount());
        response.setCreateDate(DateUtils.toString(taskEntity.getCreateDate(), DateStyle.SECOND_FORMAT2));
        response.setLastUpdateDate(DateUtils.toString(taskEntity.getLastUpdateDate(), DateStyle.SECOND_FORMAT2));
        return response;
    }

    @Override
    public QueryTaskResponse query(QueryTaskRequest request) {
        QueryTaskResponse response = new QueryTaskResponse();
        response.setPageNo(request.getPageNo());
        response.setPageSize(request.getPageSize());
        Pagination<QueryTaskBO> pagination = new Pagination<QueryTaskBO>(request.getPageSize(), request.getPageNo());
        pagination.getParams().put("taskId", request.getTaskId());
        pagination.getParams().put("templateId", request.getTemplateId());
        if (StringUtils.isNotBlank(request.getFinishDate())) {
            pagination.getParams().put("finishDate", request.getFinishDate());
        }
        if (StringUtils.isNotBlank(request.getBeginCreateDate())) {
            pagination.getParams().put("beginCreateDate", request.getBeginCreateDate());
        }
        if (StringUtils.isNotBlank(request.getEndCreateDate())) {
            pagination.getParams().put("endCreateDate", request.getEndCreateDate());
        }
        pagination.getParams().put("taskStatus", request.getTaskStatus());
        taskDAO.selectTask(pagination);
        response.setPageNum(pagination.getPageNum());
        response.setTotal(pagination.getTotal());
        for (QueryTaskBO record : pagination.getRecords()) {
            response.addRecord(QueryTaskRecord.builder()
                            .taskId(record.getTaskId())
                            .taskName(record.getTaskName())
                            .status(record.getTaskStatus())
                            .sqlStatement(record.getSqlStatement())
                            .progress(record.getTaskProgress())
                            .failureCause(record.getFailureCause())
                            .createUser(record.getCreateUserName() + "【" + record.getCreateUserId() + "】")
                            .createDate(DateUtils.toString(record.getCreateDate(), DateStyle.SECOND_FORMAT2))
                            .finishDate(record.getFinishDate())
                            .lastUpdateDate(DateUtils.toString(record.getLastUpdateDate(), DateStyle.SECOND_FORMAT2))
                            .build()
            );
        }
        return response;
    }

    @Override
    public ExecuteTaskResponse execute(ExecuteTaskRequest request) {
        ExecuteTaskResponse response = new ExecuteTaskResponse();
        TaskEntity taskEntity = taskDAO.selectByPrimaryKey(request.getTaskId());
        if (taskEntity == null) {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            response.setRspDesc("不存在记录");
            return response;
        }
        TaskStatusEnum taskStatus = TaskStatusEnum.valueOfCode(taskEntity.getTaskStatus());
        if (taskStatus == TaskStatusEnum.INIT) {
            backgroundTaskService.newTask(request.getTaskId());
            return response;
        } else {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            response.setRspDesc("任务状态不为初始化状态，不能执行");
            return response;
        }
    }

    @Override
    public DownloadTaskResponse download(DownloadTaskRequest request) {
        DownloadTaskResponse response = new DownloadTaskResponse();
        TaskEntity taskEntity = taskDAO.selectByPrimaryKey(request.getTaskId());
        if (taskEntity == null) {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            response.setRspDesc("不存在记录");
            return response;
        }
        response.setShareUrl(MessageFormatter.format("{}/share?id={}&code={}", dmsConfig.getDomain(), request.getTaskId(), taskEntity.getShareCode()));
        return response;
    }

    @Override
    public ToCreateTaskResponse toCreate(ToCreateTaskRequest request) {
        ToCreateTaskResponse response = new ToCreateTaskResponse();
        TemplateEntity templateEntity = templateDAO.selectByPrimaryKey(request.getTemplateId());
        if (templateEntity == null) {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            return response;
        }
        String sqlStatement = templateEntity.getSqlStatement();
        VariableMessageFormatter.Expression expression = VariableMessageFormatter.parse(sqlStatement, false, VariableMessageFormatter.DELIMITER_PLACEHOLDER_BEGIN_STYLE1, VariableMessageFormatter.DELIMITER_PLACEHOLDER_END_STYLE1);
        for (VariableMessageFormatter.Token token : expression.getTokens()) {
            if (token.getTokenType() == VariableMessageFormatter.TokenType.TEXT) {
                continue;
            } else if (token.isFunction()) {
                continue;
            }
            ToCreateTaskRecord record = ToCreateTaskRecord.builder()
                    .argName(token.getTokenName())
                    .argFormat(token.getFormat())
                    .build();
            response.getArguments().add(record);
        }
        response.setFileFormat(FileFormatEnum.CSV.getCode());
        response.setSqlStatement(templateEntity.getSqlStatement());
        response.setTemplateId(templateEntity.getTemplateId());
        return response;
    }

    @Override
    public CreateTaskResponse create(CreateTaskRequest request) {
        CreateTaskResponse response = new CreateTaskResponse();
        TemplateEntity templateEntity = templateDAO.selectByPrimaryKey(request.getTemplateId());
        if (templateEntity == null) {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            return response;
        }
        String sqlStatement = templateEntity.getSqlStatement();
        List<Object> args = new ArrayList();
        Map<String, Object> params = new HashMap();
        for (CreateTaskRecord record : request.getArguments()) {
            params.put(record.getArgName(), record.getArgValue());
            args.add(record.getArgValue());
        }
        String taskArguments = GSON.toJson(args);
        String sql = VariableMessageFormatter.format(sqlStatement, true, false, VariableMessageFormatter.DELIMITER_PLACEHOLDER_BEGIN_STYLE1, VariableMessageFormatter.DELIMITER_PLACEHOLDER_END_STYLE1, true, params);
        taskDAO.insertSelective(TaskEntity.builder()
                        .taskName(request.getLoginUserName() + "的【" + templateEntity.getTemplateName() + "】")
                        .templateId(templateEntity.getTemplateId())
                        .fileFormat(request.getFileFormat())
                        .templateSqlStatement(sqlStatement)
                        .taskArguments(taskArguments)
                        .taskProgress(0)
                        .sqlStatement(sql)
                        .createUserId(request.getLoginUserId())
                        .createUserName(request.getLoginUserName())
                        .taskStatus(TaskStatusEnum.INIT.getCode())
                        .shareCode(CodeUtils.generateCode(6, true))
                                //有效期24小时以内
                        .latestDownloadDate(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                                //可下载次数
                        .allowDownloadCount(4)
                        .build()
        );
        return response;
    }

    @Override
    public DeleteTaskResponse delete(DeleteTaskRequest request) {
        DeleteTaskResponse response = new DeleteTaskResponse();
        TaskEntity taskEntity = taskDAO.selectByPrimaryKey(request.getTaskId());
        if (taskEntity == null) {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            response.setRspDesc("不存在记录");
            return response;
        }
        TaskStatusEnum taskStatus = TaskStatusEnum.valueOfCode(taskEntity.getTaskStatus());
        if (taskStatus == TaskStatusEnum.INIT) {
            taskDAO.updateByPrimaryKeySelective(TaskEntity.builder().taskId(taskEntity.getTaskId()).taskStatus(TaskStatusEnum.DELETED.getCode()).build());
            return response;
        }
        return response;
    }

    @Override
    public CancelTaskResponse cancel(CancelTaskRequest request) {
        CancelTaskResponse response = new CancelTaskResponse();
        TaskEntity taskEntity = taskDAO.selectByPrimaryKey(request.getTaskId());
        if (taskEntity == null) {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            response.setRspDesc("不存在记录");
            return response;
        }
        TaskStatusEnum taskStatus = TaskStatusEnum.valueOfCode(taskEntity.getTaskStatus());
        if (taskStatus == TaskStatusEnum.EXECUTING) {
            taskDAO.updateByPrimaryKeySelective(TaskEntity.builder().taskId(taskEntity.getTaskId()).taskStatus(TaskStatusEnum.CANCELING.getCode()).build());
            return response;
        }
        return response;
    }

    @Override
    public ForceKillTaskResponse forceKill(ForceKillTaskRequest request) {
        ForceKillTaskResponse response = new ForceKillTaskResponse();
        TaskEntity taskEntity = taskDAO.selectByPrimaryKey(request.getTaskId());
        if (taskEntity == null) {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            response.setRspDesc("不存在记录");
            return response;
        }
        TaskStatusEnum taskStatus = TaskStatusEnum.valueOfCode(taskEntity.getTaskStatus());
        if (taskStatus == TaskStatusEnum.EXECUTING) {
            backgroundTaskService.cancel(request.getTaskId());
        } else {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            response.setRspDesc("任务不处于执行中，无需强杀");
            return response;
        }
        return response;
    }
}
