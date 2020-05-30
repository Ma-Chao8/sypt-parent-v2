package com.tianma315.core.invoice.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-08-22 15:48:52 | Lgc</small>
 */
 @TableName("returned_product")
public class ReturnedProductDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Long returnedProductId;
    /**  */
    private Long returnedId;
    /**  */
    private Long productId;

    /**
     * 设置：
     */
    public void setReturnedProductId(Long returnedProductId) {
        this.returnedProductId = returnedProductId;
    }
    /**
     * 获取：
     */
    public Long getReturnedProductId() {
        return returnedProductId;
    }
    /**
     * 设置：
     */
    public void setReturnedId(Long returnedId) {
        this.returnedId = returnedId;
    }
    /**
     * 获取：
     */
    public Long getReturnedId() {
        return returnedId;
    }
    /**
     * 设置：
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    /**
     * 获取：
     */
    public Long getProductId() {
        return productId;
    }
}
