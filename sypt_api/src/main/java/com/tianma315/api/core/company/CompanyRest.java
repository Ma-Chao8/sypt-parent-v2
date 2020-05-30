package com.tianma315.api.core.company;

import com.tianma315.api.core.controller.BaseRest;
import com.tianma315.core.base.Result;
import com.tianma315.core.company.domain.pojo.Company;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/company")
public class CompanyRest extends BaseRest {

    @GetMapping("/getCompany")
    @RequiresAuthentication
    public Result<Company> getCompanyConfig(){
        Company company = getCompany();
        return Result.ok(company);
    }
}
