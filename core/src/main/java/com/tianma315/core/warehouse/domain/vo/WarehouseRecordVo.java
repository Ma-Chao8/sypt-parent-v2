package com.tianma315.core.warehouse.domain.vo;

import com.tianma315.core.warehouse.domain.WarehouseRecordCodeDO;
import com.tianma315.core.warehouse.domain.WarehouseRecordDO;

import java.util.List;

public class  WarehouseRecordVo extends WarehouseRecordDO {
    private List<WarehouseRecordCodeDO> warehouseRecordCode;
    private String username;
    private String productName;
    private String productSpec;
    private String bigCode;
    private String warehouseName;

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getBigCode() {
        return bigCode;
    }

    public void setBigCode(String bigCode) {
        this.bigCode = bigCode;
    }

    public List<WarehouseRecordCodeDO> getWarehouseRecordCode() {
        return warehouseRecordCode;
    }

    public void setWarehouseRecordCode(List<WarehouseRecordCodeDO> warehouseRecordCode) {
        this.warehouseRecordCode = warehouseRecordCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
