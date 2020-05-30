package com.tianma315.core.attr.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;


/**
 * 
 * <pre>
 * 辅助属性字段
 * </pre>
 * <small> 2019-08-02 15:38:24 | Wen</small>
 */
 @TableName("attr_field")
public class AttrFieldDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Integer attrFieldId;
    /**  */
    private String attrFieldName;
    /** 属性id */
    private Integer attrId;
    /** 类型，文本，多行文本，下拉 */
    private Integer type;
    /** 表单提示 */
    private String tips;
    /** 排序 */
    private Integer orderNum;
    /**  */
    private Integer state;

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
     * 设置：
     */
    public void setAttrFieldName(String attrFieldName) {
        this.attrFieldName = attrFieldName;
    }
    /**
     * 获取：
     */
    public String getAttrFieldName() {
        return attrFieldName;
    }
    /**
     * 设置：属性id
     */
    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }
    /**
     * 获取：属性id
     */
    public Integer getAttrId() {
        return attrId;
    }
    /**
     * 设置：类型，文本，多行文本，下拉
     */
    public void setType(Integer type) {
        this.type = type;
    }
    /**
     * 获取：类型，文本，多行文本，下拉
     */
    public Integer getType() {
        return type;
    }
    /**
     * 设置：表单提示
     */
    public void setTips(String tips) {
        this.tips = tips;
    }
    /**
     * 获取：表单提示
     */
    public String getTips() {
        return tips;
    }
    /**
     * 设置：排序
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
    /**
     * 获取：排序
     */
    public Integer getOrderNum() {
        return orderNum;
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
