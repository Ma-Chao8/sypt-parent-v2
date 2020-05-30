package com.tianma315.core.material.vo;


import com.tianma315.core.material.domain.PurchaseDO;

public class PurchaseVO extends PurchaseDO {
    private String supplierName;
    private String materialName;
    private Integer checkTemplateId;
    private String companyName;
    private String checkImg;
    private String brand;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getCheckTemplateId() {
        return checkTemplateId;
    }

    public void setCheckTemplateId(Integer checkTemplateId) {
        this.checkTemplateId = checkTemplateId;
    }

    public String getCheckImg() {
        return checkImg;
    }

    public void setCheckImg(String checkImg) {
        this.checkImg = checkImg;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
