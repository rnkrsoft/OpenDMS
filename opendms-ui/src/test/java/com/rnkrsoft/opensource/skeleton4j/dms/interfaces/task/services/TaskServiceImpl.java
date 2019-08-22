package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.services;

import com.rnkrsoft.opensource.dms.enums.FileFormatEnum;
import com.rnkrsoft.opensource.dms.enums.TaskStatusEnum;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.domains.*;
import com.rnkrsoft.time.DateStyle;
import com.rnkrsoft.utils.DateUtils;

import java.util.Date;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
public class TaskServiceImpl implements TaskService {
    @Override
    public ViewTaskResponse view(ViewTaskRequest request) {
        ViewTaskResponse response = new ViewTaskResponse();
        response.setTaskId(request.getTaskId());
        response.setTaskName("zhangsan的导出用户数据任务");
        response.setSqlStatement("select * from dual");
        response.setProgress(52);
        response.setCreateUser("zhangsan");
        response.setCreateDate(DateUtils.toString(new Date(), DateStyle.SECOND_FORMAT2));
        response.setFinishDate(DateUtils.toString(new Date(), DateStyle.SECOND_FORMAT2));
        return response;
    }

    @Override
    public QueryTaskResponse query(QueryTaskRequest request) {
        QueryTaskResponse response = new QueryTaskResponse();
        response.setPageNo(request.getPageNo());
        response.setPageSize(request.getPageSize());
        response.setPageNum(5);
        response.setTotal(5 * request.getPageSize());
        for (int i = 0; i < request.getPageSize(); i++) {
            response.addRecord(QueryTaskRecord.builder()
                            .taskId(i + request.getPageSize() * request.getPageNo())
                            .taskName("测试任务" + i)
//                            .sqlStatement("select * from dual")
                            .progress(i)
                            .status(TaskStatusEnum.values()[(i % 6)].getCode())
                            .createUser("zhangsan")
                            .createDate(DateUtils.toString(new Date(), DateStyle.SECOND_FORMAT2))
                            .finishDate(DateUtils.toString(new Date(), DateStyle.SECOND_FORMAT2))
                            .lastUpdateDate(DateUtils.toString(new Date(), DateStyle.SECOND_FORMAT2))
                            .build()
            );
        }
        return response;
    }

    @Override
    public ExecuteTaskResponse execute(ExecuteTaskRequest request) {
        ExecuteTaskResponse response = new ExecuteTaskResponse();
        return response;
    }

    @Override
    public DownloadTaskResponse download(DownloadTaskRequest request) {
        DownloadTaskResponse response = new DownloadTaskResponse();
        response.setShareUrl("https://www.baidu.com");
        return response;
    }

    @Override
    public ToCreateTaskResponse toCreate(ToCreateTaskRequest request) {
        ToCreateTaskResponse response = new ToCreateTaskResponse();
        response.setTemplateId(request.getTemplateId());
        response.setSqlStatement("select * from dual");
        response.setFileFormat(FileFormatEnum.CSV.getCode());
        response.getArguments().add(ToCreateTaskRecord.builder().argName("name").argFormat("文本").build());
        response.getArguments().add(ToCreateTaskRecord.builder().argName("age").argFormat("整数").build());
        return response;
    }

    @Override
    public CreateTaskResponse create(CreateTaskRequest request) {
        CreateTaskResponse response = new CreateTaskResponse();
        return response;
    }

    @Override
    public DeleteTaskResponse delete(DeleteTaskRequest request) {
        DeleteTaskResponse response = new DeleteTaskResponse();
        return response;
    }

    @Override
    public CancelTaskResponse cancel(CancelTaskRequest request) {
        CancelTaskResponse response = new CancelTaskResponse();
        return response;
    }

    @Override
    public ForceKillTaskResponse forceKill(ForceKillTaskRequest request) {
        ForceKillTaskResponse response = new ForceKillTaskResponse();
        return response;
    }
}
