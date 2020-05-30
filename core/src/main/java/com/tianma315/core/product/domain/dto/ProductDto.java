package com.tianma315.core.product.domain.dto;

import com.tianma315.core.product.domain.pojo.Product;

/**
 * By Lgc ON 2019/09/19
 */
public class ProductDto extends Product {
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
