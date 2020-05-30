package com.tianma315.core.warehouse.domain.dto;

import com.tianma315.core.warehouse.domain.WarehouseDO;

/**
 * create By Lgc on 2019.09.19
 */
public class WarehouseDto extends WarehouseDO {
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
