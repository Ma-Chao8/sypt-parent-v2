package com.tianma315.core.procedures.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import jdk.nashorn.internal.ir.annotations.Ignore;


/**
 * 
 * <pre>
 * 环节
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
 @TableName("procedure_link")
public class ProcedureLinkDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 是否有权限
     */
    @TableField(exist = false)
    private Integer isRole;
    /**  */
    @TableId
    private Integer procedureLinkId;
    /** 商户id */
    private Long companyId;
    /** 溯源环节名称 */
    private String procedureLinkName;
    /** 图标 */
    private String icon;
    /** 排序，数值越小越靠前 */
    private Integer sort;
    /** 状态 */
    private Integer state;
    /** 是否显示查询 */
    private Integer isView;
    /**  */
    private Long createBy;
    /**  */
    private Date createDate;
    /**  */
    private Integer proceduresId;
    /** 是否添加记录 */
    private Integer isAdd;
    /**
    * 是否动态
    */
    private Integer isDynamic;
    /**
    * 是否授权所有角色
    */
    private Integer isAll;

    public Integer getIsRole() {
        return isRole;
    }

    public void setIsRole(Integer isRole) {
        this.isRole = isRole;
    }

    public Integer getIsAll() {
        return isAll;
    }

    public void setIsAll(Integer isAll) {
        this.isAll = isAll;
    }

    public Integer getIsDynamic() {
        return isDynamic;
    }

    public void setIsDynamic(Integer isDynamic) {
        this.isDynamic = isDynamic;
    }

    public Integer getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(Integer isAdd) {
        this.isAdd = isAdd;
    }

    /**
     * 设置：
     */
    public void setProcedureLinkId(Integer procedureLinkId) {
        this.procedureLinkId = procedureLinkId;
    }
    /**
     * 获取：
     */
    public Integer getProcedureLinkId() {
        return procedureLinkId;
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
     * 设置：溯源环节名称
     */
    public void setProcedureLinkName(String procedureLinkName) {
        this.procedureLinkName = procedureLinkName;
    }
    /**
     * 获取：溯源环节名称
     */
    public String getProcedureLinkName() {
        return procedureLinkName;
    }
    /**
     * 设置：图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }
    /**
     * 获取：图标
     */
    public String getIcon() {
        return icon;
    }
    /**
     * 设置：排序，数值越小越靠前
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    /**
     * 获取：排序，数值越小越靠前
     */
    public Integer getSort() {
        return sort;
    }
    /**
     * 设置：状态
     */
    public void setState(Integer state) {
        this.state = state;
    }
    /**
     * 获取：状态
     */
    public Integer getState() {
        return state;
    }
    /**
     * 设置：是否显示查询
     */
    public void setIsView(Integer isView) {
        this.isView = isView;
    }
    /**
     * 获取：是否显示查询
     */
    public Integer getIsView() {
        return isView;
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
    public void setProceduresId(Integer proceduresId) {
        this.proceduresId = proceduresId;
    }
    /**
     * 获取：
     */
    public Integer getProceduresId() {
        return proceduresId;
    }
}
