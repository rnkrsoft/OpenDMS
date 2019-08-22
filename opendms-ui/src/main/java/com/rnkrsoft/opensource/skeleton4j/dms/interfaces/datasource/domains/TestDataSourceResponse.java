package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.datasource.domains;

import lombok.Data;

import javax.web.doc.AbstractResponse;
import javax.web.doc.annotation.ApidocElement;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
public class TestDataSourceResponse extends AbstractResponse{
    @ApidocElement("结果信息")
    String resultMessage;
}
