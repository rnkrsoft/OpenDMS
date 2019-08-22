package com.rnkrsoft.opensource.dms.services;

import com.rnkrsoft.opensource.dms.enums.FileFormatEnum;
import com.rnkrsoft.opensource.dms.jdbc.dao.TaskDAO;
import com.rnkrsoft.opensource.dms.jdbc.entity.TaskEntity;
import com.rnkrsoft.skeleton4j.theme.layui.template.Template;
import com.rnkrsoft.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rnkrsoft.com on 2019/7/5.
 */
@Slf4j
@Service
public class FileShareServiceImpl implements FileShareService {
    @Autowired
    TaskDAO taskDAO;

    /**
     * 通过验证分享的信息获取下载文件，验证通过使用断点续传方式下载
     *
     * @param taskId
     * @param code
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void download(int taskId, String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //从断点续传参数中提取起始和结束偏移
        String range = request.getHeader("RANGE");
        int start = 0;
        int end = 0;
        if (null != range && range.startsWith("bytes=")) {
            String[] values = range.split("=")[1].split("-");
            start = Integer.parseInt(values[0]);
            end = Integer.parseInt(values[1]);
        }

        TaskEntity taskEntity = taskDAO.selectByPrimaryKey(taskId);
        if (taskEntity == null) {
            log.info("task '{}' is not exists, cause download happens error!", taskId);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            String resource = "/layui/error/404.html";
            Map<String, Object> parameters = new HashMap();
            parameters.put("title", "DMS");
            String html = Template.html(resource, parameters);
            Writer out = null;
            try {
                out = response.getWriter();
                out.append(html);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            return;
        }
        if (!taskEntity.getShareCode().equals(code)) {
            log.info("task '{}' share code '{}', cause download happens error!", taskId, code);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            String resource = "/layui/error/403.html";
            Map<String, Object> parameters = new HashMap();
            parameters.put("title", "DMS");
            String html = Template.html(resource, parameters);
            Writer out = null;
            try {
                out = response.getWriter();
                out.append(html);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            return;
        }
        if (taskEntity.getLatestDownloadDate().compareTo(new Date()) < 0) {
            log.info("task '{}' latest download date '{}', cause download happens error!", taskId, DateUtils.toFullString(taskEntity.getLatestDownloadDate()));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            String resource = "/layui/error/403.html";
            Map<String, Object> parameters = new HashMap();
            parameters.put("title", "DMS");
            String html = Template.html(resource, parameters);
            Writer out = null;
            try {
                out = response.getWriter();
                out.append(html);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            return;
        }
        if (taskEntity.getAllowDownloadCount() < 1) {
            log.info("task '{}' allow download count '{}', cause download happens error!", taskId, taskEntity.getAllowDownloadCount());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            String resource = "/layui/error/403.html";
            Map<String, Object> parameters = new HashMap();
            parameters.put("title", "DMS");
            String html = Template.html(resource, parameters);
            Writer out = null;
            try {
                out = response.getWriter();
                out.append(html);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            return;
        }

        RandomAccessFile raFile = new RandomAccessFile(taskEntity.getGenerateFileName(), "r");

        if (start == 0) {
            int updateCount = taskDAO.decreaseAllowDownloadCount(taskId);
            log.info("task '{}' allow download count decrease", taskId);
            if (updateCount != 1) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                String resource = "/layui/error/403.html";
                Map<String, Object> parameters = new HashMap();
                parameters.put("title", "DMS");
                String html = Template.html(resource, parameters);
                Writer out = null;
                try {
                    out = response.getWriter();
                    out.append(html);
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
                return;
            }
        }
        int requestSize = 0;
        if (end != 0 && end > start) {
            requestSize = end - start + 1;
            response.addHeader("content-length", Integer.toString(requestSize));
        } else {
            requestSize = Integer.MAX_VALUE;
        }

        byte[] buffer = new byte[4096];
        response.setContentType("application/x-download");
        String filename = taskEntity.getTaskName() + "-" + DateUtils.toString(taskEntity.getLastUpdateDate()) + "." + FileFormatEnum.valueOfCode(taskEntity.getFileFormat()).getSuffix();
        filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        ServletOutputStream os = response.getOutputStream();
        int needSize = requestSize;
        raFile.seek(start);
        while (needSize > 0) {
            int len = raFile.read(buffer);
            if (needSize < buffer.length) {
                os.write(buffer, 0, needSize);
            } else {
                os.write(buffer, 0, len);
                if (len < buffer.length) {
                    break;
                }
            }
            needSize -= buffer.length;
        }
        raFile.close();
        os.close();
    }
}
