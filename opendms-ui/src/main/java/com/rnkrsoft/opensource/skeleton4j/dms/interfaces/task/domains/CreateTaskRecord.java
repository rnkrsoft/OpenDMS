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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRecord implements Serializable{
    @ApidocElement("参数名")
    String argName;
    @ApidocElement("参数值")
    String argValue;
}
