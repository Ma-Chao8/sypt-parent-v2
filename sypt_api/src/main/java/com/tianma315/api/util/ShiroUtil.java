package com.tianma315.api.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class ShiroUtil {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }
    public static String getUserName() {
        String username = JWTUtil.getUsername(getSubject().getPrincipal().toString());
        return username;
    }
    public static void logout() {
        getSubject().logout();
    }
}
