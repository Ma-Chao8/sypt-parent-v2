package com.tianma315.core.material.vo;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class PurchaseFileVO {
    private MultipartFile file;

    private Integer purchaseId;
    /**  */
    private Long merchantId;
    /** 原料id */
    private Integer materialId;
    /** 原料生产日期(出厂日期) */
    private Date dateOfProduction;
    /** 供应商id */
    private Integer supplierId;
    /** 批次(采用时间加3位数字如20190514001) */
    private String purchaseNo;
    /** 采购人 */
    private String purchaser;
    /** 检测图片路径 */
    private String reportImg;
    /**  */
    private Date createDate;
    /**  */
    private Long createBy;

    /** 数量 */
    private Integer number;
    /** 入库标识(原料名称+批次) */
    private String identifier;

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Long getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Date getDateOfProduction() {
        return dateOfProduction;
    }

    public void setDateOfProduction(Date dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public String getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
    }

    public String getReportImg() {
        return reportImg;
    }

    public void setReportImg(String reportImg) {
        this.reportImg = reportImg;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }


    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

}
