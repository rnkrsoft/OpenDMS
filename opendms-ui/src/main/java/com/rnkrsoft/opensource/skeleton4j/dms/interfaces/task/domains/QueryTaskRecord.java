package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.domains;

import com.rnkrsoft.opensource.dms.enums.TaskStatusEnum;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.services.TaskService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.web.doc.annotation.ApidocElement;
import javax.web.doc.enums.PatternType;
import javax.web.doc.enums.ValueDisplayType;
import javax.web.doc.enums.WebDisplayType;
import javax.web.doc.enums.WebTriggerEvent;
import javax.web.skeleton4j.annotation.WebCascadeDisplayRule;
import javax.web.skeleton4j.annotation.WebCascadeInterface;
import javax.web.skeleton4j.annotation.WebResultDisplay;
import java.io.Serializable;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryTaskRecord implements Serializable{
    @ApidocElement(value = "编号", unique = true, required = false, maxLen = 5, interfaces = {
            @WebCascadeInterface(serviceClass = TaskService.class, value = "view"),
            @WebCascadeInterface(serviceClass = TaskService.class,
                    value = "execute",
                    displayName = "执行",
                    confirm = true,
                    confirmMessage = "请确认是否立即执行\\\"${taskName}\\\"任务？",
                    rules = {
                            @WebCascadeDisplayRule("${status}==1") //状态为等待执行时显示
                    }
            ),
            @WebCascadeInterface(serviceClass = TaskService.class,
                    value = "download",
                    displayName = "下载",
                    confirm = true,
                    confirmMessage = "请确认是否下载${taskName}?",
                    cascadeEvent = WebTriggerEvent.CLICK,
                    resultDisplay = @WebResultDisplay(displayType = WebDisplayType.REDIRECT),
                    rules = {
                            @WebCascadeDisplayRule("${status}==4")//状态为完成时显示
                    }
            ),
            @WebCascadeInterface(serviceClass = TaskService.class,
                    value = "delete",
                    displayName = "删除",
                    confirm = true,
                    confirmMessage = "请确认是否删除\\\"${taskName}\\\"任务？",
                    rules = {
                            @WebCascadeDisplayRule("${status}==1 || ${status}==3 || ${status}==5") //状态为等待执行时显示
                    }
            ),
            @WebCascadeInterface(serviceClass = TaskService.class,
                    value = "cancel",
                    displayName = "取消",
                    confirm = true,
                    confirmMessage = "请确认是否取消\\\"${taskName}\\\"任务？",
                    rules = {
                            @WebCascadeDisplayRule("${status}==2")//状态为等待执行时显示
                    }
            ),
            @WebCascadeInterface(serviceClass = TaskService.class,
                    value = "forceKill",
                    displayName = "强制取消",
                    confirm = true,
                    confirmMessage = "请确认是否强杀\\\"${taskName}\\\"任务？",
                    rules = {
                            @WebCascadeDisplayRule("${status}==2")//状态为等待执行时显示
                    }
            )
    })
    Integer taskId;
    @ApidocElement(value = "任务名称", maxLen = 36)
    String taskName;
    @ApidocElement(value = "状态", maxLen = 10, enumClass = TaskStatusEnum.class, valueDisplayType = ValueDisplayType.SELECTION)
    Integer status;
    @ApidocElement(value = "进度(%)", maxLen = 5)
    Integer progress;
    @ApidocElement(value = "创建时间", maxLen = 15, valueDisplayType = ValueDisplayType.DATE, patternType = PatternType.FORMAT, pattern = "yyyy/MM/dd HH:mm:ss")
    String createDate;
    @ApidocElement(value = "完成时间", maxLen = 15, valueDisplayType = ValueDisplayType.DATE, patternType = PatternType.FORMAT, pattern = "yyyy/MM/dd HH:mm:ss")
    String finishDate;
    @ApidocElement(value = "SQL语句", maxLen = 40)
    String sqlStatement;
    @ApidocElement(value = "失败原因", maxLen = 20)
    String failureCause;
    @ApidocElement(value = "创建者", maxLen = 15)
    String createUser;
    @ApidocElement(value = "最近更新时间", maxLen = 15, valueDisplayType = ValueDisplayType.DATE, patternType = PatternType.FORMAT, pattern = "yyyy/MM/dd HH:mm:ss")
    String lastUpdateDate;
}
