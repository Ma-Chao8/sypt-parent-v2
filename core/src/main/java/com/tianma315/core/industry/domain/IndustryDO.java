package com.tianma315.core.industry.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 行业
 * </pre>
 * <small> 2019-06-29 14:23:26 | Wen</small>
 */
 @TableName("industry")
public class IndustryDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Integer industryId;
    /** 名称 */
    private String industryName;
    /** 描述 */
    private String describe;
    /** 状态 */
    private Integer state;
    /**  */
    private Date createDate;
    /**  */
    private Long createBy;

    /**
     * 设置：
     */
    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }
    /**
     * 获取：
     */
    public Integer getIndustryId() {
        return industryId;
    }
    /**
     * 设置：名称
     */
    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }
    /**
     * 获取：名称
     */
    public String getIndustryName() {
        return industryName;
    }
    /**
     * 设置：描述
     */
    public void setDescribe(String describe) {
        this.describe = describe;
    }
    /**
     * 获取：描述
     */
    public String getDescribe() {
        return describe;
    }
    /**
     * 设置：状态
     */
    public void setState(Integer state) {
        this.state = state;
    }
    /**
     * 获取：状态
     */
    public Integer getState() {
        return state;
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
}
