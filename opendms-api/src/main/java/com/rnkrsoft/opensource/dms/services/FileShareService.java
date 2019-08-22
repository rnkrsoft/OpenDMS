package com.rnkrsoft.opensource.dms.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by rnkrsoft.com on 2019/7/5.
 * 文件分享服务
 */
public interface FileShareService {
    /**
     * 下载文件
     * @param taskId
     * @param code
     * @param request
     * @param response
     */
    void download(int taskId, String code, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
