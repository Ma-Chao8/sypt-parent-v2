package com.tianma315.web.base;

import com.tianma315.core.company.domain.pojo.Company;
import com.tianma315.core.company.service.CompanyService;
import com.tianma315.core.base.Result;
import com.tianma315.core.sys.service.UserService;
import com.tianma315.core.utils.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tianma315.core.sys.domain.UserDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * </pre>
 * <small> 2018年2月25日 | Aron</small>
 */
@Controller
public abstract class BaseController {
    private static final String MSG_SUCCESS = "操作成功";
    private static final String MSG_FAIL = "操作失败";

    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;


    protected Logger log = LoggerFactory.getLogger(this.getClass());

    public UserDO getUser() {
        return ShiroUtils.getUser();
    }

    public Long getUserId() {
        return getUser().getId();
    }

    public String getUsername() {
        return getUser().getUsername();
    }

    public Long getCompanyId(){
       //Company company = companyService.getByUserId(getUserId());
        UserDO userDO = userService.selectById(getUserId());
        if (userDO!=null)return userDO.getCompanyId();
       return null;
    }
    public Company getCompany(){
        Company company = companyService.getByUserId(getUserId());
        if (company!=null)return company;
        return null;
    }


    /* result function */

    protected <E> Result<E> success(String msg, E data) {
        Result<E> result = Result.ok(data);
        result.setMsg(msg);
        return result;
    }


    protected <E> Result<E> success(E data) {
        return success(MSG_SUCCESS, data);
    }


    protected <E> Result<E> success() {
        return success(null);
    }


    protected <E> Result<E> fail(String msg, E data) {
        Result<E> result = Result.fail(data);
        result.setMsg(msg);
        return result;
    }


    protected <E> Result<E> fail(E data) {
        return fail(MSG_FAIL, data);
    }


    protected <E> Result<E> fail() {
        return fail(null);
    }

}