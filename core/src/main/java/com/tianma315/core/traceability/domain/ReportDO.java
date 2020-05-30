//package com.tianma315.core.traceability.domain;
//
//import java.io.Serializable;
//import java.util.Date;
//
//import com.baomidou.mybatisplus.annotations.TableField;
//import com.baomidou.mybatisplus.annotations.TableId;
//import com.baomidou.mybatisplus.annotations.TableName;
//
//
//
///**
// *
// * <pre>
// * 检测报告
// * </pre>
// * <small> 2019-06-20 13:49:53 | Wen</small>
// */
// @TableName("report")
//public class ReportDO implements Serializable {
//    @TableField(exist = false)
//    private static final long serialVersionUID = 1L;
//
//    /**  */
//    @TableId
//    private Integer reportId;
//    /** 档案id */
//    private Integer traceabilityFileId;
//    /** 图片地址 */
//    private String imgUrl;
//    /** 状态 */
//    private Integer state;
//    /** 资源表id */
//    private Long resourceId;
//    /**  */
//    private Long createBy;
//    /**  */
//    private Date createDate;
//
//    /**
//     * 设置：
//     */
//    public void setReportId(Integer reportId) {
//        this.reportId = reportId;
//    }
//    /**
//     * 获取：
//     */
//    public Integer getReportId() {
//        return reportId;
//    }
//    /**
//     * 设置：档案id
//     */
//    public void setTraceabilityFileId(Integer traceabilityFileId) {
//        this.traceabilityFileId = traceabilityFileId;
//    }
//    /**
//     * 获取：档案id
//     */
//    public Integer getTraceabilityFileId() {
//        return traceabilityFileId;
//    }
//    /**
//     * 设置：图片地址
//     */
//    public void setImgUrl(String imgUrl) {
//        this.imgUrl = imgUrl;
//    }
//    /**
//     * 获取：图片地址
//     */
//    public String getImgUrl() {
//        return imgUrl;
//    }
//    /**
//     * 设置：状态
//     */
//    public void setState(Integer state) {
//        this.state = state;
//    }
//    /**
//     * 获取：状态
//     */
//    public Integer getState() {
//        return state;
//    }
//    /**
//     * 设置：资源表id
//     */
//    public void setResourceId(Long resourceId) {
//        this.resourceId = resourceId;
//    }
//    /**
//     * 获取：资源表id
//     */
//    public Long getResourceId() {
//        return resourceId;
//    }
//    /**
//     * 设置：
//     */
//    public void setCreateBy(Long createBy) {
//        this.createBy = createBy;
//    }
//    /**
//     * 获取：
//     */
//    public Long getCreateBy() {
//        return createBy;
//    }
//    /**
//     * 设置：
//     */
//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }
//    /**
//     * 获取：
//     */
//    public Date getCreateDate() {
//        return createDate;
//    }
//}
