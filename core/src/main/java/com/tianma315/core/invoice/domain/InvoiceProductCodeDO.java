package com.tianma315.core.invoice.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 货单产品码关联
 * </pre>
 * <small> 2019-08-23 10:03:22 | Lgc</small>
 */
 @TableName("invoice_product_code")
public class InvoiceProductCodeDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Long codeId;
    /**  */
    private Long invoiceProductId;
    /** 发货大码 */
    private String bigCode;
    /** 发货小码 */
    private String smallCode;
    /** 状态 1已经使用 2退货状态 */
    private Integer status;

    /**
     * 设置：
     */
    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }
    /**
     * 获取：
     */
    public Long getCodeId() {
        return codeId;
    }
    /**
     * 设置：
     */
    public void setInvoiceProductId(Long invoiceProductId) {
        this.invoiceProductId = invoiceProductId;
    }
    /**
     * 获取：
     */
    public Long getInvoiceProductId() {
        return invoiceProductId;
    }
    /**
     * 设置：发货大码
     */
    public void setBigCode(String bigCode) {
        this.bigCode = bigCode;
    }
    /**
     * 获取：发货大码
     */
    public String getBigCode() {
        return bigCode;
    }
    /**
     * 设置：发货小码
     */
    public void setSmallCode(String smallCode) {
        this.smallCode = smallCode;
    }
    /**
     * 获取：发货小码
     */
    public String getSmallCode() {
        return smallCode;
    }
    /**
     * 设置：状态 1已经使用 2退货状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    /**
     * 获取：状态 1已经使用 2退货状态
     */
    public Integer getStatus() {
        return status;
    }
}
