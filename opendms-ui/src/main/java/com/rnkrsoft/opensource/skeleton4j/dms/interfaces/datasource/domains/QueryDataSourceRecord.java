package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.domains;

import com.rnkrsoft.opensource.dms.enums.DataSourceTypeEnum;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.services.DataSourceService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.web.doc.annotation.ApidocElement;
import javax.web.skeleton4j.annotation.WebCascadeInterface;
import java.io.Serializable;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryDataSourceRecord implements Serializable {
    @ApidocElement(value = "编号", required = true, readonly = false, unique = true, maxLen = 5, interfaces = {
            @WebCascadeInterface(displayName = "查看", serviceClass = DataSourceService.class, value = "view"),
            @WebCascadeInterface(displayName = "编辑", serviceClass = DataSourceService.class, value = "toUpdate"),
            @WebCascadeInterface(displayName = "测试一下", serviceClass = DataSourceService.class, value = "test"),
            @WebCascadeInterface(displayName = "删除", serviceClass = DataSourceService.class, value = "delete")
    })
    Integer dataSourceId;
    @ApidocElement(value = "名称", required = true, readonly = false, maxLen = 36)
    String dataSourceName;
    @ApidocElement(value = "类型", required = true, readonly = false, enumClass = DataSourceTypeEnum.class, maxLen = 10)
    int dataSourceType;

    @ApidocElement(value = "地址", required = true, readonly = false, maxLen = 20)
    String host;

    @ApidocElement(value = "端口", required = true, readonly = false, maxLen = 10)
    Integer port;

    @ApidocElement(value = "数据库名", required = true, readonly = false, maxLen = 20)
    String schema;

    @ApidocElement(value = "用户名", required = true, readonly = false, maxLen = 20)
    String userName;
}
