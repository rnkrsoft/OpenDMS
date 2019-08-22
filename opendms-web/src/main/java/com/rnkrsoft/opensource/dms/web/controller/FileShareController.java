package com.rnkrsoft.opensource.dms.web.controller;

import com.rnkrsoft.opensource.dms.services.FileShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by rnkrsoft.com on 2019/7/5.
 */
@Controller
public class FileShareController {
    @Autowired
    FileShareService fileShareService;
    @RequestMapping("/share")
    public void download(String id, String code, HttpServletRequest request, HttpServletResponse response){
        try {
            fileShareService.download(Integer.valueOf(id), code, request, response);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
