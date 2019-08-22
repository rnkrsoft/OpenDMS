package com.rnkrsoft.opensource.skeleton4j.dms.pages.dms;

import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.template.services.TemplateService;

import javax.web.skeleton4j.annotation.WebImport;
import javax.web.skeleton4j.annotation.WebNamespace;
import javax.web.skeleton4j.annotation.WebPage;
import javax.web.skeleton4j.enums.WebGui;
import javax.web.skeleton4j.enums.WebMode;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@WebPage(priority = 2,
        displayName = "模板管理",
        author = "rnkrsoft.com",
        namespaces = {
                @WebNamespace(imports = {
                        @WebImport(gui = WebGui.FORM, mode = WebMode.QUERY, serviceClass = TemplateService.class, value = "query"),
                        @WebImport(gui = WebGui.MODEL, mode = WebMode.CREATE, serviceClass = TemplateService.class, value = "createTemplate=create")
                })
        }
)
public class TemplatePage {
}
