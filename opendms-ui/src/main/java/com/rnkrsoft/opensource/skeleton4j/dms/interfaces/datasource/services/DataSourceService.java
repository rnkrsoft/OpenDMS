package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.services;

import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.domains.*;

import javax.web.doc.annotation.ApidocInterface;
import javax.web.doc.annotation.ApidocService;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@ApidocService("数据源服务")
public interface DataSourceService {
    @ApidocInterface("创建数据源")
    CreateDataSourceResponse create(CreateDataSourceRequest request);
    @ApidocInterface(" 编辑查看数据源")
    ToUpdateDataSourceResponse toUpdate(ToUpdateDataSourceRequest request);
    @ApidocInterface("编辑数据源")
    UpdateDataSourceResponse update(UpdateDataSourceRequest request);
    @ApidocInterface("删除数据源")
    DeleteDataSourceResponse delete(DeleteDataSourceRequest request);
    @ApidocInterface("查看数据源")
    ViewDataSourceResponse view(ViewDataSourceRequest request);
    @ApidocInterface("查询数据源")
    QueryDataSourceResponse query(QueryDataSourceRequest request);
    @ApidocInterface("测试数据源")
    TestDataSourceResponse test(TestDataSourceRequest request);
    @ApidocInterface("获取数据源")
    FetchDataSourceResponse fetch(FetchDataSourceRequest request);
}
