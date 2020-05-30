package com.tianma315.core.small_code.vo;

import com.tianma315.core.small_code.domain.SmallRecordDO;

import java.util.List;

public class SmallRecordVO extends SmallRecordDO {
    private Integer type;

    private List<String> smallCodes;

    private Long warehouseId;

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<String> getSmallCodes() {
        return smallCodes;
    }

    public void setSmallCodes(List<String> smallCodes) {
        this.smallCodes = smallCodes;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

