package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.domains;

import lombok.Data;

import javax.web.doc.AbstractRequest;
import javax.web.doc.annotation.ApidocElement;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
public class DeleteDataSourceRequest extends AbstractRequest{
    @ApidocElement(value = "数据源编号",   required = true, readonly = false, unique = true)
    int dataSourceId;
}
