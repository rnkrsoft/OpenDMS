package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.template.domains;

import lombok.Data;

import javax.web.doc.AbstractRequest;
import javax.web.doc.annotation.ApidocElement;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
public class ToUpdateTemplateRequest extends AbstractRequest{
    @ApidocElement(value = "模板编号",unique = true, hidden = true)
    Integer templateId;
}
