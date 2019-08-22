package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.domains;

import com.rnkrsoft.opensource.dms.enums.DataSourceTypeEnum;
import lombok.Data;

import javax.web.doc.AbstractRequest;
import javax.web.doc.annotation.ApidocElement;
import javax.web.doc.enums.ValueDisplayType;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
public class CreateDataSourceRequest extends AbstractRequest{
    @ApidocElement(value = "数据源名称",  required = true, readonly = false)
    String dataSourceName;

    @ApidocElement(value = "数据库类型",  maxLen = 1, minLen = 1, required = true, readonly = false, enumClass = DataSourceTypeEnum.class)
    Integer dataSourceType;

    @ApidocElement(value = "地址",   required = true, readonly = false)
    String host;

    @ApidocElement(value = "端口", maxLen = 5, required = true, readonly = false)
    Integer port;

    @ApidocElement(value = "数据库驱动",   required = false, readonly = false, placeholder = "不填时根据数据源类型自动填充")
    String driverClassName;

    @ApidocElement(value = "数据库名",   required = true, readonly = false)
    String schema;

    @ApidocElement(value = "用户名",   required = false, readonly = false)
    String username;

    @ApidocElement(value = "密码",   required = false, readonly = false)
    String password;

    @ApidocElement(value = "说明", valueDisplayType = ValueDisplayType.TEXTAREA , required = false, readonly = false)
    String remark;
}
