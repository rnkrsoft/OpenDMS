package com.rnkrsoft.opensource.skeleton4j.dms.interfaces.task.domains;

import lombok.Data;

import javax.web.doc.AbstractResponse;
import javax.web.doc.annotation.ApidocElement;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
@Data
public class DownloadTaskResponse extends AbstractResponse{
    @ApidocElement("分享地址")
    String shareUrl;
}
