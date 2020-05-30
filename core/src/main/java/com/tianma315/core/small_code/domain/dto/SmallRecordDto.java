package com.tianma315.core.small_code.domain.dto;

import com.tianma315.core.small_code.domain.SmallRecordDO;

public class SmallRecordDto extends SmallRecordDO {
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
