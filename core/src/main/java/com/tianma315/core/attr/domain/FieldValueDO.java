package com.tianma315.core.attr.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;


/**
 * 
 * <pre>
 * 字段-值
 * </pre>
 * <small> 2019-08-02 15:38:24 | Wen</small>
 */
 @TableName("field_value")
public class FieldValueDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /** 字段值id */
    @TableId
    private Integer fieldValueId;
    /** 属性字段id */
    private Integer attrFieldId;
    /** 字段值 */
    private String fieldValue;

    /**
     * 设置：字段值id
     */
    public void setFieldValueId(Integer fieldValueId) {
        this.fieldValueId = fieldValueId;
    }
    /**
     * 获取：字段值id
     */
    public Integer getFieldValueId() {
        return fieldValueId;
    }
    /**
     * 设置：属性字段id
     */
    public void setAttrFieldId(Integer attrFieldId) {
        this.attrFieldId = attrFieldId;
    }
    /**
     * 获取：属性字段id
     */
    public Integer getAttrFieldId() {
        return attrFieldId;
    }
    /**
     * 设置：字段值
     */
    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
    /**
     * 获取：字段值
     */
    public String getFieldValue() {
        return fieldValue;
    }
}
