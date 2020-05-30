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
 * <small> 2019-08-22 15:48:53 | Lgc</small>
 */
 @TableName("returned_product_code")
public class ReturnedProductCodeDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Long codeId;
    /**  */
    private Long returnedProductId;
    /**  */
    private String bigCode;
    /**  */
    private String smallCode;

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
    public void setBigCode(String bigCode) {
        this.bigCode = bigCode;
    }
    /**
     * 获取：
     */
    public String getBigCode() {
        return bigCode;
    }
    /**
     * 设置：
     */
    public void setSmallCode(String smallCode) {
        this.smallCode = smallCode;
    }
    /**
     * 获取：
     */
    public String getSmallCode() {
        return smallCode;
    }
}
