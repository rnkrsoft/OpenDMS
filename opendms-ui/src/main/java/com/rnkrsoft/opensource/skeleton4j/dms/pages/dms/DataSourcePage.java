package com.rnkrsoft.opensource.skeleton4j.dms.pages.dms;

import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.services.DataSourceService;

import javax.web.skeleton4j.annotation.WebImport;
import javax.web.skeleton4j.annotation.WebNamespace;
import javax.web.skeleton4j.annotation.WebPage;
import javax.web.skeleton4j.enums.WebGui;
import javax.web.skeleton4j.enums.WebMode;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@WebPage(priority = 3,
        displayName = "数据源管理",
        author = "rnkrsoft.com",
        namespaces = {
                @WebNamespace(imports = {
                        @WebImport(gui = WebGui.FORM, mode = WebMode.QUERY, serviceClass = DataSourceService.class, value = "query"),
                        @WebImport(gui = WebGui.MODEL, mode = WebMode.CREATE, serviceClass = DataSourceService.class, value = "create")
                })
        }
)
public class DataSourcePage {
}
