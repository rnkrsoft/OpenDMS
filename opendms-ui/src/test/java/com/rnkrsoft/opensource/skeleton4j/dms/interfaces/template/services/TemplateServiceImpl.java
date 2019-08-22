package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.template.services;

import com.rnkrsoft.opensource.dms.enums.DataSourceTypeEnum;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.template.domains.*;

import javax.web.data.Node;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
public class TemplateServiceImpl implements TemplateService{
    @Override
    public CreateTemplateResponse create(CreateTemplateRequest request) {
        CreateTemplateResponse response = new CreateTemplateResponse();
        return response;
    }

    @Override
    public ToUpdateTemplateResponse toUpdate(ToUpdateTemplateRequest request) {
        ToUpdateTemplateResponse response = new ToUpdateTemplateResponse();
        response.setTemplateId(request.getTemplateId());
        response.setTemplateName("测试模板");
        response.setDataSourceId(1);
        response.setMaxRowSize(1000);
        response.setSqlStatement("select * from table1 t where t.serial_no = ${serialNo} ans t.begin_date > ${beginDate}");
        return response;
    }

    @Override
    public UpdateTemplateResponse update(UpdateTemplateRequest request) {
        UpdateTemplateResponse response = new UpdateTemplateResponse();
        return response;
    }

    @Override
    public DeleteTemplateResponse delete(DeleteTemplateRequest request) {
        DeleteTemplateResponse response = new DeleteTemplateResponse();
        return response;
    }

    @Override
    public ViewTemplateResponse view(ViewTemplateRequest request) {
        ViewTemplateResponse response = new ViewTemplateResponse();
        response.setTemplateId(request.getTemplateId());
        response.setTemplateName("测试模板");
        response.setDataSourceId(1);
        response.setMaxRowSize(1000);
        response.setSqlStatement("select * from table1 t where t.serial_no = ${serialNo} ans t.begin_date > ${beginDate}");
        return response;
    }

    @Override
    public QueryTemplateResponse query(QueryTemplateRequest request) {
        QueryTemplateResponse response = new QueryTemplateResponse();
        response.setPageNo(request.getPageNo());
        response.setPageSize(request.getPageSize());
        response.setPageNum(5);
        response.setTotal(5 * request.getPageSize());
        for (int i = 0; i < request.getPageSize(); i++) {
            response.addRecord(QueryTemplateRecord.builder()
                            .templateId(i)
                            .templateName("模板" + (i + request.getPageSize() * request.getPageNo()))
                            .maxRowSize(1000)
                            .sqlStatement("select * from table" + (i + request.getPageSize() * request.getPageNo()))
                    .build()
            );
        }
        return response;
    }

    @Override
    public TestTemplateResponse test(TestTemplateRequest request) {
        TestTemplateResponse response = new TestTemplateResponse();
        return response;
    }

    @Override
    public FetchTemplateResponse fetch(FetchTemplateRequest request) {
        FetchTemplateResponse response = new FetchTemplateResponse();
        response.addNode(Node.builder().text("测试模板1").value("1").build());
        response.addNode(Node.builder().text("测试模板2").value("2").build());
        response.addNode(Node.builder().text("测试模板3").value("3").build());
        response.addNode(Node.builder().text("测试模板4").value("4").build());
        response.addNode(Node.builder().text("测试模板5").value("5").build());
        response.addNode(Node.builder().text("测试模板6").value("6").build());
        return response;
    }
}
