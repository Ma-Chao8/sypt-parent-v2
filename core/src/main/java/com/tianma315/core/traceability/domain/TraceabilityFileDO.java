package com.tianma315.core.traceability.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 溯源档案
 * </pre>
 * <small> 2019-06-20 13:49:53 | Wen</small>
 */
 @TableName("traceability_file")
public class TraceabilityFileDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Integer traceabilityFileId;
    /** 商户id */
    private Long companyId;
    /** 档案名称 */
    private String traceabilityFileName;
    /** 产品id */
    private Long productId;
    /**  */
    private Long createBy;
    /**  */
    private Date createDate;
    /** 是否取消 */
    private Integer state;
    /**
     * 介绍语
     */
    private String introduce;

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    /**
     * 设置：
     */
    public void setTraceabilityFileId(Integer traceabilityFileId) {
        this.traceabilityFileId = traceabilityFileId;
    }
    /**
     * 获取：
     */
    public Integer getTraceabilityFileId() {
        return traceabilityFileId;
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
     * 设置：档案名称
     */
    public void setTraceabilityFileName(String traceabilityFileName) {
        this.traceabilityFileName = traceabilityFileName;
    }
    /**
     * 获取：档案名称
     */
    public String getTraceabilityFileName() {
        return traceabilityFileName;
    }
    /**
     * 设置：产品id
     */
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
     * 设置：是否取消
     */
    public void setState(Integer state) {
        this.state = state;
    }
    /**
     * 获取：是否取消
     */
    public Integer getState() {
        return state;
    }
}
