package com.rnkrsoft.opensource.dms.services;

import com.rnkrsoft.opensource.dms.enums.TemplateStatusEnum;
import com.rnkrsoft.opensource.dms.jdbc.bo.QueryTemplateBO;
import com.rnkrsoft.opensource.dms.jdbc.dao.TemplateDAO;
import com.rnkrsoft.opensource.dms.jdbc.entity.TemplateEntity;
import com.rnkrsoft.framework.orm.Pagination;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.template.domains.*;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.template.services.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.web.data.Node;
import javax.web.doc.enums.Skeleton4jRspCode;
import java.util.List;

/**
 * Created by rnkrsoft.com on 2019/6/30.
 */
@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    TemplateDAO templateDAO;

    @Override
    public CreateTemplateResponse create(CreateTemplateRequest request) {
        CreateTemplateResponse response = new CreateTemplateResponse();
        if (request.getSqlStatement().trim().endsWith(";")){
            response.setRspCode(Skeleton4jRspCode.FAIL);
            response.setRspDesc("SQL语句含有以分号结尾,请检查SQL语句保证有效");
            return response;
        }
        templateDAO.insertSelective(TemplateEntity.builder()
                        .templateName(request.getTemplateName())
                        .sqlStatement(request.getSqlStatement())
                        .dataSourceId(request.getDatasourceId())
                        .maxRowSize(request.getMaxRowSize())
                        .templateStatus(TemplateStatusEnum.NORMAL.getCode())
                        .build()
        );
        return response;
    }

    @Override
    public ToUpdateTemplateResponse toUpdate(ToUpdateTemplateRequest request) {
        ToUpdateTemplateResponse response = new ToUpdateTemplateResponse();
        TemplateEntity templateEntity = templateDAO.selectByPrimaryKey(request.getTemplateId());
        if (templateEntity == null) {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            return response;
        }
        if (TemplateStatusEnum.valueOfCode(templateEntity.getTemplateStatus()) != TemplateStatusEnum.NORMAL){
            response.setRspCode(Skeleton4jRspCode.FAIL);
            response.setRspDesc("请确认模板是否已删除！");
            return response;
        }
        response.setTemplateId(templateEntity.getTemplateId());
        response.setTemplateName(templateEntity.getTemplateName());
        response.setDataSourceId(templateEntity.getDataSourceId());
        response.setSqlStatement(templateEntity.getSqlStatement());
        response.setMaxRowSize(templateEntity.getMaxRowSize());
        return response;
    }

    @Override
    public UpdateTemplateResponse update(UpdateTemplateRequest request) {
        UpdateTemplateResponse response = new UpdateTemplateResponse();
        if (request.getSqlStatement().trim().endsWith(";")){
            response.setRspCode(Skeleton4jRspCode.FAIL);
            response.setRspDesc("SQL语句含有以分号结尾,请检查SQL语句保证有效");
            return response;
        }
        TemplateEntity templateEntity = templateDAO.selectByPrimaryKey(request.getTemplateId());
        if (templateEntity == null) {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            return response;
        }
        if (TemplateStatusEnum.valueOfCode(templateEntity.getTemplateStatus()) != TemplateStatusEnum.NORMAL){
            response.setRspCode(Skeleton4jRspCode.FAIL);
            response.setRspDesc("请确认模板是否已删除！");
            return response;
        }
        TemplateEntity updateEntity = new TemplateEntity();
        updateEntity.setTemplateId(templateEntity.getTemplateId());
        updateEntity.setTemplateName(request.getTemplateName());
        updateEntity.setDataSourceId(request.getDataSourceId());
        updateEntity.setMaxRowSize(request.getMaxRowSize());
        updateEntity.setSqlStatement(request.getSqlStatement());
        int cnt = templateDAO.updateByPrimaryKeySelective(updateEntity);
        if (cnt == 0){
            response.setRspCode(Skeleton4jRspCode.FAIL);
        }
        return response;
    }

    @Override
    public DeleteTemplateResponse delete(DeleteTemplateRequest request) {
        DeleteTemplateResponse response = new DeleteTemplateResponse();
        TemplateEntity templateEntity = templateDAO.selectByPrimaryKey(request.getTemplateId());
        if (templateEntity == null) {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            return response;
        }
        if (TemplateStatusEnum.valueOfCode(templateEntity.getTemplateStatus()) != TemplateStatusEnum.NORMAL){
            response.setRspCode(Skeleton4jRspCode.FAIL);
            response.setRspDesc("请确认模板是否已删除！");
            return response;
        }
        int cnt = templateDAO.updateByPrimaryKeySelective(TemplateEntity.builder()
                .templateId(request.getTemplateId())
                .templateStatus(TemplateStatusEnum.DELETED.getCode())
                .build()
        );
        if (cnt == 0){
            response.setRspCode(Skeleton4jRspCode.FAIL);
        }
        return response;
    }

    @Override
    public ViewTemplateResponse view(ViewTemplateRequest request) {
        ViewTemplateResponse response = new ViewTemplateResponse();
        TemplateEntity templateEntity = templateDAO.selectByPrimaryKey(request.getTemplateId());
        if (templateEntity == null) {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            return response;
        }
        response.setTemplateId(templateEntity.getTemplateId());
        response.setTemplateName(templateEntity.getTemplateName());
        response.setDataSourceId(templateEntity.getDataSourceId());
        response.setSqlStatement(templateEntity.getSqlStatement());
        response.setMaxRowSize(templateEntity.getMaxRowSize());
        return response;
    }

    @Override
    public QueryTemplateResponse query(QueryTemplateRequest request) {
        QueryTemplateResponse response = new QueryTemplateResponse();
        response.setPageNo(request.getPageNo());
        response.setPageSize(request.getPageSize());
        Pagination<QueryTemplateBO> pagination = new Pagination<QueryTemplateBO>(request.getPageSize(), request.getPageNo());
        pagination.getParams().put("templateName", request.getTemplateName());
        pagination.getParams().put("dataSourceId", request.getDataSourceId());
        pagination.getParams().put("sqlStatement", request.getSqlStatement());
        pagination.getParams().put("templateStatus", request.getTemplateStatus());
        templateDAO.selectTemplate(pagination);
        response.setPageNum(pagination.getPageNum());
        response.setTotal(pagination.getTotal());
        for (QueryTemplateBO record : pagination.getRecords()){
            response.addRecord(QueryTemplateRecord.builder()
                            .templateId(record.getTemplateId())
                            .templateName(record.getTemplateName())
                            .maxRowSize(record.getMaxRowSize())
                            .sqlStatement(record.getSqlStatement())
                            .datasourceName(record.getDataSourceName())
                            .datasourceType(record.getDataSourceType())
                            .templateStatus(record.getTemplateStatus())
                            .build()
            );
        }
        return response;
    }

    @Override
    public TestTemplateResponse test(TestTemplateRequest request) {
        TestTemplateResponse response = new TestTemplateResponse();
        TemplateEntity templateEntity = templateDAO.selectByPrimaryKey(request.getTemplateId());
        if (templateEntity == null) {
            response.setRspCode(Skeleton4jRspCode.FAIL);
            return response;
        }
        String jdbcUrl = "";
        return response;
    }

    @Override
    public FetchTemplateResponse fetch(FetchTemplateRequest request) {
        FetchTemplateResponse response = new FetchTemplateResponse();
        List<TemplateEntity> templateEntities = templateDAO.selectAnd(TemplateEntity.builder()
                .templateStatus(TemplateStatusEnum.NORMAL.getCode())
                .build()
        );
        for (TemplateEntity record : templateEntities) {
            response.addNode(Node.builder()
                            .text(record.getTemplateName())
                            .value(Integer.toString(record.getTemplateId()))
                            .build()
            );
        }
        return response;
    }
}
