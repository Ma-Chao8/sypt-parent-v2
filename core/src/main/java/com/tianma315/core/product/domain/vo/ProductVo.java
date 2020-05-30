package com.tianma315.core.product.domain.vo;

import com.tianma315.core.invoice.domain.InvoiceProductCodeDO;
import com.tianma315.core.product.domain.pojo.Product;
import com.tianma315.core.product.domain.pojo.ProductFieldDO;

import java.util.List;

/**
 *
 */
public class ProductVo extends Product {
    private long invoiceProductId;
    private List<InvoiceProductCodeDO> productCodes;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getInvoiceProductId() {
        return invoiceProductId;
    }

    public void setInvoiceProductId(long invoiceProductId) {
        this.invoiceProductId = invoiceProductId;
    }

    public List<InvoiceProductCodeDO> getProductCodes() {
        return productCodes;
    }

    public void setProductCodes(List<InvoiceProductCodeDO> productCodes) {
        this.productCodes = productCodes;
    }
}
