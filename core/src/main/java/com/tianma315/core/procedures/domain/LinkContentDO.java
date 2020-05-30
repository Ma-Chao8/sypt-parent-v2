package com.tianma315.core.procedures.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 环节内容表
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
 @TableName("link_content")
public class LinkContentDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @TableField(exist = false)
    private List<String> pics;
    /**  */
    @TableId
    private Integer linkContentId;
    /** 溯源环节细节名称 */
    private String contentName;
    /** 默认值 */
    private String defaultValue;
    /** 排序 */
    private Integer sort;
    /** 状态 */
    private Integer state;
    /** 是否显示 */
    private Integer isView;
    /**  */
    private Long createBy;
    /**  */
    private Date createDate;
    /**  */
    private Integer proceduresLinkId;

    private Integer type;

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
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
    public void setLinkContentId(Integer linkContentId) {
        this.linkContentId = linkContentId;
    }
    /**
     * 获取：
     */
    public Integer getLinkContentId() {
        return linkContentId;
    }
    /**
     * 设置：溯源环节细节名称
     */
    public void setContentName(String contentName) {
        this.contentName = contentName;
    }
    /**
     * 获取：溯源环节细节名称
     */
    public String getContentName() {
        return contentName;
    }
    /**
     * 设置：默认值
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    /**
     * 获取：默认值
     */
    public String getDefaultValue() {
        return defaultValue;
    }
    /**
     * 设置：排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    /**
     * 获取：排序
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
     * 设置：是否显示
     */
    public void setIsView(Integer isView) {
        this.isView = isView;
    }
    /**
     * 获取：是否显示
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
    public void setProceduresLinkId(Integer proceduresLinkId) {
        this.proceduresLinkId = proceduresLinkId;
    }
    /**
     * 获取：
     */
    public Integer getProceduresLinkId() {
        return proceduresLinkId;
    }
}
