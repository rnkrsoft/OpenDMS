package com.rnkrsoft.opensource.dms.services;

import com.rnkrsoft.message.MessageFormatter;
import com.rnkrsoft.opensource.dms.jdbc.dao.DataSourceDAO;
import com.rnkrsoft.opensource.dms.jdbc.entity.DataSourceEntity;
import com.rnkrsoft.framework.orm.Pagination;
import com.rnkrsoft.opensource.dms.enums.DataSourceTypeEnum;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.domains.*;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.services.DataSourceService;
import com.rnkrsoft.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.web.data.Node;
import javax.web.doc.enums.Skeleton4jRspCode;
import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * Created by rnkrsoft.com on 2019/6/30.
 */
@Slf4j
@Service
public class DataSourceServiceImpl implements DataSourceService {
    @Autowired
    DataSourceDAO dataSourceDAO;

    @Override
    public CreateDataSourceResponse create(CreateDataSourceRequest request) {
        CreateDataSourceResponse response = new CreateDataSourceResponse();
        String driverClassName = request.getDriverClassName();
        if (StringUtils.isBlank(driverClassName)) {
            if (DataSourceTypeEnum.valueOfCode(request.getDataSourceType()) == DataSourceTypeEnum.MY_SQL) {
                driverClassName = "com.mysql.jdbc.Driver";
            }
        }
        dataSourceDAO.insertSelective(DataSourceEntity.builder()
                        .dataSourceName(request.getDataSourceName())
                        .dataSourceType(request.getDataSourceType())
                        .host(request.getHost())
                        .port(request.getPort())
                        .driverClassName(driverClassName)
                        .schemaName(request.getSchema())
                        .username(request.getUsername())
                        .password(request.getPassword())
                        .remark(request.getRemark())
                        .build()
        );
        return response;
    }

    @Override
    public ToUpdateDataSourceResponse toUpdate(ToUpdateDataSourceRequest request) {
        ToUpdateDataSourceResponse response = new ToUpdateDataSourceResponse();
        DataSourceEntity dataSourceEntity = dataSourceDAO.selectByPrimaryKey(request.getDataSourceId());
        if (dataSourceEntity == null) {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            response.setRspDesc("不存在记录");
            return response;
        }
        response.setDataSourceId(dataSourceEntity.getDataSourceId());
        response.setDataSourceType(dataSourceEntity.getDataSourceType());
        response.setHost(dataSourceEntity.getHost());
        response.setPort(dataSourceEntity.getPort());
        response.setDriverClassName(dataSourceEntity.getDriverClassName());
        response.setSchema(dataSourceEntity.getSchemaName());
        response.setUsername(dataSourceEntity.getUsername());
        response.setPassword(dataSourceEntity.getPassword());
        response.setRemark(dataSourceEntity.getRemark());
        return response;
    }

    @Override
    public UpdateDataSourceResponse update(UpdateDataSourceRequest request) {
        UpdateDataSourceResponse response = new UpdateDataSourceResponse();
        DataSourceEntity dataSourceEntity = dataSourceDAO.selectByPrimaryKey(request.getDataSourceId());
        if (dataSourceEntity == null) {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            response.setRspDesc("不存在记录");
            return response;
        }
        DataSourceEntity updateRecord = new DataSourceEntity();
        updateRecord.setDataSourceId(dataSourceEntity.getDataSourceId());
        updateRecord.setDataSourceType(request.getDataSourceType());
        updateRecord.setHost(request.getHost());
        updateRecord.setPort(request.getPort());
        updateRecord.setDriverClassName(request.getDriverClassName());
        updateRecord.setSchemaName(request.getSchema());
        updateRecord.setUsername(request.getUsername());
        updateRecord.setPassword(request.getPassword());
        updateRecord.setRemark(request.getRemark());
        int updateCnt = dataSourceDAO.updateByPrimaryKeySelective(updateRecord);
        if (updateCnt == 0){
            response.setRspCode(Skeleton4jRspCode.FAIL);
            return response;
        }
        return response;
    }

    @Override
    public DeleteDataSourceResponse delete(DeleteDataSourceRequest request) {
        DeleteDataSourceResponse response = new DeleteDataSourceResponse();
        dataSourceDAO.deleteByPrimaryKey(request.getDataSourceId());
        return response;
    }

