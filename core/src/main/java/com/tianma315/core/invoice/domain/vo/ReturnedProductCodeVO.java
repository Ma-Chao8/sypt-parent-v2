package com.tianma315.core.invoice.domain.vo;


import com.tianma315.core.invoice.domain.ReturnedProductCodeDO;

public class ReturnedProductCodeVO extends ReturnedProductCodeDO {

    private long invoiceId;
    protected long productId;
    private String productName;
    private String productSpec;

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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
