package com.tianma315.core.invoice.domain.vo;

import com.tianma315.core.invoice.domain.ReturnedDO;


import java.util.List;

/**
 * Created by TianMa-Android on 2018/9/1.
 */
public class ReturnedVO extends ReturnedDO {

    private List<ReturnedProductVO> products;
    private String agentName;
    private String warehouseName;

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public List<ReturnedProductVO> getProducts() {
        return products;
    }

    public void setProducts(List<ReturnedProductVO> products) {
        this.products = products;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
}
