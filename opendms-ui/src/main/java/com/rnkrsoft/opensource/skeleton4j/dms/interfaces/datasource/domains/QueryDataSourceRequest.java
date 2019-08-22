package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.domains;

import com.rnkrsoft.opensource.dms.enums.DataSourceTypeEnum;
import lombok.Data;

import javax.web.doc.AbstractRequestPage;
import javax.web.doc.annotation.ApidocElement;
import javax.web.doc.enums.ValueDisplayType;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
public class QueryDataSourceRequest extends AbstractRequestPage {
    @ApidocElement(value = "数据库类型", valueDisplayType = ValueDisplayType.SELECTION, required = false, readonly = false, enumClass = DataSourceTypeEnum.class,maxLen = 2)
    Integer dataSourceType;

    @ApidocElement(value = "地址", valueDisplayType = ValueDisplayType.INTEGER, required = false, readonly = false, maxLen = 36)
    Integer host;
}
