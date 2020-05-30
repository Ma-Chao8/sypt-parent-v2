package com.tianma315.web.coderecord.view;

import com.tianma315.core.company.domain.pojo.Company;
import com.tianma315.core.company.service.CompanyService;
import com.tianma315.web.base.BaseController;
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
 * Created by zcm on 2019/9/24.
 */
@Controller
@RequestMapping("code/view")
public class CodeViewController extends BaseController {

    @Autowired
    private CompanyService companyService;


    @RequestMapping("/out/record")
    ModelAndView codeOutRecord() {
        ModelAndView view = new ModelAndView("coderecord/codeOutRecord");
        return view;
    }


    @RequestMapping("/out/record/add")
    ModelAndView codeOutRecordAdd() {
        ModelAndView view = new ModelAndView("coderecord/codeOutAdd");
        List<Company> companies = companyService.getAll();
        view.addObject("companies", companies);
        return view;
    }

}
