package org.springframework.social.wechat.connect;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

import java.util.UUID;

public class WeChatConnectionSignUp implements ConnectionSignUp {

    public String execute(Connection<?> connection) {
        //根据社交用户信息，默认创建用户并返回用户唯一标识
        String uuid = UUID.randomUUID().toString();
        System.out.println("uuid==========="+uuid);
        return uuid;
    }
}