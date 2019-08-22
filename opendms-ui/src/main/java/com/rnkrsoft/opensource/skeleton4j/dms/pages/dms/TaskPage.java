package com.rnkrsoft.opensource.skeleton4j.dms.pages.dms;

import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.services.TaskService;

import javax.web.skeleton4j.annotation.WebImport;
import javax.web.skeleton4j.annotation.WebNamespace;
import javax.web.skeleton4j.annotation.WebPage;
import javax.web.skeleton4j.enums.WebGui;
import javax.web.skeleton4j.enums.WebMode;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@WebPage(priority = 1,
        displayName = "任务管理",
        author = "rnkrsoft.com",
        namespaces = {
                @WebNamespace(imports = {
                        @WebImport(gui = WebGui.FORM, mode = WebMode.QUERY, serviceClass = TaskService.class, value = "query")
                })
        }
)
public class TaskPage {
}
