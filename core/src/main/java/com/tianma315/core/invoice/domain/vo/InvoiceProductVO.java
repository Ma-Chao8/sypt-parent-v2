package com.tianma315.core.invoice.domain.vo;

import com.tianma315.core.invoice.domain.InvoiceProductCodeDO;
import com.tianma315.core.invoice.domain.InvoiceProductDO;


import java.util.List;

/**
 * Created by TianMa-Android on 2018/9/1.
 */
public class InvoiceProductVO extends InvoiceProductDO {

    private String productName;
    private String productSpec;
    private List<InvoiceProductCodeDO> codes;

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

    public List<InvoiceProductCodeDO> getCodes() {
        return codes;
    }

    public void setCodes(List<InvoiceProductCodeDO> codes) {
        this.codes = codes;
    }
}
