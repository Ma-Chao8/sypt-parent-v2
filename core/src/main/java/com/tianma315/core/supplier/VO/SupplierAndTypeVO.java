package com.tianma315.core.supplier.VO;


import com.tianma315.core.supplier.domain.SupplierDO;

public class SupplierAndTypeVO extends SupplierDO {
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