    @Override
    public ViewDataSourceResponse view(ViewDataSourceRequest request) {
        ViewDataSourceResponse response = new ViewDataSourceResponse();
        DataSourceEntity dataSourceEntity = dataSourceDAO.selectByPrimaryKey(request.getDataSourceId());
        if (dataSourceEntity == null) {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            response.setRspDesc("不存在记录");
            return response;
        }
        response.setDataSourceId(dataSourceEntity.getDataSourceId());
        response.setDataSourceType(dataSourceEntity.getDataSourceType());
        response.setHost(dataSourceEntity.getHost());
        response.setPort(dataSourceEntity.getPort());
        response.setDriverClassName(dataSourceEntity.getDriverClassName());
        response.setUsername(dataSourceEntity.getUsername());
        response.setRemark(dataSourceEntity.getRemark());
        return response;
    }

    @Override
    public QueryDataSourceResponse query(QueryDataSourceRequest request) {
        QueryDataSourceResponse response = new QueryDataSourceResponse();
        response.setPageNo(request.getPageNo());
        response.setPageSize(request.getPageSize());

        Pagination<DataSourceEntity> pagination = new Pagination<DataSourceEntity>(request.getPageSize(), request.getPageNo(), new DataSourceEntity());
        pagination.getParams().put("dataSourceType", request.getDataSourceType());
        pagination.getParams().put("host", request.getHost());
        dataSourceDAO.selectDataSource(pagination);
        response.setPageNum(pagination.getPageNum());
        response.setTotal(pagination.getTotal());
        for (DataSourceEntity record : pagination.getRecords()) {
            response.addRecord(QueryDataSourceRecord.builder()
                    .dataSourceName(record.getDataSourceName())
                    .dataSourceId(record.getDataSourceId())
                    .dataSourceType(record.getDataSourceType())
                    .host(record.getHost())
                    .port(record.getPort())
                    .schema(record.getSchemaName())
                    .userName(record.getUsername())
                    .build());
        }
        return response;
    }

    @Override
    public TestDataSourceResponse test(TestDataSourceRequest request) {
        TestDataSourceResponse response = new TestDataSourceResponse();
        DataSourceEntity dataSourceEntity = dataSourceDAO.selectByPrimaryKey(request.getDataSourceId());
        if (dataSourceEntity == null) {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            response.setRspDesc("不存在记录");
            return response;
        }
        DataSourceTypeEnum dataSourceType = DataSourceTypeEnum.valueOfCode(dataSourceEntity.getDataSourceType());
        //数据库名
        String schema = dataSourceEntity.getSchemaName();
        //用户号
        String username = dataSourceEntity.getUsername();
        //密码
        String password = dataSourceEntity.getPassword();
        //地址
        String host = dataSourceEntity.getHost();
        //端口
        int port = dataSourceEntity.getPort();
        //驱动类
        String driverClassName = dataSourceEntity.getDriverClassName();
        String url = MessageFormatter.format("jdbc:{}://{}:{}/{}", dataSourceType.getJdbc(), host, port, schema);

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT 1 FROM DUAL");
            response.setResultMessage("测试连通性成功！");
            return response;
        } catch (SQLException e1) {
            Throwable e = e1.getCause();
            response.setResultMessage("测试连通性失败！" + "请检查:" + (e == null ? e1.getMessage() : e.getMessage()));
            log.error("测试连通性失败！" + "请检查:" + (e == null ? e1.getMessage() : e.getMessage()), e1);
            return response;
        } catch (ClassNotFoundException e2) {
            response.setResultMessage("测试连通性失败！请检查驱动类【" + e2.getMessage() + "】是否存在？");
            log.error("测试连通性失败！请检查驱动类【" + e2.getMessage() + "】是否存在？", e2);
            return response;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {

            }
        }

    }

    @Override
    public FetchDataSourceResponse fetch(FetchDataSourceRequest request) {
        FetchDataSourceResponse response = new FetchDataSourceResponse();
        List<DataSourceEntity> dataSourceEntities = dataSourceDAO.selectAnd(DataSourceEntity.builder().status(1).build());
        for (DataSourceEntity record : dataSourceEntities) {
            response.addNode(Node.builder().text(record.getDataSourceName()).value(Integer.toString(record.getDataSourceId())).build());
        }
        return response;
    }
}
