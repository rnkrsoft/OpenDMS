package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.domains;

import com.rnkrsoft.opensource.dms.enums.TaskStatusEnum;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.template.services.TemplateService;
import lombok.Data;

import javax.web.doc.AbstractRequestPage;
import javax.web.doc.annotation.ApidocElement;
import javax.web.doc.enums.*;
import javax.web.skeleton4j.annotation.WebCascadeInterface;
import javax.web.skeleton4j.annotation.WebResultDisplay;
import javax.web.skeleton4j.session.LoginUserId;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
public class QueryTaskRequest extends AbstractRequestPage implements LoginUserId {
    @ApidocElement(value = "任务状态",
            required = false,
            maxLen = 10,
            enumClass = TaskStatusEnum.class,
            layout = WebLayout.LINEAR,
            valueDisplayType = ValueDisplayType.SELECTION,
            defaults = {"1", "2", "3", "4"}
    )
    final Set<Integer> taskStatus = new HashSet();
    @ApidocElement(value = "任务编号", required = false)
    Integer taskId;
    @ApidocElement(value = "SQL模板", required = false, interfaces = {
            @WebCascadeInterface(serviceClass = TemplateService.class,
                    value = "fetchTemplate=fetch",
                    cascadeEvent = WebTriggerEvent.INIT,
                    resultDisplay = @WebResultDisplay(displayType = WebDisplayType.DATA_SOURCE)
            )
    })
    Integer templateId;
    @ApidocElement(value = "创建开始时间", required = false, valueDisplayType = ValueDisplayType.DATE, patternType = PatternType.FORMAT, pattern = "yyyy/MM/dd")
    String beginCreateDate;
    @ApidocElement(value = "创建结束时间", required = false, valueDisplayType = ValueDisplayType.DATE, patternType = PatternType.FORMAT, pattern = "yyyy/MM/dd")
    String endCreateDate;
    @ApidocElement(value = "完成时间", required = false, valueDisplayType = ValueDisplayType.DATE, patternType = PatternType.FORMAT, pattern = "yyyy/MM/dd")
    String finishDate;

    String loginUserId;
}
