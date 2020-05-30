package com.tianma315.core.warehouse.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 产品员工关联
 * </pre>
 * <small> 2019-08-20 15:11:51 | Lgc</small>
 */
 @TableName("warehouse_manager")
public class WarehouseManagerDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Long id;
    /** 用户id */
    private Long userId;
    /**  */
    private Long companyId;
    /** 仓库id */
    private Long warehouseId;
    /** 管理员姓名 */
    private String realName;
    /** 备注 */
    private String remark;

    /**
     * 设置：
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * 获取：
     */
    public Long getId() {
        return id;
    }
    /**
     * 设置：用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    /**
     * 获取：用户id
     */
    public Long getUserId() {
        return userId;
    }
    /**
     * 设置：
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    /**
     * 获取：
     */
    public Long getCompanyId() {
        return companyId;
    }
    /**
     * 设置：仓库id
     */
    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
    /**
     * 获取：仓库id
     */
    public Long getWarehouseId() {
        return warehouseId;
    }
    /**
     * 设置：管理员姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }
    /**
     * 获取：管理员姓名
     */
    public String getRealName() {
        return realName;
    }
    /**
     * 设置：备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * 获取：备注
     */
    public String getRemark() {
        return remark;
    }
}
