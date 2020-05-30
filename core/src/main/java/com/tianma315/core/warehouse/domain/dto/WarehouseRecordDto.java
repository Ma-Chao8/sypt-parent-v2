package com.tianma315.core.warehouse.domain.dto;

import com.tianma315.core.warehouse.domain.WarehouseRecordDO;

public class WarehouseRecordDto extends WarehouseRecordDO {
    private String searchName;
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

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }
}
