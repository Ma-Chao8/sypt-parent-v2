package com.tianma315.api.shiro;

import com.tianma315.api.shiro.JWTToken;
import com.tianma315.api.util.JWTUtil;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.sys.domain.UserDO;
import com.tianma315.core.sys.service.MenuService;
import com.tianma315.core.sys.service.UserService;
import com.tianma315.core.utils.SpringContextHolder;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA
 *
 * @Author yuanhaoyue swithaoy@gmail.com
 * @Description 自定义 Realm
 * @Date 2018-04-09
 * @Time 16:58
 */
@Component
public class CustomRealm extends AuthorizingRealm {
    private final UserService userService;

    @Autowired
    public CustomRealm(UserService userService) {
        this.userService = userService;
    }

    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————身份认证方法————");
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null || !JWTUtil.verify(token, username)) {
            throw new AuthenticationException("token认证失败！");
        }
        String password = userService.getPassword(username);
        if (password == null) {
            throw new MessageException("该用户不存在！");
        }
        return new SimpleAuthenticationInfo(token, token, "MyRealm");
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("————权限认证————");
        String username = JWTUtil.getUsername(principals.toString());
        UserDO userDO = userService.getUserDOByUserName(username);
//        Long userId = ShiroUtils.getUserId();
        MenuService menuService = SpringContextHolder.getBean(MenuService.class);
        Set<String> perms = menuService.listPerms(userDO.getId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(perms);
        return info;
    }
}