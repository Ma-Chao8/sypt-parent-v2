package com.tianma315.core.trace.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 溯源批次出库记录
 * </pre>
 * <small> 2019-08-08 15:02:49 | Wb</small>
 */
 @TableName("trace_out")
public class TraceOutDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Integer traceOutId;
    /** 批次 */
    private String batch;
    /** 开始序号 */
    private Integer startSerial;
    /** 结束序号 */
    private Integer endSerial;
    /** 产品id */
    private Integer productId;
    /**  */
    private String createBy;
    /**  */
    private Date createDate;
    /**  */
    private Integer state;
    /** 检测报告id */
    private Integer reportId;
    /** 原材料入库记录id */
    private Integer purchaseId;

    private Long companyId;

    private Integer traceabilityFileId;

    public Integer getTraceabilityFileId() {
        return traceabilityFileId;
    }

    public void setTraceabilityFileId(Integer traceabilityFileId) {
        this.traceabilityFileId = traceabilityFileId;
    }

    /**
     * 设置：
     */
    public void setTraceOutId(Integer traceOutId) {
        this.traceOutId = traceOutId;
    }
    /**
     * 获取：
     */
    public Integer getTraceOutId() {
        return traceOutId;
    }
    /**
     * 设置：批次
     */
    public void setBatch(String batch) {
        this.batch = batch;
    }
    /**
     * 获取：批次
     */
    public String getBatch() {
        return batch;
    }
    /**
     * 设置：开始序号
     */
    public void setStartSerial(Integer startSerial) {
        this.startSerial = startSerial;
    }
    /**
     * 获取：开始序号
     */
    public Integer getStartSerial() {
        return startSerial;
    }
    /**
     * 设置：结束序号
     */
    public void setEndSerial(Integer endSerial) {
        this.endSerial = endSerial;
    }
    /**
     * 获取：结束序号
     */
    public Integer getEndSerial() {
        return endSerial;
    }
    /**
     * 设置：产品id
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    /**
     * 获取：产品id
     */
    public Integer getProductId() {
        return productId;
    }
    /**
     * 设置：
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    /**
     * 获取：
     */
    public String getCreateBy() {
        return createBy;
    }
    /**
     * 设置：
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    /**
     * 获取：
     */
    public Date getCreateDate() {
        return createDate;
    }
    /**
     * 设置：
     */
    public void setState(Integer state) {
        this.state = state;
    }
    /**
     * 获取：
     */
    public Integer getState() {
        return state;
    }
    /**
     * 设置：检测报告id
     */
    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }
    /**
     * 获取：检测报告id
     */
    public Integer getReportId() {
        return reportId;
    }
    /**
     * 设置：原材料入库记录id
     */
    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }
    /**
     * 获取：原材料入库记录id
     */
    public Integer getPurchaseId() {
        return purchaseId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
