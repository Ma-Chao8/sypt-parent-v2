package com.tianma315.web.statistics.view;

import com.tianma315.web.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


//                   _ooOoo_
//                  o8888888o
//                  88" . "88
//                  (| -_- |)
//                  O\  =  /O
//               ____/`---'\____
//             .'  \\|     |//  `.
//            /  \\|||  :  |||//  \
//           /  _||||| -:- |||||-  \
//           |   | \\\  -  /// |   |
//           | \_|  ''\---/''  |   |
//           \  .-\__  `-`  ___/-. /
//         ___`. .'  /--.--\  `. . __
//      ."" '<  `.___\_<|>_/___.'  >'"".
//     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//     \  \ `-.   \_ __\ /__ _/   .-` /  /
//======`-.____`-.___\_____/___.-`____.-'======
//                   `=---='
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//         佛祖保佑       永无BUG

/**
 * 统计数据视图
 * <p>
 * Created by zcm on 2019/7/2.
 */
@Controller
@RequestMapping("/statistics/view")
public class StatisticsViewController extends BaseController {

    /**
     * 微信用户统计视图
     *
     * @return
     */
    @GetMapping("/wechat")
    ModelAndView viewWechat() {
        return new ModelAndView("statistics/wechat-users");
    }

    /**
     * 用户区域分布统计视图
     *
     * @return
     */
    @GetMapping("/user/area")
    ModelAndView userArea() {
        return new ModelAndView("statistics/user-area");
    }

    /**
     * 用户扫码量统计
     *
     * @return
     */
    @GetMapping("/user/scan")
    ModelAndView userScan() {
        return new ModelAndView("statistics/user-scan");
    }


    /**
     *
     *
     * @return
     */
    @GetMapping("/user/repurchase")
    ModelAndView userRepurchase() {
        return new ModelAndView("statistics/user-repurchase");
    }


    /**
     * 跳转订单统计页面
     */
    @GetMapping( "/invoice/count")
    public String  invoiceCount(){
        return "statistics/invoice";
    }

    /**
     * 发货量统计
     */
    @GetMapping("/invoiceProductCode/count")
    public String  invoiceProductCodeCount(){
        return "statistics/invoice-product-code";
    }

}
