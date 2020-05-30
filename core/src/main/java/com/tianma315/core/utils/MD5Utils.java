package com.tianma315.core.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class MD5Utils {
    private static final String SALT = "1qazxsw2";
    private static final String ALGORITH_NAME = "md5";
    private static final int HASH_ITERATIONS = 2;

    /**
     * @param salt
     * @param password
     * @return
     */
    public static String encrypt(String salt, String password) {

        if (salt == null){
            throw new NullPointerException("salt can not be a null object");
        }

        if (password == null){
            throw new NullPointerException("password can not be a null object");
        }

        String newPassword = new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(salt + SALT), HASH_ITERATIONS).toHex();
        return newPassword;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        System.out.println(MD5Utils.encrypt("hnjx", "123456"));

    }

}
