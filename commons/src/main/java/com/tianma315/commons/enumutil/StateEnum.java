package com.tianma315.commons.enumutil;

public enum StateEnum {
    not_del(0,"未删除"),
    del(1,"已删除"),
    usable(1,"可用"),
    prohibit(0,"禁用");

    private Integer index;
    private String message;

    StateEnum(Integer index, String message) {
        this.index = index;
        this.message = message;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
