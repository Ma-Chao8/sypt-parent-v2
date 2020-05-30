package com.tianma315.api.core.invoice.rest;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.api.core.controller.BaseRest;
import com.tianma315.api.core.invoice.service.PdaInvoiceService;
import com.tianma315.core.base.Result;
import com.tianma315.core.invoice.domain.vo.DeliverRecordVo;
import com.tianma315.core.invoice.domain.vo.InspectVo;
import com.tianma315.core.invoice.vo.DeliverForm;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


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
 * 货单接口
 * <p>
 * Created by zcm on 2018/5/14.
 */
@RestController
@RequestMapping("/invoice")
public class InvoiceRest extends BaseRest {

    @Autowired
    private PdaInvoiceService pdaInvoiceService;

    /**
     * 扫码发货
     *
     * @param form
     * @return
     */
    @PostMapping("/deliver")
    @RequiresAuthentication
    Result deliver(@RequestBody DeliverForm form) {
        if (pdaInvoiceService.deliver(form, getUserId(), getCompanyId(), getCompany().getProductIdent())) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 发货记录
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/delivered/record")
    @RequiresAuthentication
    Result deliveredRecord(int pageNumber, int pageSize, String searchKey) {
        Page<DeliverRecordVo> page = pdaInvoiceService.deliveredRecord(pageNumber, pageSize, getUserId(),searchKey);
        return Result.ok(page);
    }


    /**
     * 退货（支持多码退货）大小码皆可
     *
     * @param codes
     * @return
     */
    @PostMapping("/returned")
    @RequiresAuthentication
    Result returned(String[] codes) {
        if (codes == null || codes.length == 0)
            return Result.fail("退货码不能为空");
        if (pdaInvoiceService.returned(Arrays.asList(codes), getUserId(), getCompanyId(), getCompany().getProductIdent())) {
            return Result.ok("退货成功");
        }
        return Result.fail();
    }


    /**
     * 稽查，允许使用大小码（仅支持单码稽查）
     *
     * @param code
     * @return
     */
    @GetMapping("/inspect")
    @RequiresAuthentication
    Result inspect(String code) {
        InspectVo inspectVo = pdaInvoiceService.getInspect(getCompanyId(), code, getCompany().getProductIdent());
        if (inspectVo != null) {
            inspectVo.setInspectCode(code);
            return Result.ok(inspectVo);
        }
        return Result.fail();
    }


}
