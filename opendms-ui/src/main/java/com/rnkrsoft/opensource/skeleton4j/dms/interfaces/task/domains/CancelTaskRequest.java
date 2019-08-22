package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.domains;

import lombok.Data;

import javax.web.doc.annotation.ApidocElement;
import javax.web.skeleton4j.session.LoginUserId;
import java.io.Serializable;

/**
 * Created by rnkrsoft.com on 2019/7/5.
 */
@Data
public class CancelTaskRequest implements Serializable, LoginUserId{
    @ApidocElement(value = "任务编号", unique = true, hidden = true)
    Integer taskId;
    String loginUserId;
}
