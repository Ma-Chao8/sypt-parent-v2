package com.tianma315.wx.invoice.rest;
import com.tianma315.core.base.Result;
import com.tianma315.core.company.domain.pojo.CodeConfig;
import com.tianma315.core.exception.MessageException;

import com.tianma315.wx.common.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TianMa-Android on 2018/8/28.
 */
@RestController
@RequestMapping("/sys")
public class SysRestController extends BaseController {
    private static final String CHARSET_DEFAULT = Charset.forName("UTF-8").name();

    /**
     * 获取系统配置信息
     *
     * @return
     */
    @GetMapping("/code/config")
    Result config() {
        try {
            CodeConfig codeConfig = new CodeConfig();
            codeConfig.setBigCodeConfigs(formatCodeConfig(getCompany().getBigCodeConfig()));
            codeConfig.setSmallCodeConfig(formatCodeConfig(getCompany().getSmallCodeConfig()));
            return Result.ok(codeConfig);
        } catch (MessageException e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }

    /**
     * 格式化匹配规则
     *
     * @param s
     * @return
     */
    private List<String> formatCodeConfig(String s) {
        List<String> list = new ArrayList();
        if (s == null)
            return list;
        s.replace("\r", "");
        for (String c : s.split("\n")) {
            if (c != null && !"".equals(c.trim()))
                list.add(c);
        }
        return list;
    }


    /**
     * 获取应用版本号
     *
     * @return
     */
    @GetMapping("/app/version")
    Result version() {
        try {


            return Result.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }

    /**
     * 更新应用
     *
     * @param response
     */
    @GetMapping("/app/upgrade")
    void upgrade(HttpServletResponse response) {
        try {
            String fileName = "";
            byte[] data = null;
            response.reset();
            response.setCharacterEncoding(CHARSET_DEFAULT);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.addHeader("Content-Length", String.valueOf(data.length));
            response.setContentType("application/octet-stream;charset=UTF-8");
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
        } catch (MessageException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
