package com.tianma315.web.invoice.controller;

import com.tianma315.core.invoice.domain.vo.InspectVo;
import com.tianma315.core.invoice.service.InvoiceService;
import com.tianma315.core.base.Result;
import com.tianma315.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/invoice/inspect")
public class InspectController extends BaseController {
  @Autowired
  InvoiceService invoiceService;

 @GetMapping()
 String inspect(){
     return "/invoice/inspect";
 }

    @GetMapping("/select/{code}")
    @ResponseBody
    public Result<InspectVo> inspectVoResult (@PathVariable("code")String code){
        if (code == null)
            return Result.fail("稽查码不不能为空",null);
        InspectVo inspect = invoiceService.getInspect(getCompanyId(), code, getCompany().getProductIdent());
        inspect.setInspectCode(code);
        return Result.ok(inspect);
    }
}
