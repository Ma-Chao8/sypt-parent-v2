package com.tianma315.wx.inspect.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/wx/view")
public class InspectViewController {

    /**
     * 稽查界面
     *
     * @return
     */
    @GetMapping("/inspect")
    ModelAndView login() {
        return new ModelAndView("inspect");
    }


}
