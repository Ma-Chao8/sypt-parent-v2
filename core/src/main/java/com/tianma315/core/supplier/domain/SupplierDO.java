package com.tianma315.core.supplier.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 供应商
 * </pre>
 * <small> 2019-06-24 09:58:51 | Wen</small>
 */
 @TableName("supplier")
public class SupplierDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Integer supplierId;
    /**  */
    private Long companyId;
    /**  */
    private Integer supplierTypeId;
    /** 供应商名称 */
    private String supplierName;
    /** 联系人 */
    private String linkman;
    /** 联系人电话 */
    private String tel;
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
    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }
    /**
     * 获取：
     */
    public Integer getSupplierId() {
        return supplierId;
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
     * 设置：供应商名称
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    /**
     * 获取：供应商名称
     */
    public String getSupplierName() {
        return supplierName;
    }
    /**
     * 设置：联系人
     */
    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }
    /**
     * 获取：联系人
     */
    public String getLinkman() {
        return linkman;
    }
    /**
     * 设置：联系人电话
     */
    public void setTel(String tel) {
        this.tel = tel;
    }
    /**
     * 获取：联系人电话
     */
    public String getTel() {
        return tel;
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
