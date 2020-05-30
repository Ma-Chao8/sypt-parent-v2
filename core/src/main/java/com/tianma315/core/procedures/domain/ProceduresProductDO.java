package com.tianma315.core.procedures.domain;

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
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
 @TableName("procedures_product")
public class ProceduresProductDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Integer procedureProductId;
    /**  */
    private Integer productId;
    /**  */
    private Integer procedureId;
    /**  */
    private Integer createBy;
    /**  */
    private Date createDate;

    /**
     * 设置：
     */
    public void setProcedureProductId(Integer procedureProductId) {
        this.procedureProductId = procedureProductId;
    }
    /**
     * 获取：
     */
    public Integer getProcedureProductId() {
        return procedureProductId;
    }
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
    public void setProcedureId(Integer procedureId) {
        this.procedureId = procedureId;
    }
    /**
     * 获取：
     */
    public Integer getProcedureId() {
        return procedureId;
    }
    /**
     * 设置：
     */
    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }
    /**
     * 获取：
     */
    public Integer getCreateBy() {
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
