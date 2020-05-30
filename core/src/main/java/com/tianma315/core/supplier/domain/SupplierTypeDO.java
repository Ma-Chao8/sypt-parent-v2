package com.tianma315.core.supplier.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 供应商类型
 * </pre>
 * <small> 2019-06-24 09:58:51 | Wen</small>
 */
 @TableName("supplier_type")
public class SupplierTypeDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Integer supplierTypeId;
    /**  */
    private Long companyId;
    /** 类型名称 */
    private String typeName;
    /** 备注 */
    private String mark;
    /**  */
    private Integer state;

    /**
     * 设置：
     */
    public void setSupplierTypeId(Integer supplierTypeId) {
        this.supplierTypeId = supplierTypeId;
    }
    /**
     * 获取：
     */
    public Integer getSupplierTypeId() {
        return supplierTypeId;
    }
    /**
     * 设置：
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    /**
     * 获取：
     */
    public Long getCompanyId() {
        return companyId;
    }
    /**
     * 设置：类型名称
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    /**
     * 获取：类型名称
     */
    public String getTypeName() {
        return typeName;
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
