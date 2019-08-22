package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.web.doc.annotation.ApidocElement;
import java.io.Serializable;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToCreateTaskRecord implements Serializable{
    @ApidocElement(value = "参数名", readonly = true, required = false)
    String argName;
    @ApidocElement(value = "参数格式", readonly = true, required = false)
    String argFormat;
    @ApidocElement("参数值")
    String argValue;
}
