package com.tianma315.core.invoice.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 货单产品
 * </pre>
 * <small> 2019-08-23 10:03:22 | Lgc</small>
 */
 @TableName("invoice_product")
public class InvoiceProductDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Long invoiceProductId;
    /** 货单id */
    private Long invoiceId;
    /** 发货产品id */
    private Long productId;
    /** 大码数量 */
    private Integer bigNumber;
    /** 小码数量 */
    private Integer smallNumber;

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
     * 设置：货单id
     */
    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }
    /**
     * 获取：货单id
     */
    public Long getInvoiceId() {
        return invoiceId;
    }
    /**
     * 设置：发货产品id
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    /**
     * 获取：发货产品id
     */
    public Long getProductId() {
        return productId;
    }
    /**
     * 设置：大码数量
     */
    public void setBigNumber(Integer bigNumber) {
        this.bigNumber = bigNumber;
    }
    /**
     * 获取：大码数量
     */
    public Integer getBigNumber() {
        return bigNumber;
    }
    /**
     * 设置：小码数量
     */
    public void setSmallNumber(Integer smallNumber) {
        this.smallNumber = smallNumber;
    }
    /**
     * 获取：小码数量
     */
    public Integer getSmallNumber() {
        return smallNumber;
    }
}
