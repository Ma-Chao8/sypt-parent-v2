package com.tianma315.core.agent.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 经销商等级
 * </pre>
 * <small> 2019-07-26 10:05:37 | Wen</small>
 */
 @TableName("agent_level")
public class AgentLevelDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Long levelId;
    /** 商户id */
    private Long companyId;
    /** 等级名称 */
    private String levelName;
    /** 等级排序，等级越低序列号越大 */
    private Integer levelWeight;
    /** 状态 1 可用  0禁用 */
    private Integer status;

    private Long createBy;

    private Date createDate;

    /**
     * 设置：
     */
    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }
    /**
     * 获取：
     */
    public Long getLevelId() {
        return levelId;
    }
    /**
     * 设置：商户id
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    /**
     * 获取：商户id
     */
    public Long getCompanyId() {
        return companyId;
    }
    /**
     * 设置：等级名称
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
    /**
     * 获取：等级名称
     */
    public String getLevelName() {
        return levelName;
    }
    /**
     * 设置：等级排序，等级越低序列号越大
     */
    public void setLevelWeight(Integer levelWeight) {
        this.levelWeight = levelWeight;
    }
    /**
     * 获取：等级排序，等级越低序列号越大
     */
    public Integer getLevelWeight() {
        return levelWeight;
    }
    /**
     * 设置：状态 1 可用  0禁用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    /**
     * 获取：状态 1 可用  0禁用
     */
    public Integer getStatus() {
        return status;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
