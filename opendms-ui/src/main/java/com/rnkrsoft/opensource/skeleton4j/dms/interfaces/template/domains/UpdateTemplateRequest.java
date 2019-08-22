package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.template.domains;


import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.services.DataSourceService;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.template.services.TemplateService;
import lombok.Data;

import javax.web.doc.annotation.ApidocElement;
import javax.web.doc.enums.ValueDisplayType;
import javax.web.doc.enums.WebDisplayType;
import javax.web.doc.enums.WebTriggerEvent;
import javax.web.skeleton4j.annotation.WebCascadeInterface;
import javax.web.skeleton4j.annotation.WebResultDisplay;
import java.io.Serializable;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
public class UpdateTemplateRequest implements Serializable{
    @ApidocElement(value = "模板编号", hidden = true, unique = true, interfaces = {
            @WebCascadeInterface(displayName = "保存", serviceClass = TemplateService.class, value = "updateTemplate=update")
    })
    Integer templateId;
    @ApidocElement("模板名称")
    String templateName;
    @ApidocElement(value = "最大导出行数", defaults = "1000")
    Integer maxRowSize;
    @ApidocElement(value = "数据源编号",   required = true, readonly = false, valueDisplayType = ValueDisplayType.SELECTION, interfaces = {
            @WebCascadeInterface(
                    serviceClass = DataSourceService.class,
                    value = "fetchDataSource=fetch",
                    cascadeEvent = WebTriggerEvent.INIT,
                    resultDisplay = @WebResultDisplay(displayType = WebDisplayType.DATA_SOURCE)
            )
    })
    Integer dataSourceId;
    @ApidocElement(value = "SQL语句", maxLen = 10240, placeholder = "可输入带有变量占位符的SQL语句")
    String sqlStatement;
}
