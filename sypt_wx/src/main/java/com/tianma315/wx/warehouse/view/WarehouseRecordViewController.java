package com.tianma315.wx.warehouse.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * By lgc on 2019/09/05
 */
@Controller
@RequestMapping("/wx/view")
public class WarehouseRecordViewController {
    /**
     * 跳转扫码入库界面
     * @return
     */
    @GetMapping("/warehouseRecord")
    ModelAndView wareRecord(){
        return new ModelAndView("invoice/warehouse_record");
    }
}
