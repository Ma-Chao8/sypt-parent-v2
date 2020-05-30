package com.tianma315.core.report.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * <pre>
 * 检测机构
 * </pre>
 * <small> 2019-06-27 17:10:49 | Wen</small>
 */
 @TableName("check_mechanism")
public class CheckMechanismDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Long checkMechanismId;
    /**  */
    private String checkMechanismName;
    /**  */
    private Long createBy;
    /**  */
    private Date createDate;
    /**  */
    private Integer state;

    /**
     * 设置：
     */
    public void setCheckMechanismId(Long checkMechanismId) {
        this.checkMechanismId = checkMechanismId;
    }
    /**
     * 获取：
     */
    public Long getCheckMechanismId() {
        return checkMechanismId;
    }
    /**
     * 设置：
     */
    public void setCheckMechanismName(String checkMechanismName) {
        this.checkMechanismName = checkMechanismName;
    }
    /**
     * 获取：
     */
    public String getCheckMechanismName() {
        return checkMechanismName;
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
