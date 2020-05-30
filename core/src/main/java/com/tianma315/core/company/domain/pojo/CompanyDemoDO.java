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
 * <small> 2019-09-26 16:03:38 | Lgc</small>
 */
 @TableName("company_demo")
public class CompanyDemoDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Integer id;
    /**  */
    private Integer companyId;
    /**  */
    private Integer demoId;

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
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    /**
     * 获取：
     */
    public Integer getCompanyId() {
        return companyId;
    }
    /**
     * 设置：
     */
    public void setDemoId(Integer demoId) {
        this.demoId = demoId;
    }
    /**
     * 获取：
     */
    public Integer getDemoId() {
        return demoId;
    }
}
