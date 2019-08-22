package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.domains;

import com.rnkrsoft.opensource.dms.enums.DataSourceTypeEnum;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.services.DataSourceService;
import lombok.Data;

import javax.web.doc.AbstractResponse;
import javax.web.doc.annotation.ApidocElement;
import javax.web.doc.enums.ValueDisplayType;
import javax.web.skeleton4j.annotation.WebCascadeInterface;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
public class ToUpdateDataSourceResponse extends AbstractResponse{
    @ApidocElement(value = "数据源编号",   required = true, readonly = false, unique = true, interfaces = {
            @WebCascadeInterface(displayName = "保存", serviceClass = DataSourceService.class, value = "update", confirm = true)
    })
    int dataSourceId;

    @ApidocElement(value = "数据库类型",   required = true, readonly = false, enumClass = DataSourceTypeEnum.class)
    int dataSourceType;

    @ApidocElement(value = "地址",   required = true, readonly = false)
    String host;

    @ApidocElement(value = "端口",   required = true, readonly = false)
    int port;

    @ApidocElement(value = "数据库驱动",   required = true, readonly = false)
    String driverClassName;

    @ApidocElement(value = "数据库名",   required = true, readonly = false)
    String schema;

    @ApidocElement(value = "用户名",   required = false, readonly = false)
    String username;

    @ApidocElement(value = "密码",   required = false, readonly = false, valueDisplayType = ValueDisplayType.PASSWORD)
    String password;

    @ApidocElement(value = "说明", valueDisplayType = ValueDisplayType.TEXTAREA , required = false, readonly = false)
    String remark;
}
