package com.tianma315.core.coderecord.vo;

public class CodeRecordCountVO {
    private Integer nowDay;
    private Integer num;

    public Integer getNowDay() {
        return nowDay;
    }

    public void setNowDay(Integer nowDay) {
        this.nowDay = nowDay;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "CodeRecordCountVO{" +
                "nowDay=" + nowDay +
                ", num=" + num +
                '}';
    }
}
