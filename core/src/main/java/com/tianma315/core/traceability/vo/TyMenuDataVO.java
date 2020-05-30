package com.tianma315.core.traceability.vo;

import com.tianma315.core.traceability.domain.TyMenuDataDO;

import java.util.List;

public class TyMenuDataVO extends TyMenuDataDO {
    private  String contentName;
    private  List<String> pics;

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }
}
