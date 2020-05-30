package com.tianma315.core.invoice.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 退货记录
 * </pre>
 * <small> 2019-08-21 14:57:42 | Lgc</small>
 */
 @TableName("returned")
public class ReturnedDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /** 自增主键 */
    @TableId
    private Long returnedId;
    /** 货单号 */
    private Long invoiceId;
    /** 所属商户id */
    private Long companyId;
    /** 退货时间 */
    private Date createDate;
    /** 操作人 */
    private Long createBy;
    /** 备注 */
    private String remark;

    private Long warehouseId;

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    /**
     * 设置：自增主键
     */
    public void setReturnedId(Long returnedId) {
        this.returnedId = returnedId;
    }
    /**
     * 获取：自增主键
     */
    public Long getReturnedId() {
        return returnedId;
    }
    /**
     * 设置：货单号
     */
    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }
    /**
     * 获取：货单号
     */
    public Long getInvoiceId() {
        return invoiceId;
    }
    /**
     * 设置：所属商户id
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    /**
     * 获取：所属商户id
     */
    public Long getCompanyId() {
        return companyId;
    }
    /**
     * 设置：退货时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    /**
     * 获取：退货时间
     */
    public Date getCreateDate() {
        return createDate;
    }
    /**
     * 设置：操作人
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
    /**
     * 获取：操作人
     */
    public Long getCreateBy() {
        return createBy;
    }
    /**
     * 设置：备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * 获取：备注
     */
    public String getRemark() {
        return remark;
    }
}
