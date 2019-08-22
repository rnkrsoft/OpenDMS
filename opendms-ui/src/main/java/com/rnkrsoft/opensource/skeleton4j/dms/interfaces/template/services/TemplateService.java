package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.template.services;

import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.template.domains.*;

import javax.web.doc.annotation.ApidocInterface;
import javax.web.doc.annotation.ApidocService;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@ApidocService("任务模板服务")
public interface TemplateService {
    @ApidocInterface("创建任务模板")
    CreateTemplateResponse create(CreateTemplateRequest request);
    @ApidocInterface("编辑查看任务模板")
    ToUpdateTemplateResponse toUpdate(ToUpdateTemplateRequest request);
    @ApidocInterface("编辑任务模板")
    UpdateTemplateResponse update(UpdateTemplateRequest request);
    @ApidocInterface("删除任务模板")
    DeleteTemplateResponse delete(DeleteTemplateRequest request);
    @ApidocInterface("查看任务模板")
    ViewTemplateResponse view(ViewTemplateRequest request);
    @ApidocInterface("查询任务模板")
    QueryTemplateResponse query(QueryTemplateRequest request);
    @ApidocInterface("测试任务模板")
    TestTemplateResponse test(TestTemplateRequest request);
    @ApidocInterface("拉取任务模板")
    FetchTemplateResponse fetch(FetchTemplateRequest request);
}
