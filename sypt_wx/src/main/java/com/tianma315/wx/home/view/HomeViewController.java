package com.tianma315.wx.home.view;

import com.tianma315.core.company.domain.pojo.Company;
import com.tianma315.core.company.service.CompanyService;
import com.tianma315.core.sys.domain.MenuDO;
import com.tianma315.core.sys.service.MenuService;
import com.tianma315.wx.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
 * Created by zcm on 2019/8/22.
 */
@Controller
public class HomeViewController extends BaseController {

    @Autowired
    private MenuService menuService;


    @Autowired
    private CompanyService companyService;

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping(value = {"/", "/index", "/home"})
    ModelAndView index() {
        //查询菜单
        List<MenuDO> menus = menuService.getMenuByUsername(getUser().getUsername());
        //查询机构
        Company byId = companyService.getById(getUserDo().getCompanyId());
        ModelAndView view = new ModelAndView("index");
        view.addObject("username",getUser().getUsername());
        view.addObject("company",byId);
        view.addObject("menus", menus);
        return view;
    }


}
