package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.services;

import com.rnkrsoft.opensource.dms.enums.DataSourceTypeEnum;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.domains.*;

import javax.web.data.Node;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
public class DataSourceServiceImpl implements DataSourceService {
    @Override
    public CreateDataSourceResponse create(CreateDataSourceRequest request) {
        CreateDataSourceResponse response = new CreateDataSourceResponse();
        return response;
    }

    @Override
    public ToUpdateDataSourceResponse toUpdate(ToUpdateDataSourceRequest request) {
        System.out.println(request);
        ToUpdateDataSourceResponse response = new ToUpdateDataSourceResponse();
        response.setDataSourceId(request.getDataSourceId());
        response.setDataSourceType(DataSourceTypeEnum.MY_SQL.getCode());
        response.setDriverClassName("com.mysql.Driver");
        response.setHost("127.0.0.1");
        response.setPort(3306);
        response.setUsername("root");
        response.setPassword("test");
        response.setRemark("this is a test");
        return response;
    }

    @Override
    public UpdateDataSourceResponse update(UpdateDataSourceRequest request) {
        System.out.println(request);
        UpdateDataSourceResponse response = new UpdateDataSourceResponse();
        return response;
    }

    @Override
    public DeleteDataSourceResponse delete(DeleteDataSourceRequest request) {
        System.out.println(request);
        DeleteDataSourceResponse response = new DeleteDataSourceResponse();
        return response;
    }

    @Override
    public ViewDataSourceResponse view(ViewDataSourceRequest request) {
        System.out.println(request);
        ViewDataSourceResponse response = new ViewDataSourceResponse();
        response.setDataSourceId(request.getDataSourceId());
        response.setDataSourceType(DataSourceTypeEnum.MY_SQL.getCode());
        response.setDriverClassName("com.mysql.Driver");
        response.setHost("127.0.0.1");
        response.setPort(3306);
        response.setUsername("root");
        response.setRemark("this is a test");
        return response;
    }

    @Override
    public QueryDataSourceResponse query(QueryDataSourceRequest request) {
        System.out.println(request);
        QueryDataSourceResponse response = new QueryDataSourceResponse();
        response.setPageNo(request.getPageNo());
        response.setPageSize(request.getPageSize());
        response.setPageNum(5);
        response.setTotal(5 * request.getPageSize());
        for (int i = 0; i < request.getPageSize(); i++) {
            response.addRecord(QueryDataSourceRecord.builder()
                            .dataSourceId(i)
                            .dataSourceType(DataSourceTypeEnum.MY_SQL.getCode())
                            .host("localhost")
                            .port(3306)
                            .userName("root")
                            .build()
            );
        }
        return response;
    }

    @Override
    public TestDataSourceResponse test(TestDataSourceRequest request) {
        System.out.println(request);
        TestDataSourceResponse response = new TestDataSourceResponse();
        return response;
    }

    @Override
    public FetchDataSourceResponse fetch(FetchDataSourceRequest request) {
        System.out.println(request);
        FetchDataSourceResponse response = new FetchDataSourceResponse();
        response.addNode(Node.builder().text("开发数据源1").value("1").build());
        response.addNode(Node.builder().text("开发数据源2").value("2").build());
        response.addNode(Node.builder().text("开发数据源3").value("3").build());
        return response;
    }
}
