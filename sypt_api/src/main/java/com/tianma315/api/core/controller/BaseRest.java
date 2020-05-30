package com.tianma315.api.core.controller;

import com.tianma315.api.util.ShiroUtil;
import com.tianma315.core.company.domain.pojo.Company;
import com.tianma315.core.company.service.CompanyService;
import com.tianma315.core.sys.domain.UserDO;
import com.tianma315.core.sys.service.UserService;
import com.tianma315.core.warehouse.service.WarehouseManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BaseRest {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private WarehouseManagerService warehouseManagerService;

    public UserDO getUser() {
        String userName = ShiroUtil.getUserName();
        UserDO user = userService.getUserDOByUserName(userName);
        return user;
    }

    public Long getUserId() {
        UserDO user = getUser();
        return user.getId();
    }

    public Company getCompany() {
        Company company = companyService.getById(getUser().getCompanyId());
        return company;
    }

    public Long getCompanyId() {
        Company company = getCompany();
        return company.getCompanyId();
    }

    public Long getWarehouseId(){
        return warehouseManagerService.selectVoById(getUserId()).getWarehouseId();
    }

    /**
     * @return
     */
    protected Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }

}
