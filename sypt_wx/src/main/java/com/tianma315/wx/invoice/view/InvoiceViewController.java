package com.tianma315.wx.invoice.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("wx/view")
public class InvoiceViewController {

    /**
     * 货单界面
     *
     * @return
     */
    @GetMapping("/invoice")
    ModelAndView invoice() {
        return new ModelAndView("invoice/invoice");
    }

    /**
     * 发货管理界面
     *
     * @return
     */
    @GetMapping("/invoice/manager")
    ModelAndView invoiceManager() {
        return new ModelAndView("invoice/invoice_manager");
    }

    /**
     * 发货列表界面
     *
     * @return
     */
    @GetMapping("/invoice/list")
    ModelAndView invoiceList() {
        return new ModelAndView("invoice/invoice_list");
    }





}
