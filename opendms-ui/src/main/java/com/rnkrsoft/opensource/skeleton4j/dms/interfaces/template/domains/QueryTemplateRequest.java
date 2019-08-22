package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.template.domains;

import com.rnkrsoft.opensource.dms.enums.TemplateStatusEnum;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.services.DataSourceService;
import lombok.Data;

import javax.web.doc.AbstractRequestPage;
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
public class QueryTemplateRequest extends AbstractRequestPage{
    @ApidocElement(value = "模板名称", required = false, placeholder = "支持模糊查询", layout = WebLayout.LINEAR)
    String templateName;
    @ApidocElement(value = "SQL语句", required = false, placeholder = "支持模糊查询", layout = WebLayout.LINEAR)
    String sqlStatement;
    @ApidocElement(value = "数据源编号",   required = false, readonly = false, valueDisplayType = ValueDisplayType.SELECTION, interfaces = {
            @WebCascadeInterface(
                    serviceClass = DataSourceService.class,
                    value = "fetchDataSource=fetch",
                    cascadeEvent = WebTriggerEvent.INIT,
                    resultDisplay = @WebResultDisplay(displayType = WebDisplayType.DATA_SOURCE)
            )
    })
    Integer dataSourceId;
    @ApidocElement(value = "状态", required = false, layout = WebLayout.FLOW, enumClass = TemplateStatusEnum.class, defaults = "1")
    Integer templateStatus;
}
