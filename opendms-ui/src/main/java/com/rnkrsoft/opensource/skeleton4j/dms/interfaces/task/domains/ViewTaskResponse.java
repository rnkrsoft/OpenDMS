package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.domains;

import lombok.Data;

import javax.web.doc.AbstractResponse;
import javax.web.doc.annotation.ApidocElement;
import javax.web.doc.enums.ValueDisplayType;
import javax.web.doc.enums.WebLayout;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
public class ViewTaskResponse extends AbstractResponse{
    @ApidocElement(value = "任务编号", unique = true, hidden = false, required = false, layout = WebLayout.LINEAR)
    Integer taskId;
    @ApidocElement(value = "任务名称", required = false, layout = WebLayout.LINEAR)
    String taskName;
    @ApidocElement(value = "SQL语句", valueDisplayType = ValueDisplayType.TEXTAREA, layout = WebLayout.LINEAR)
    String sqlStatement;
    @ApidocElement(value = "进度", required = false, readonly = true)
    Integer progress;
    @ApidocElement(value = "创建者", required = false, readonly = true)
    String createUser;
    @ApidocElement(value = "完成时间", required = false, readonly = true)
    String finishDate;
    @ApidocElement(value = "允许下载次数", required = false, readonly = true)
    Integer allowDownloadCount;
    @ApidocElement(value = "最迟下载日期", required = false, readonly = true)
    String latestDownloadDate;
    @ApidocElement(value = "创建时间", required = false, readonly = true)
    String createDate;
    @ApidocElement(value = "最后更新时间", required = false, readonly = true)
    String lastUpdateDate;
}
