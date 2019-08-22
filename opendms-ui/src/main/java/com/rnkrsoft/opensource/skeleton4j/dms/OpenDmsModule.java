package com.rnkrsoft.opensource.skeleton4j.dms;

import javax.web.skeleton4j.annotation.Skeleton4jModule;
import javax.web.skeleton4j.enums.InterfaceCall;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Skeleton4jModule(name = "DMS",
        desc = "开源DMS管理系统",
        interfacePackages = {
                "com.rnkrsoft.opensource.skeleton4j.dms.interfaces"
        },
        pagePackages = {
                "com.rnkrsoft.opensource.skeleton4j.dms.pages"
        },
        call = InterfaceCall.LOCAL
)
public interface OpenDmsModule {
}
