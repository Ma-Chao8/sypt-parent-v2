package com.tianma315.core.stock.domain.vo;

import com.tianma315.core.stock.domain.StockDO;

public class StockVO extends StockDO {
    private String productName;
    private String productSpec;
    private String warehouseName;
    private Integer bigNumber;
    private Integer smallNumber;

    public Integer getBigNumber() {
        return bigNumber;
    }

    public void setBigNumber(Integer bigNumber) {
        this.bigNumber = bigNumber;
    }

    public Integer getSmallNumber() {
        return smallNumber;
    }

    public void setSmallNumber(Integer smallNumber) {
        this.smallNumber = smallNumber;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }
}
