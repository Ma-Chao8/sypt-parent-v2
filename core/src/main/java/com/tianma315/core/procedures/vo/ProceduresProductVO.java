package com.tianma315.core.procedures.vo;

import java.util.List;

public class ProceduresProductVO {
    private Integer procedureId;
    private List<Integer> productIdList;

    public Integer getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(Integer procedureId) {
        this.procedureId = procedureId;
    }

    public List<Integer> getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(List<Integer> productIdList) {
        this.productIdList = productIdList;
    }
}
