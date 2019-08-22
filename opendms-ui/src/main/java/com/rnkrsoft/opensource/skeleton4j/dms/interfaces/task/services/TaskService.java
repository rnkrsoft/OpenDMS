package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.services;

import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.domains.*;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.domains.CreateTaskRequest;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.domains.CreateTaskResponse;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.domains.ToCreateTaskRequest;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.domains.ToCreateTaskResponse;

import javax.web.doc.annotation.ApidocInterface;
import javax.web.doc.annotation.ApidocService;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@ApidocService("任务服务")
public interface TaskService {
    @ApidocInterface("查看")
    ViewTaskResponse view(ViewTaskRequest request);
    @ApidocInterface("查询")
    QueryTaskResponse query(QueryTaskRequest request);
    @ApidocInterface("执行")
    ExecuteTaskResponse execute(ExecuteTaskRequest request);
    @ApidocInterface("下载")
    DownloadTaskResponse download(DownloadTaskRequest request);
    @ApidocInterface("转向创建任务")
    ToCreateTaskResponse toCreate(ToCreateTaskRequest request);
    @ApidocInterface("创建任务")
    CreateTaskResponse create(CreateTaskRequest request);
    @ApidocInterface("删除任务")
    DeleteTaskResponse delete(DeleteTaskRequest request);
    @ApidocInterface("取消任务")
    CancelTaskResponse cancel(CancelTaskRequest request);
    @ApidocInterface("强杀任务")
    ForceKillTaskResponse forceKill(ForceKillTaskRequest request);
}
