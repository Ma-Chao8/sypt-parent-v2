package com.tianma315.core.traceability.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 菜单数据
 * </pre>
 * <small> 2019-06-20 13:49:53 | Wen</small>
 */
 @TableName("ty_menu_data")
public class TyMenuDataDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Integer menuDataId;
    /**  */
    private Integer menuId;
    /**  */
    private String dataKey;
    /**  */
    private String dataValue;
    /**  */
    private Date createDate;
    /**  */
    private Long createBy;
    /**  */
    private Integer isDelete;
    /**  */
    private Integer menuDataSort;
    /**  */
    private Integer linkContentId;
    /**  */
    private Integer type;

    public Integer getLinkContentId() {
        return linkContentId;
    }

    public void setLinkContentId(Integer linkContentId) {
        this.linkContentId = linkContentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 设置：
     */
    public void setMenuDataId(Integer menuDataId) {
        this.menuDataId = menuDataId;
    }
    /**
     * 获取：
     */
    public Integer getMenuDataId() {
        return menuDataId;
    }
    /**
     * 设置：
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
    /**
     * 获取：
     */
    public Integer getMenuId() {
        return menuId;
    }
    /**
     * 设置：
     */
    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }
    /**
     * 获取：
     */
    public String getDataKey() {
        return dataKey;
    }
    /**
     * 设置：
     */
    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }
    /**
     * 获取：
     */
    public String getDataValue() {
        return dataValue;
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
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
    /**
     * 获取：
     */
    public Integer getIsDelete() {
        return isDelete;
    }
    /**
     * 设置：
     */
    public void setMenuDataSort(Integer menuDataSort) {
        this.menuDataSort = menuDataSort;
    }
    /**
     * 获取：
     */
    public Integer getMenuDataSort() {
        return menuDataSort;
    }
}
