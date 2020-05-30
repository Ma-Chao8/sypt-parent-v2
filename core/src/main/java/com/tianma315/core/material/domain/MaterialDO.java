package com.tianma315.core.material.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 原材料
 * </pre>
 * <small> 2019-06-24 11:23:31 | Wen</small>
 */
 @TableName("material")
public class MaterialDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Integer materialId;
    /** 公司id */
    private Integer companyId;
    /** 名称 */
    private String materialName;
    /** 规格 */
    private String specs;
    /** 保质期 */
    private String shelflife;
    /** 计量单位 */
    private String unit;
    /** 品牌 */
    private String brand;
    /** 单价 */
    private BigDecimal price;
    /** 备注 */
    private String mark;
    /**  */
    private Date createDate;
    /**  */
    private Long createBy;
    /**  */
    private Integer state;

    /**
     * 设置：
     */
    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }
    /**
     * 获取：
     */
    public Integer getMaterialId() {
        return materialId;
    }
    /**
     * 设置：公司id
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    /**
     * 获取：公司id
     */
    public Integer getCompanyId() {
        return companyId;
    }
    /**
     * 设置：名称
     */
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
    /**
     * 获取：名称
     */
    public String getMaterialName() {
        return materialName;
    }
    /**
     * 设置：规格
     */
    public void setSpecs(String specs) {
        this.specs = specs;
    }
    /**
     * 获取：规格
     */
    public String getSpecs() {
        return specs;
    }
    /**
     * 设置：保质期
     */
    public void setShelflife(String shelflife) {
        this.shelflife = shelflife;
    }
    /**
     * 获取：保质期
     */
    public String getShelflife() {
        return shelflife;
    }
    /**
     * 设置：计量单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
    /**
     * 获取：计量单位
     */
    public String getUnit() {
        return unit;
    }
    /**
     * 设置：品牌
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }
    /**
     * 获取：品牌
     */
    public String getBrand() {
        return brand;
    }
    /**
     * 设置：单价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    /**
     * 获取：单价
     */
    public BigDecimal getPrice() {
        return price;
    }
    /**
     * 设置：备注
     */
    public void setMark(String mark) {
        this.mark = mark;
    }
    /**
     * 获取：备注
     */
    public String getMark() {
        return mark;
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
}
