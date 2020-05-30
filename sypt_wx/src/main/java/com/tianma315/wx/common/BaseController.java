package com.tianma315.wx.common;

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
import com.tianma315.core.company.service.CompanyService;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.sys.domain.UserDO;
import com.tianma315.core.sys.service.UserService;
import com.tianma315.core.warehouse.domain.WarehouseManagerDO;
import com.tianma315.core.warehouse.domain.vo.WarehouseManagerVo;
import com.tianma315.core.warehouse.service.WarehouseManagerService;
import com.tianma315.core.wxuserinfo.domain.WxUserInfoDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.security.SocialUser;


/**
 * Description
 * <p>
 * Created by zcm on 2019/7/10.
 */
public abstract class BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private WarehouseManagerService warehouseManagerService;
    @Autowired
    private CompanyService companyService;


    protected Logger log = LoggerFactory.getLogger(this.getClass());


    /**
     * 获取微信用户授权信息
     *
     * @return
     */
    protected final WxUserInfoDO getWxUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() != null) {
            if (authentication.getDetails() instanceof WxUserInfoDO) {
                return (WxUserInfoDO) authentication.getDetails();
            }
        }
        throw new MessageException("用户身份认证失败");
    }


    protected final SocialUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new MessageException("用户信息获取失败");
        }

        if (authentication.getPrincipal() instanceof SocialUser) {
            return (SocialUser) authentication.getPrincipal();
        }
        throw new MessageException("用户信息获取失败");
    }

    protected UserDO getUserDo() {
        UserDO userDo = userService.getUserDOByUserName(getUser().getUserId());
        if (userDo == null)
            throw new MessageException("用户信息获取失败");
        return userDo;
    }

    /**
     * 获取仓管信息
     *
     * @return
     */
    protected WarehouseManagerDO getWarehouseManager() {
        WarehouseManagerVo warehouseManager = warehouseManagerService.selectVoByUserId(getUserDo().getId());
        if (warehouseManager == null) {
            throw new MessageException("发货员信息获取失败");
        }

        System.out.println("##############1111id========" + warehouseManager.getCompanyId());


        return warehouseManager;
    }


    protected Company getCompany() {
        Company company = companyService.getById(getWarehouseManager().getCompanyId());
        if (company == null)
            throw new MessageException("商户信息获取失败");
        return company;
    }


}
