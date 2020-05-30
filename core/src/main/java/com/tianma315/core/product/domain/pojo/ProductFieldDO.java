package com.tianma315.core.product.domain.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 产品-字段
 * </pre>
 * <small> 2019-08-06 14:41:19 | Wen</small>
 */
 @TableName("product_field")
public class ProductFieldDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @TableId
    private Integer productFieldId;
    
    /**  */
    private Integer productId;
    /**  */
    private Integer attrFieldId;
    /** 值 */
    private String values;
    /**  */
    private Integer state;
    /**  */
    private Integer attrId;

    public Integer getProductFieldId() {
        return productFieldId;
    }

    public void setProductFieldId(Integer productFieldId) {
        this.productFieldId = productFieldId;
    }

    /**
     * 设置：
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    /**
     * 获取：
     */
    public Integer getProductId() {
        return productId;
    }
    /**
     * 设置：
     */
    public void setAttrFieldId(Integer attrFieldId) {
        this.attrFieldId = attrFieldId;
    }
    /**
     * 获取：
     */
    public Integer getAttrFieldId() {
        return attrFieldId;
    }
    /**
     * 设置：值
     */
    public void setValues(String values) {
        this.values = values;
    }
    /**
     * 获取：值
     */
    public String getValues() {
        return values;
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
    /**
     * 设置：
     */
    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }
    /**
     * 获取：
     */
    public Integer getAttrId() {
        return attrId;
    }

}
