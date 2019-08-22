package com.rnkrsoft.opensource.dms.config;

import com.rnkrsoft.framework.config.annotation.DynamicConfig;
import com.rnkrsoft.framework.config.annotation.DynamicParam;
import lombok.Data;

/**
 * Created by rnkrsoft.com on 2019/7/5.
 */
@Data
@DynamicConfig
public class DmsConfig {
    @DynamicParam(value = "${dms.domain}", desc = "DMS域名")
    String domain;
}
