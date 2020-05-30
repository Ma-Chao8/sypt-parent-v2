package com.tianma315.core.warehouse.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-08-22 15:33:17 | wen</small>
 */
 @TableName("warehouse_record")
public class WarehouseRecordDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Integer id;
    /**  */
    //private String code;
    /**  */
    private Integer productId;
    /**  */
//    private Integer type;
    /**  */
    private Long createBy;
    /**  */
    private Date createDate;

    private Integer number;

    private Long warehouseId;

    private Long companyId;

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

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

    /**
     * 设置：
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    /**
     * 获取：
     */
    public Integer getProductId() {
        return productId;
    }
    /**
     * 设置：
     */
//    public void setType(Integer type) {
//        this.type = type;
//    }
//    /**
//     * 获取：
//     */
//    public Integer getType() {
//        return type;
//    }
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
}
