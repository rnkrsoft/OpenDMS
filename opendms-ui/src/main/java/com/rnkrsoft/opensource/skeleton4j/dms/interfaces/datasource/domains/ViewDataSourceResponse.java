package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.domains;

import com.rnkrsoft.opensource.dms.enums.DataSourceTypeEnum;
import lombok.Data;

import javax.web.doc.AbstractResponse;
import javax.web.doc.annotation.ApidocElement;
import javax.web.doc.enums.ValueDisplayType;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
public class ViewDataSourceResponse extends AbstractResponse{
    @ApidocElement(value = "数据源编号",   required = true, readonly = false)
    int dataSourceId;

    @ApidocElement(value = "数据库类型",   required = true, readonly = false, enumClass = DataSourceTypeEnum.class)
    int dataSourceType;

    @ApidocElement(value = "地址",   required = true, readonly = false)
    String host;

    @ApidocElement(value = "端口",   required = true, readonly = false)
    int port;

    @ApidocElement(value = "数据库驱动",   required = true, readonly = false)
    String driverClassName;

    @ApidocElement(value = "用户名",   required = true, readonly = false)
    String username;

    @ApidocElement(value = "说明", valueDisplayType = ValueDisplayType.TEXTAREA , required = false, readonly = false)
    String remark;
}
