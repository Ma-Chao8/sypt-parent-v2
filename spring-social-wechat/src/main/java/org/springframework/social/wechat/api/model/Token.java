package org.springframework.social.wechat.api.model;

import java.util.Date;


/**
 * 微信全局token
 */
public class Token extends ErrorMsg {
    private String access_token;
    private long expires_in;
    private long create;

    public Token() {
        this(new Date().getTime());
    }

    public Token(long create) {
        this.create = create;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public long getCreate() {
        return create;
    }

    public void setCreate(long create) {
        this.create = create;
    }

    /**
     * 是否过期
     *
     * @return
     */
    public boolean hasExpiresIn() {
        long current = new Date().getTime();
        long end = getCreate() + getExpires_in() * 1000 - 20 * 60 * 1000;
        return current > end;
    }
}
