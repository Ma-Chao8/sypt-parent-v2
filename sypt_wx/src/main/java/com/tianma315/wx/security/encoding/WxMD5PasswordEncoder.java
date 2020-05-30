package com.tianma315.wx.security.encoding;

import com.tianma315.core.utils.MD5Utils;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public class WxMD5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encodePassword(String rawPass, Object salt) {
        String encodePassword = MD5Utils.encrypt(String.valueOf(salt), rawPass);
        return encodePassword;
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        String encodePassword = MD5Utils.encrypt(String.valueOf(salt), rawPass);
        return encPass.equals(encodePassword);
    }
}
