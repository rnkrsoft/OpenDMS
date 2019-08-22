package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.template.domains;

import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.services.DataSourceService;
import lombok.Data;

import javax.web.doc.AbstractRequest;
import javax.web.doc.annotation.ApidocElement;
import javax.web.doc.enums.ValueDisplayType;
import javax.web.doc.enums.WebDisplayType;
import javax.web.doc.enums.WebTriggerEvent;
import javax.web.skeleton4j.annotation.WebCascadeInterface;
import javax.web.skeleton4j.annotation.WebResultDisplay;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
public class CreateTemplateRequest extends AbstractRequest{
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
    Integer datasourceId;
    @ApidocElement(value = "SQL语句", maxLen = 10240, placeholder = "可输入带有变量占位符的SQL语句", valueDisplayType = ValueDisplayType.TEXTAREA)
    String sqlStatement;
}
