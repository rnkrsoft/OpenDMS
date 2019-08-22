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
@Table(name = "TEMPLATE", prefix = "TB", suffix = "")
@Comment("模板信息表")
public class TemplateEntity extends BaseEntity{
    @PrimaryKey(strategy = PrimaryKeyStrategy.IDENTITY)
    @NumberColumn(name = "TEMPLATE_ID", nullable = false)
    @Comment("模板编号")
    Integer templateId;
    @StringColumn(name = "TEMPLATE_NAME", nullable = false)
    @Comment("模板名称")
    String templateName;
    @StringColumn(name = "SQL_STATEMENT", nullable = false)
    @Comment("SQL语句")
    String sqlStatement;
    @NumberColumn(name = "MAX_ROW_SIZE", nullable = false, defaultValue = "1000")
    @Comment("最大行数")
    Integer maxRowSize;
    @NumberColumn(name = "DATA_SOURCE_ID", nullable = false)
    @Comment("数据源编号")
    Integer dataSourceId;
    @NumberColumn(name = "TEMPLATE_STATUS", nullable = false, defaultValue = "1")
    @Comment("模板状态")
    Integer templateStatus;
}
