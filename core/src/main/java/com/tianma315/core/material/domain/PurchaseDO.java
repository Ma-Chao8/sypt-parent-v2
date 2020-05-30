package com.tianma315.core.material.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 原材料进库表
 * </pre>
 * <small> 2019-06-24 11:23:31 | Wen</small>
 */
 @TableName("purchase")
public class PurchaseDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Integer purchaseId;
    /**  */
    private Integer companyId;
    /** 原料id */
    private Integer materialId;
    /** 原料生产日期(出厂日期) */
    private Date dateOfProduction;
    /** 供应商id */
    private Integer supplierId;
    /** 批次 */
    private String purchaseNo;
    /** 采购人 */
    private String purchaser;
    /** 检测图片路径 */
    private String reportImg;
    /**  */
    private Date createDate;
    /**  */
    private Long createBy;
    /**  */
    private Integer state;
    /** 数量 */
    private Integer number;
    /** 入库标识(原料名称+批次) */
    private String identifier;
    /** 入库时间 */
    private Date dateStore;

    public Date getDateStore() {
        return dateStore;
    }

    public void setDateStore(Date dateStore) {
        this.dateStore = dateStore;
    }

    /**
     * 设置：
     */
    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }
    /**
     * 获取：
     */
    public Integer getPurchaseId() {
        return purchaseId;
    }
    /**
     * 设置：
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    /**
     * 获取：
     */
    public Integer getCompanyId() {
        return companyId;
    }
    /**
     * 设置：原料id
     */
    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }
    /**
     * 获取：原料id
     */
    public Integer getMaterialId() {
        return materialId;
    }
    /**
     * 设置：原料生产日期(出厂日期)
     */
    public void setDateOfProduction(Date dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
    }
    /**
     * 获取：原料生产日期(出厂日期)
     */
    public Date getDateOfProduction() {
        return dateOfProduction;
    }
    /**
     * 设置：供应商id
     */
    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }
    /**
     * 获取：供应商id
     */
    public Integer getSupplierId() {
        return supplierId;
    }
    /**
     * 设置：批次
     */
    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }
    /**
     * 获取：批次
     */
    public String getPurchaseNo() {
        return purchaseNo;
    }
    /**
     * 设置：采购人
     */
    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
    }
    /**
     * 获取：采购人
     */
    public String getPurchaser() {
        return purchaser;
    }
    /**
     * 设置：检测图片路径
     */
    public void setReportImg(String reportImg) {
        this.reportImg = reportImg;
    }
    /**
     * 获取：检测图片路径
     */
    public String getReportImg() {
        return reportImg;
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
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
    /**
     * 获取：
     */
    public Long getCreateBy() {
        return createBy;
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
     * 设置：数量
     */
    public void setNumber(Integer number) {
        this.number = number;
    }
    /**
     * 获取：数量
     */
    public Integer getNumber() {
        return number;
    }
    /**
     * 设置：入库标识(原料名称+批次)
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    /**
     * 获取：入库标识(原料名称+批次)
     */
    public String getIdentifier() {
        return identifier;
    }
}
