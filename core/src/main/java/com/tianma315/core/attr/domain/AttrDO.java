package com.tianma315.core.attr.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * <pre>
 * 辅助属性
 * </pre>
 * <small> 2019-08-02 15:38:24 | Wen</small>
 */
 @TableName("attr")
public class AttrDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Integer attrId;
    /** 属性名称 */
    private String attrName;
    /** 备注 */
    private String desc;
    /** 创建时间 */
    private Date createDate;
    /** 最后更新时间 */
    private Date lsUpdateDate;

    private Integer state;

    private Long companyId;

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
    /**
     * 设置：属性名称
     */
    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }
    /**
     * 获取：属性名称
     */
    public String getAttrName() {
        return attrName;
    }
    /**
     * 设置：备注
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    /**
     * 获取：备注
     */
    public String getDesc() {
        return desc;
    }
    /**
     * 设置：创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    /**
     * 获取：创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }
    /**
     * 设置：最后更新时间
     */
    public void setLsUpdateDate(Date lsUpdateDate) {
        this.lsUpdateDate = lsUpdateDate;
    }
    /**
     * 获取：最后更新时间
     */
    public Date getLsUpdateDate() {
        return lsUpdateDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
