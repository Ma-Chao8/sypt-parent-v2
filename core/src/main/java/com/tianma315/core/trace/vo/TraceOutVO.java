package com.tianma315.core.trace.vo;


import com.tianma315.core.trace.domain.TraceOutDO;

public class TraceOutVO extends TraceOutDO {
    private String productName;
    private String purchaseNo;
    private String reportName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}
