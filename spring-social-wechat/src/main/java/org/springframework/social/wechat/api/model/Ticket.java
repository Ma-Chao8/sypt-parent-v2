package org.springframework.social.wechat.api.model;

import java.util.Date;

/**
 * 微信ticket
 */
public class Ticket extends ErrorMsg {


    /**
     * errcode : 0
     * errmsg : ok
     * ticket : bxLdikRXVbTPdHSM05e5u5sUoXNKdvsdshFKA
     * expires_in : 7200
     */

    private String ticket;
    private long expires_in;
    private long create;

    public Ticket() {
        this(new Date().getTime());
    }

    public Ticket(long create) {
        this.create = create;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
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
