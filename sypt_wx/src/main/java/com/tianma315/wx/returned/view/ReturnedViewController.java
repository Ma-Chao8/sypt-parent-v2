package com.tianma315.wx.returned.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * by Lgc on 2019/9/4
 */
@Controller
@RequestMapping("/wx/view")
public class ReturnedViewController {

    /**
     * 退货界面
     *
     * @return
     */
    @GetMapping("/returned")
    ModelAndView login() {
        return new ModelAndView("returned/returned");
    }
}
