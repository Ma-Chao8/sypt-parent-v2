package com.tianma315.core.report.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * <pre>
 * 检测类型
 * </pre>
 * <small> 2019-06-27 17:10:49 | Wen</small>
 */
 @TableName("check_type")
public class CheckTypeDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Long checkTypeId;
    /**  */
    private String checkTypeName;
    /**  */
    private Long createBy;
    /**  */
    private Date createDate;
    /**  */
    private Integer state;

    /**
     * 设置：
     */
    public void setCheckTypeId(Long checkTypeId) {
        this.checkTypeId = checkTypeId;
    }
    /**
     * 获取：
     */
    public Long getCheckTypeId() {
        return checkTypeId;
    }
    /**
     * 设置：
     */
    public void setCheckTypeName(String checkTypeName) {
        this.checkTypeName = checkTypeName;
    }
    /**
     * 获取：
     */
    public String getCheckTypeName() {
        return checkTypeName;
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
