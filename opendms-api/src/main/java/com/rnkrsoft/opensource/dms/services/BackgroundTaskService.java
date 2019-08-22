package com.rnkrsoft.opensource.dms.services;


/**
 * Created by rnkrsoft.com on 2019/7/1.
 * 后台任务服务
 */
public interface BackgroundTaskService {
    /**
     * 初始化后台服务
     */
    void init();

    /**
     * 关闭后台服务
     */
    void shutdown();

    /**
     * 根据任务号创建后台任务
     * @param taskId 任务号
     */
    void newTask(Integer taskId);

    /**
     * 根据任务号取消后台任务
     * @param taskId 任务号
     */
    void cancel(Integer taskId);

    /**
     * 注册后台任务
     * @param taskId 任务号
     * @param thread 任务体
     */
    void register(Integer taskId, Thread thread);

    /**
     * 移除任务
     * @param taskId 任务号
     */
    void remove(Integer taskId);

    /**
     * 生成任务对应的文件
     * @param taskId 任务号
     */
    void generate(Integer taskId);
}
