package com.tianma315.core.traceability.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 溯源档案菜单(环节)
 * </pre>
 * <small> 2019-06-20 13:49:53 | Wen</small>
 */
 @TableName("ty_menu")
public class TyMenuDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Integer menuId;
    /**  */
    private Integer traceabilityFileId;
    /**  */
    private Integer menuLevel;
    /**  */
    private Integer parentMenuId;
    /**  */
    private String menuName;
    /**  */
    private String menuValue;
    /**  */
    private Long createBy;
    /**  */
    private Date createDate;
    /**  */
    private Integer isDelete;
    /**  */
    private Integer menuOrder;

    private Integer procedureLinkId;

    /**
    * 图标
    */
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getProcedureLinkId() {
        return procedureLinkId;
    }

    public void setProcedureLinkId(Integer procedureLinkId) {
        this.procedureLinkId = procedureLinkId;
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
     * 设置：
     */
    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }
    /**
     * 获取：
     */
    public Integer getMenuLevel() {
        return menuLevel;
    }
    /**
     * 设置：
     */
    public void setParentMenuId(Integer parentMenuId) {
        this.parentMenuId = parentMenuId;
    }
    /**
     * 获取：
     */
    public Integer getParentMenuId() {
        return parentMenuId;
    }
    /**
     * 设置：
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    /**
     * 获取：
     */
    public String getMenuName() {
        return menuName;
    }
    /**
     * 设置：
     */
    public void setMenuValue(String menuValue) {
        this.menuValue = menuValue;
    }
    /**
     * 获取：
     */
    public String getMenuValue() {
        return menuValue;
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
    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }
    /**
     * 获取：
     */
    public Integer getMenuOrder() {
        return menuOrder;
    }
}
