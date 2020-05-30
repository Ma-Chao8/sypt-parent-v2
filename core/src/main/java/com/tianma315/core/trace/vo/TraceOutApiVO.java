package com.tianma315.core.trace.vo;



import com.tianma315.core.trace.domain.TraceOutDO;


import java.util.List;

public class TraceOutApiVO {
    private TraceOutDO traceOutDO;
//    private ProductDO productDO;
//    private ReportApiVO reportApiVO;
    private List<TraceOutAttrFieldVO> traceOutAttrFieldVOS;

    public TraceOutDO getTraceOutDO() {
        return traceOutDO;
    }

    public void setTraceOutDO(TraceOutDO traceOutDO) {
        this.traceOutDO = traceOutDO;
    }

//    public ProductDO getProductDO() {
//        return productDO;
//    }
//
//    public void setProductDO(ProductDO productDO) {
//        this.productDO = productDO;
//    }
//
//    public ReportApiVO getReportApiVO() {
//        return reportApiVO;
//    }
//
//    public void setReportApiVO(ReportApiVO reportApiVO) {
//        this.reportApiVO = reportApiVO;
//    }

    public List<TraceOutAttrFieldVO> getTraceOutAttrFieldVOS() {
        return traceOutAttrFieldVOS;
    }

    public void setTraceOutAttrFieldVOS(List<TraceOutAttrFieldVO> traceOutAttrFieldVOS) {
        this.traceOutAttrFieldVOS = traceOutAttrFieldVOS;
    }
}
