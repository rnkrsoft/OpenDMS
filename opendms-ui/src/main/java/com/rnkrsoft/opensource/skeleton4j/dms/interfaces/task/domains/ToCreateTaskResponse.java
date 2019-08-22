package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.domains;

import com.rnkrsoft.opensource.dms.enums.FileFormatEnum;
import com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.services.TaskService;
import lombok.Data;

import javax.web.doc.AbstractResponse;
import javax.web.doc.annotation.ApidocElement;
import javax.web.doc.enums.ValueDisplayType;
import javax.web.doc.enums.WebDisplayType;
import javax.web.skeleton4j.annotation.WebCascadeInterface;
import javax.web.skeleton4j.annotation.WebResultDisplay;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
public class ToCreateTaskResponse extends AbstractResponse{
    @ApidocElement(value = "模板编号", valueDisplayType = ValueDisplayType.TEXT, hidden = true, unique = true,  interfaces = {
            @WebCascadeInterface(displayName = "创建任务", serviceClass = TaskService.class, value = "createTask=create", resultDisplay = @WebResultDisplay(displayType = WebDisplayType.MSG_BOX))
    })
    Integer templateId;
    @ApidocElement(value = "文件格式", valueDisplayType = ValueDisplayType.SELECTION, enumClass = FileFormatEnum.class, defaults = "1")
    Integer fileFormat;
    @ApidocElement(value = "SQL语句", valueDisplayType = ValueDisplayType.TEXTAREA, readonly = true)
    String sqlStatement;
    @ApidocElement(value = "参数", valueDisplayType = ValueDisplayType.TABLE, required = false)
    final List<ToCreateTaskRecord> arguments = new ArrayList();
}
