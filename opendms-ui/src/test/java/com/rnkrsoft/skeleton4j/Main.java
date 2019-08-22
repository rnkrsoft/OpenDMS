package com.rnkrsoft.skeleton4j;

import com.rnkrsoft.skeleton4j.authority.mock.MockAuthorityExtractor;
import com.rnkrsoft.skeleton4j.authority.mock.MockAuthorityService;
import com.rnkrsoft.opensource.skeleton4j.dms.OpenDmsModule;
import com.rnkrsoft.skeleton4j.pool.SimpleModulePool;
import com.rnkrsoft.skeleton4j.theme.layui.LayuiTheme;

import javax.web.skeleton4j.boot.Skeleton4jApplicationLoader;
import javax.web.skeleton4j.boot.annotation.Skeleton4jApplication;
import javax.web.skeleton4j.enums.RuntimeEnvironment;

/**
 * Created by rnkrsoft.com on 2018/11/8.
 */
@Skeleton4jApplication(
        app = "openDMS",
        appDesc = "OpenDMS",
        env = RuntimeEnvironment.DEV,
        modules = {
                OpenDmsModule.class
        },
        themes = LayuiTheme.class,
        index = "/",
        defaultTheme = LayuiTheme.class,
        authorityService = MockAuthorityService.class,
        authorityExtractor = MockAuthorityExtractor.class,
        skeleton4jService = StandardSkeleton4jService.class,
        modulePool = SimpleModulePool.class,
        serverPort = 8080
)
public class Main {

    public static void main(String[] args) {
        Skeleton4jApplicationLoader.runWith(Main.class, args);
    }
}
