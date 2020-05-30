package com.tianma315.core.report.domain;

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
 * <small> 2019-06-25 13:58:06 | Wen</small>
 */
 @TableName("report_img")
public class ReportImgDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Integer reportImgId;
    /**  */
    private Integer reportId;
    /** 图片访问地址 */
    private String imgSrc;
    /** 附件id */
    private Integer enclosureId;
    /**  */
    private Integer state;

    /**
     * 设置：
     */
    public void setReportImgId(Integer reportImgId) {
        this.reportImgId = reportImgId;
    }
    /**
     * 获取：
     */
    public Integer getReportImgId() {
        return reportImgId;
    }
    /**
     * 设置：
     */
    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }
    /**
     * 获取：
     */
    public Integer getReportId() {
        return reportId;
    }
    /**
     * 设置：图片访问地址
     */
    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
    /**
     * 获取：图片访问地址
     */
    public String getImgSrc() {
        return imgSrc;
    }
    /**
     * 设置：附件id
     */
    public void setEnclosureId(Integer enclosureId) {
        this.enclosureId = enclosureId;
    }
    /**
     * 获取：附件id
     */
    public Integer getEnclosureId() {
        return enclosureId;
    }
    /**
     * 设置：
     */
    public void setState(Integer state) {
        this.state = state;
    }
    /**
     * 获取：
     */
    public Integer getState() {
        return state;
    }
}
