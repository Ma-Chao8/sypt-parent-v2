package com.tianma315.core.invoice.domain.vo;
import com.tianma315.core.invoice.domain.ReturnedProductCodeDO;
import com.tianma315.core.invoice.domain.ReturnedProductDO;

import java.util.List;

/**
 * Created by TianMa-Android on 2019/8/22.
 */
public class ReturnedProductVO extends ReturnedProductDO {
    private String productName;
    private String productSpec;
    private List<ReturnedProductCodeDO> codes;

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

    public List<ReturnedProductCodeDO> getCodes() {
        return codes;
    }

    public void setCodes(List<ReturnedProductCodeDO> codes) {
        this.codes = codes;
    }
}
