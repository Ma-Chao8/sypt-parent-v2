package com.tianma315.core.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.tianma315.core.sys.domain.UserDO;

public class ShiroUtils {
	public static Subject getSubjct() {
		return SecurityUtils.getSubject();
	}
	public static UserDO getUser() {
		return (UserDO)getSubjct().getPrincipal();
	}
	public static Long getUserId() {
		return getUser().getId();
	}
	public static void logout() {
		getSubjct().logout();
	}
}
