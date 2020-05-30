package com.tianma315.core.warehouse.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 仓库
 * </pre>
 * <small> 2019-08-20 15:09:27 | Lgc</small>
 */
 @TableName("warehouse")
public class WarehouseDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /** 自增主键 */
    @TableId
    private Long warehouseId;
    /** 所属商户iid */
    private Long companyId;
    /** 仓库名称 */
    private String warehouseName;
    /** 仓库地址 */
    private String warehouseAddress;
    /** 创建时间 */
    private Date createDate;
    /** 状态 1可用和 2冻结 3删除 */
    private Integer status;

    /**
     * 设置：自增主键
     */
    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
    /**
     * 获取：自增主键
     */
    public Long getWarehouseId() {
        return warehouseId;
    }
    /**
     * 设置：所属商户iid
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    /**
     * 获取：所属商户iid
     */
    public Long getCompanyId() {
        return companyId;
    }
    /**
     * 设置：仓库名称
     */
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    /**
     * 获取：仓库名称
     */
    public String getWarehouseName() {
        return warehouseName;
    }
    /**
     * 设置：仓库地址
     */
    public void setWarehouseAddress(String warehouseAddress) {
        this.warehouseAddress = warehouseAddress;
    }
    /**
     * 获取：仓库地址
     */
    public String getWarehouseAddress() {
        return warehouseAddress;
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
     * 设置：状态 1可用和 2冻结 3删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    /**
     * 获取：状态 1可用和 2冻结 3删除
     */
    public Integer getStatus() {
        return status;
    }
}
