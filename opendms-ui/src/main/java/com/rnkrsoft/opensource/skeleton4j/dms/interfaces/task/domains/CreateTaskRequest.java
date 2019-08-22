package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.domains;

import com.rnkrsoft.opensource.dms.enums.FileFormatEnum;
import lombok.Data;

import javax.web.doc.annotation.ApidocElement;
import javax.web.doc.enums.ValueDisplayType;
import javax.web.skeleton4j.session.LoginUserId;
import javax.web.skeleton4j.session.LoginUserName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
public class CreateTaskRequest implements Serializable, LoginUserId, LoginUserName{
    @ApidocElement(value = "模板编号", valueDisplayType = ValueDisplayType.TEXT, unique = true)
    Integer templateId;
    @ApidocElement(value = "文件格式", valueDisplayType = ValueDisplayType.SELECTION, enumClass = FileFormatEnum.class)
    Integer fileFormat;
    @ApidocElement(value = "参数", valueDisplayType = ValueDisplayType.TABLE, required = false)
    final List<CreateTaskRecord> arguments = new ArrayList();
    String loginUserId;
    String loginUserName;
}
