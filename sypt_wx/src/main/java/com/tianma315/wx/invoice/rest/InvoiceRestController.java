package com.tianma315.wx.invoice.rest;


import com.tianma315.core.base.Result;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.invoice.domain.vo.InspectVo;
import com.tianma315.core.invoice.domain.vo.InvoiceVO;
import com.tianma315.core.invoice.vo.DeliverForm;
import com.tianma315.wx.common.BaseController;
import com.tianma315.wx.invoice.service.WxInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
 * Created by zcm on 2019/8/24.
 */
@RestController
@RequestMapping("/invoice")
public class InvoiceRestController extends BaseController{
  @Autowired
  WxInvoiceService invoiceService;

    /**
     * 扫码发货
     *
     * @param form
     * @return
     */
    @PostMapping("/deliver")
    Result deliver(@RequestBody DeliverForm form) {
        try {
            if (invoiceService.deliver(form,
                    getUserDo().getId(),
                    getCompany().getCompanyId(),
                    getCompany().getProductIdent())) {
                return Result.ok("已成功发货");
            }
            return Result.fail();
        } catch (MessageException e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail();
    }
  
  
  
  
  
    /**
     * @return
     */
    @GetMapping("/deliver/list")
    Result deliverList(int page) {

        try {
            List<InvoiceVO> list = invoiceService.deliverList(getUserDo().getId(), page);
            return Result.ok(list);
        } catch (MessageException e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
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
    Result inspect(String code) {
        try {
            InspectVo inspectVo = invoiceService.getInspect(getCompany().getCompanyId(),
                    code,
                    getCompany().getProductIdent());

            if (inspectVo != null) {
                inspectVo.setInspectCode(code);
                return Result.ok(inspectVo);
            }
            return Result.fail();
        } catch (MessageException e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

    /**
     * 退货（支持多码退货）大小码皆可
     *
     * @param codes
     * @return
     */
    @PostMapping("/returned")
    Result returned(String[] codes) {
        try {
            if (codes == null || codes.length == 0)
                return Result.fail("退货码不能为空");
            if (invoiceService.returned(Arrays.asList(codes), getUserDo().getId(), getCompany().getCompanyId(), getCompany().getProductIdent())) {
                return Result.ok("退货成功");
            }
            return Result.fail();
        } catch (MessageException e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

}
