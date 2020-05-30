package com.tianma315.core.company.domain.pojo;

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
 * <small> 2019-08-26 09:52:18 | Aron</small>
 */
 @TableName("company_config")
public class CompanyConfigDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Integer id;
    /**  */
    private Long companyId;
    /** 出码类型,1自动出码，2绑码，3兼容 */
    private Integer outType;
    /** 是否防窜货，0否 1是 */
    private Integer isTrace;
    /** 无库存是否出库，0是，1否*/
    private Integer isStock;

    public Integer getIsStock() {
        return isStock;
    }

    public void setIsStock(Integer isStock) {
        this.isStock = isStock;
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
     * 设置：出码类型,1自动出码，2绑码，3兼容
     */
    public void setOutType(Integer outType) {
        this.outType = outType;
    }
    /**
     * 获取：出码类型,1自动出码，2绑码，3兼容
     */
    public Integer getOutType() {
        return outType;
    }
    /**
     * 设置：是否防窜货，0否 1是
     */
    public void setIsTrace(Integer isTrace) {
        this.isTrace = isTrace;
    }
    /**
     * 获取：是否防窜货，0否 1是
     */
    public Integer getIsTrace() {
        return isTrace;
    }
}
