package com.tianma315.web.company.view;

import com.tianma315.core.company.domain.pojo.Company;
import com.tianma315.core.company.service.CompanyService;
import com.tianma315.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
 * Description
 * <p>
 * Created by zcm on 2019/6/24.
 */
@Controller
@RequestMapping("/company/view")
public class CompanyViewController extends BaseController {

    @Autowired
    private CompanyService companyService;

    /**
     * 列表视图
     *
     * @return
     */
    @GetMapping("/list")
    ModelAndView viewList() {
        return new ModelAndView("company/list");
    }

    /**
     * 添加视图
     *
     * @return
     */
    @GetMapping("/add")
    ModelAndView viewAdd() {
        return new ModelAndView("company/add");
    }

    @GetMapping("/edit/{id}")
    ModelAndView viewEdit(@PathVariable long id) {
        Company company = companyService.getById(id);
        return new ModelAndView("company/edit") {{
            addObject("company", company);
        }};
    }

    /**
     * 将商户Id 通过model域传到绑定产品页面中去
     * @param companyId 商户Id
     * @param model 存储数据
     * @return 内部转发到绑定产品页面
     */
    @GetMapping("/bind/{companyId}")
    String bindProduct(@PathVariable Integer companyId, Model model){
        model.addAttribute("conpanyId",companyId);
        return "company/bind";
    }

}
