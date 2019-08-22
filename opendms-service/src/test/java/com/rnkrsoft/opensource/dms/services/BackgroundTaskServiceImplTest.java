package com.rnkrsoft.opensource.dms.services;

import com.rnkrsoft.framework.test.CreateTable;
import com.rnkrsoft.framework.test.DataSourceTest;
import com.rnkrsoft.opensource.dms.jdbc.entity.DataSourceEntity;
import com.rnkrsoft.opensource.dms.jdbc.entity.TaskEntity;
import com.rnkrsoft.opensource.dms.jdbc.entity.TemplateEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.*;

/**
 * Created by rnkrsoft.com on 2019/7/2.
 */
@ContextConfiguration(locations = "classpath*:BackgroundTaskServiceImplTest.xml")
@CreateTable(entities = {
            TaskEntity.class,
            TemplateEntity.class,
            DataSourceEntity.class
        },
        prefix = "TB"
)
public class BackgroundTaskServiceImplTest extends DataSourceTest {
    @Autowired
    BackgroundTaskService backgroundTaskService;
    @Test
    public void testNewTask() throws Exception {
        backgroundTaskService.newTask(1);
    }
}