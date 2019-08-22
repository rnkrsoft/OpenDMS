package com.rnkrsoft.opensource.dms.jdbc.entity;

import com.rnkrsoft.framework.orm.PrimaryKey;
import com.rnkrsoft.framework.orm.PrimaryKeyStrategy;
import com.rnkrsoft.framework.orm.jdbc.*;
import com.rnkrsoft.opensource.dms.enums.FileFormatEnum;
import com.rnkrsoft.opensource.dms.enums.TaskStatusEnum;
import lombok.*;

import java.util.Date;

/**
 * Created by rnkrsoft.com on 2019/6/30.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "TASK", prefix = "TB", suffix = "")
@Comment("任务信息表")
public class TaskEntity extends BaseEntity {
    @PrimaryKey(strategy = PrimaryKeyStrategy.IDENTITY)
    @NumberColumn(name = "TASK_ID", nullable = false)
    @Comment("任务编号")
    Integer taskId;
    @StringColumn(name = "TASK_NAME", nullable = false)
    @Comment("任务名称")
    String taskName;
    @NumberColumn(name = "TEMPLATE_ID", nullable = false)
    @Comment("模板编号")
    Integer templateId;
    @StringColumn(name = "TEMPLATE_SQL_STATEMENT", nullable = false)
    @Comment("模板中定义的SQL语句")
    String templateSqlStatement;
    @StringColumn(name = "SQL_STATEMENT", nullable = false)
    @Comment("实际执行的SQL语句,仅作为展示用途，真实的SQL语句通过占位符和参数实现")
    String sqlStatement;
    @StringColumn(name = "TASK_ARGUMENTS", nullable = false)
    @Comment("任务参数")
    String taskArguments;
    @NumberColumn(name = "FILE_FORMAT", nullable = false, enumClass = FileFormatEnum.class, defaultValue = "1")
    @Comment("文件格式")
    Integer fileFormat;
    @NumberColumn(name = "TASK_PROGRESS", nullable = false, defaultValue = "0")
    @Comment("进度（%）")
    Integer taskProgress;
    @StringColumn(name = "CREATE_USER_ID", nullable = false)
    @Comment("创建用户号")
    String createUserId;
    @StringColumn(name = "CREATE_USER_NAME", nullable = false)
    @Comment("创建用户名")
    String createUserName;
    @StringColumn(name = "FINISH_DATE", nullable = true)
    @Comment("完成日期 yyy/MM/dd hh:mm:ss")
    String finishDate;
    @NumberColumn(name = "TASK_STATUS", nullable = false, enumClass = TaskStatusEnum.class)
    @Comment("任务状态")
    Integer taskStatus;
    @StringColumn(name = "FAILURE_CAUSE", nullable = true, type = StringType.TEXT)
    @Comment("失败原因")
    String failureCause;
    @StringColumn(name = "GENERATE_FILE_NAME", nullable = true, type = StringType.TEXT)
    @Comment("生成文件")
    String generateFileName;
    @StringColumn(name = "SHARE_CODE", nullable = true, type = StringType.TEXT)
    @Comment("分享验证码")
    String shareCode;
    @NumberColumn(name = "ALLOW_DOWNLOAD_COUNT", nullable = true, type = NumberType.INTEGER)
    @Comment("允许下载次数")
    Integer allowDownloadCount;
    @Comment("最迟下载日期")
    @DateColumn(name = "latest_download_date", nullable = false, type = DateType.TIMESTAMP, currentTimestamp = true)
    Date latestDownloadDate;
}
