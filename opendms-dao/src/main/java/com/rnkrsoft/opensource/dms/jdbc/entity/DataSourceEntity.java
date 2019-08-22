package com.rnkrsoft.opensource.dms.jdbc.entity;

import com.rnkrsoft.framework.orm.PrimaryKey;
import com.rnkrsoft.framework.orm.PrimaryKeyStrategy;
import com.rnkrsoft.framework.orm.jdbc.*;
import lombok.*;

/**
 * Created by rnkrsoft.com on 2019/6/30.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "DATA_SOURCE", prefix = "TB", suffix = "")
@Comment("数据源信息表")
public class DataSourceEntity extends BaseEntity {
    @PrimaryKey(strategy = PrimaryKeyStrategy.IDENTITY)
    @NumberColumn(name = "DATA_SOURCE_ID", nullable = false)
    @Comment("数据源编号")
    Integer dataSourceId;
    @StringColumn(name = "DATA_SOURCE_NAME", nullable = false)
    @Comment("数据源名称")
    String dataSourceName;
    @NumberColumn(name = "DATA_SOURCE_TYPE", nullable = false)
    @Comment("数据源类型")
    Integer dataSourceType;
    @StringColumn(name = "HOST", nullable = false)
    @Comment("地址")
    String host;
    @NumberColumn(name = "PORT", nullable = false)
    @Comment("端口")
    Integer port;
    @StringColumn(name = "DRIVER_CLASS_NAME", nullable = false)
    @Comment("驱动类名")
    String driverClassName;
    @StringColumn(name = "SCHEMA_NAME", nullable = false)
    @Comment("数据库名")
    String schemaName;
    @StringColumn(name = "USER_NAME", nullable = false)
    @Comment("用户名")
    String username;
    @StringColumn(name = "PASSWORD", nullable = false)
    @Comment("密码")
    String password;
    @StringColumn(name = "REMARK", nullable = true)
    @Comment("备注")
    String remark;
    @NumberColumn(name = "DATA_SOURCE_STATUS", nullable = false, defaultValue = "1")
    @Comment("状态")
    Integer status;
}
