package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.template.domains;

import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.services.DataSourceService;
import lombok.Data;

import javax.web.doc.AbstractResponse;
import javax.web.doc.annotation.ApidocElement;
import javax.web.doc.enums.ValueDisplayType;
import javax.web.doc.enums.WebDisplayType;
import javax.web.doc.enums.WebLayout;
import javax.web.doc.enums.WebTriggerEvent;
import javax.web.skeleton4j.annotation.WebCascadeInterface;
import javax.web.skeleton4j.annotation.WebResultDisplay;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
public class ViewTemplateResponse extends AbstractResponse{
    @ApidocElement(value = "模板编号", hidden = false, unique = true, layout = WebLayout.LINEAR)
    Integer templateId;
    @ApidocElement(value = "模板名称",layout = WebLayout.LINEAR)
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
    @ApidocElement(value = "SQL语句", placeholder = "可输入带有变量占位符的SQL语句", valueDisplayType = ValueDisplayType.TEXTAREA)
    String sqlStatement;
}
