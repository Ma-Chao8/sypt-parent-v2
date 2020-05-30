package com.tianma315.api.core.code;


import com.tianma315.api.core.controller.BaseRest;
import com.tianma315.core.base.Result;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.invoice.vo.ScanCodeVo;
import com.tianma315.core.small_code.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * Created by zcm on 2019/9/3.
 */
@RestController
@RequestMapping("/code")
public class CodeRestController extends BaseRest {

    @Autowired
    private CodeService codeService;

    /**
     * 系统码校验，检验用户扫描的码是否是系统的的有效码，将非法的码返回给用户
     *
     * @return
     */
    @PostMapping("/validate")
    Result codeValidate(@RequestBody List<ScanCodeVo> codes) {
        try {
            if (codeService.validate(getCompanyId(), getCompany().getProductIdent(), codes)) {
                return Result.ok();
            }
            return Result.fail();
        } catch (MessageException e) {
            return Result.fail(e.getMessage(), e.getData());
        }
    }

    /**
     * 判断码是否可以发货
     *
     * @param codes
     * @return
     */
    @PostMapping("/deliverable")
    Result codeDeliverable(@RequestBody List<ScanCodeVo> codes) {
        try {
            if (codeService.allowDelivered(getCompanyId(), getCompany().getProductIdent(), codes)) {
                return Result.ok();
            }
            return Result.fail();
        } catch (MessageException e) {
            return Result.fail(e.getMessage(), e.getData());
        }
    }

    /**
     * 判断码是否可退货
     *
     * @param codes
     * @return
     */
    @PostMapping("/returnable")
    Result codeReturnable(@RequestBody List<String> codes) {
        try {
            if (codeService.returnable(getCompanyId(), getCompany().getProductIdent(), codes)) {
                return Result.ok();
            }
            return Result.fail();
        } catch (MessageException e) {
            return Result.fail(e.getMessage(), e.getData());
        }
    }

    /**
     * 判断码是否可稽查
     *
     * @param codes
     * @return
     */
    @PostMapping("/inspectable")
    Result codeInspectable(@RequestBody List<String> codes) {
        try {
            if (codeService.inspectable(getCompanyId(), getCompany().getProductIdent(), codes)) {
                return Result.ok();
            }
            return Result.fail();
        } catch (MessageException e) {
            return Result.fail(e.getMessage(), e.getData());
        }
    }
}
