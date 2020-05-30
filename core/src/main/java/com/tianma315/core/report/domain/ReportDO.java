package com.tianma315.core.report.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 检测报告
 * </pre>
 * <small> 2019-06-25 13:58:06 | Wen</small>
 */
 @TableName("report")
public class ReportDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Integer reportId;
    /** 档案id */
    private Integer traceabilityFileId;
    /** 商户id */
    private Integer companyId;
    /** 名称 */
    private String reportName;
    /** 状态0正常1删除 */
    private Integer state;
    /** 批次 */
    private String batchNumber;
    /** 检测人 */
    private String checkBy;
    /** 检测类型 */
    private Integer checkTypeId;
    /** 检测企业机构 */
    private Integer checkMechanismId;
    /** 检测时间 */
    private Date checkDate;
    /** 检测人电话(非必填) */
    private String checkPhone;
    /** 创建人 */
    private Long createBy;
    /** 创建时间 */
    private Date createDate;

//    private Integer isBind;

    /**
     * 设置：
     */
    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }
    /**
     * 获取：
     */
    public Integer getReportId() {
        return reportId;
    }
    /**
     * 设置：档案id
     */
    public void setTraceabilityFileId(Integer traceabilityFileId) {
        this.traceabilityFileId = traceabilityFileId;
    }
    /**
     * 获取：档案id
     */
    public Integer getTraceabilityFileId() {
        return traceabilityFileId;
    }
    /**
     * 设置：商户id
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    /**
     * 获取：商户id
     */
    public Integer getCompanyId() {
        return companyId;
    }
    /**
     * 设置：名称
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
    /**
     * 获取：名称
     */
    public String getReportName() {
        return reportName;
    }
    /**
     * 设置：状态0正常1删除
     */
    public void setState(Integer state) {
        this.state = state;
    }
    /**
     * 获取：状态0正常1删除
     */
    public Integer getState() {
        return state;
    }
    /**
     * 设置：批次
     */
    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }
    /**
     * 获取：批次
     */
    public String getBatchNumber() {
        return batchNumber;
    }
    /**
     * 设置：检测人
     */
    public void setCheckBy(String checkBy) {
        this.checkBy = checkBy;
    }
    /**
     * 获取：检测人
     */
    public String getCheckBy() {
        return checkBy;
    }

    public Integer getCheckTypeId() {
        return checkTypeId;
    }

    public void setCheckTypeId(Integer checkTypeId) {
        this.checkTypeId = checkTypeId;
    }

    public Integer getCheckMechanismId() {
        return checkMechanismId;
    }

    public void setCheckMechanismId(Integer checkMechanismId) {
        this.checkMechanismId = checkMechanismId;
    }

    /**
     * 设置：检测时间
     */
    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }
    /**
     * 获取：检测时间
     */
    public Date getCheckDate() {
        return checkDate;
    }
    /**
     * 设置：检测人电话(非必填)
     */
    public void setCheckPhone(String checkPhone) {
        this.checkPhone = checkPhone;
    }
    /**
     * 获取：检测人电话(非必填)
     */
    public String getCheckPhone() {
        return checkPhone;
    }
    /**
     * 设置：创建人
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
    /**
     * 获取：创建人
     */
    public Long getCreateBy() {
        return createBy;
    }
    /**
     * 设置：创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    /**
     * 获取：创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

//    public Integer getIsBind() {
//        return isBind;
//    }
//
//    public void setIsBind(Integer isBind) {
//        this.isBind = isBind;
//    }
}
