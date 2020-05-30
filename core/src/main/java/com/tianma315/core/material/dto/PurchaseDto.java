package com.tianma315.core.material.dto;

import com.tianma315.core.material.domain.PurchaseDO;

public class PurchaseDto extends PurchaseDO {
    private String sortName;
    private String sortOrder;

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
