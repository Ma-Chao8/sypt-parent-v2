package com.tianma315.core.product.domain.vo;


import com.tianma315.core.product.domain.pojo.ProductFieldDO;

public class ProductFieldVO extends ProductFieldDO {
    private String attrFieldName;
    private Integer type;

    public String getAttrFieldName() {
        return attrFieldName;
    }

    public void setAttrFieldName(String attrFieldName) {
        this.attrFieldName = attrFieldName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
