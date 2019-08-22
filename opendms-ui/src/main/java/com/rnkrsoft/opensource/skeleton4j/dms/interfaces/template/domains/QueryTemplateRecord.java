package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.template.domains;

import com.rnkrsoft.opensource.dms.enums.DataSourceTypeEnum;
import com.rnkrsoft.opensource.dms.enums.TemplateStatusEnum;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.services.TaskService;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.template.services.TemplateService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.web.doc.annotation.ApidocElement;
import javax.web.doc.enums.WebLayout;
import javax.web.skeleton4j.annotation.WebCascadeDisplayRule;
import javax.web.skeleton4j.annotation.WebCascadeInterface;
import javax.web.skeleton4j.enums.RuleMethod;
import java.io.Serializable;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryTemplateRecord implements Serializable {
    @ApidocElement(value = "编号", hidden = false, unique = true, maxLen = 5, interfaces = {
            @WebCascadeInterface(displayName = "查看",
                    serviceClass = TemplateService.class,
                    value = "view"
            ),
            @WebCascadeInterface(displayName = "编辑",
                    serviceClass = TemplateService.class,
                    value = "toUpdate",
                    //模板正常时显示编辑
                    rules = @WebCascadeDisplayRule(method = RuleMethod.SHOW, value = "${templateStatus}==1")
            ),
            @WebCascadeInterface(displayName = "测试",
                    serviceClass = TemplateService.class,
                    value = "test"
            ),
            @WebCascadeInterface(displayName = "创建任务",
                    serviceClass = TaskService.class,
                    value = "toCreateTask=toCreate",
                    //模板正常时显示编辑
                    rules = @WebCascadeDisplayRule(method = RuleMethod.SHOW, value = "${templateStatus}==1")
            ),
            @WebCascadeInterface(displayName = "删除",
                    serviceClass = TemplateService.class,
                    value = "delete",
                    confirm = true,
                    confirmMessage = "请确认是否删除${templateName}模板，删除以后不能创建任务？",
                    //模板正常时显示编辑
                    rules = @WebCascadeDisplayRule(method = RuleMethod.SHOW, value = "${templateStatus}==1")
            ),
    })
    Integer templateId;
    @ApidocElement(value = "模板名称", maxLen = 20)
    String templateName;
    @ApidocElement(value = "SQL语句", maxLen = 40)
    String sqlStatement;
    @ApidocElement(value = "最大导出行数", defaults = "1000", maxLen = 8)
    Integer maxRowSize;
    @ApidocElement(value = "数据源名称", maxLen = 20)
    String datasourceName;
    @ApidocElement(value = "数据源类型", maxLen = 10, enumClass = DataSourceTypeEnum.class)
    Integer datasourceType;
    @ApidocElement(value = "状态", maxLen = 10, required = false, layout = WebLayout.FLOW, enumClass = TemplateStatusEnum.class)
    Integer templateStatus;
}
