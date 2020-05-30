package com.tianma315.web.sys.view;

import com.tianma315.core.sys.service.FileService;
import com.tianma315.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 文件上传
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@Controller
@RequestMapping("/file/view")
public class FileViewController extends BaseController {

    @Autowired
    private FileService sysFileService;

    ModelAndView uploadView(){
        return new ModelAndView("sys/upload-file");
    }


}
