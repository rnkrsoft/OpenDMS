package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.template.domains;

import lombok.Data;

import javax.web.doc.annotation.ApidocElement;
import java.io.Serializable;

/**
 * Created by rnkrsoft.com on 2019/8/21.
 */
@Data
public class DeleteTemplateRequest implements Serializable{
    @ApidocElement(value = "模板编号",unique = true, hidden = true)
    Integer templateId;
}
