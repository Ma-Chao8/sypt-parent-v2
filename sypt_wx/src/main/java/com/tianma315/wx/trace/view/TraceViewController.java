package com.tianma315.wx.trace.view;

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

import com.tianma315.core.company.domain.pojo.Company;
import com.tianma315.core.traceability.service.TraceabilityFileService;
import com.tianma315.core.traceability.vo.TraceInfoVo;
import com.tianma315.core.traceability.vo.TraceabilityFileVO;
import com.tianma315.wx.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Description
 * <p>
 * Created by zcm on 2019/7/2.
 */
@Controller
@RequestMapping("/trace/view")
public class TraceViewController extends BaseController {
    @Autowired
    private TraceabilityFileService traceFileService;

    /**
     * @param code
     * @return
     */
    @GetMapping("/template/{code}")
    ModelAndView loadTemplate(@PathVariable String code, String ident) {
        TraceInfoVo info = traceFileService.getTraceInfo(ident, code);
        if (info == null) {
            return new ModelAndView("message");
        }

        return new ModelAndView(String.format("mould/%s", info.getTheme())) {{
            addObject("tf", info);
        }};
    }


}
