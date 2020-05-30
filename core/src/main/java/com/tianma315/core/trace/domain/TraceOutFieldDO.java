package com.tianma315.core.trace.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 批次-字段属性
 * </pre>
 * <small> 2019-08-08 15:02:50 | Wb</small>
 */
 @TableName("trace_out_field")
public class TraceOutFieldDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Integer id;
    /**  */
    private Integer traceOutId;
    /**  */
    private Integer attrFieldId;
    /**  */
    private String values;
    /**  */
    private Integer state;
    /**  */
    private Integer attrId;

    /**
     * 设置：
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * 获取：
     */
    public Integer getId() {
        return id;
    }
    /**
     * 设置：
     */
    public void setTraceOutId(Integer traceOutId) {
        this.traceOutId = traceOutId;
    }
    /**
     * 获取：
     */
    public Integer getTraceOutId() {
        return traceOutId;
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
     * 设置：
     */
    public void setValues(String values) {
        this.values = values;
    }
    /**
     * 获取：
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
