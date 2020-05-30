package com.tianma315.core.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class ResponseUtil {
    public static HttpServletResponse getResponse(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.setContentType("application/msexcel");
        response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
        return response;
    }
}