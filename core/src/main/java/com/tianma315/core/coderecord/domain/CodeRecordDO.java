package com.tianma315.core.coderecord.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 溯源码查询记录
 * </pre>
 * <small> 2019-06-26 16:36:01 | Wen</small>
 */
 @TableName("code_record")
public class CodeRecordDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @TableField(exist = false)
    public static final int STATUS_DELETE = 0;
    @TableField(exist = false)
    public static final int STATUS_ENABLE = 1;

    /**  */
    @TableId
    private Integer codeRecordId;
    /** 小码 */
    private String smallCode;
    /** 查询次数 */
    private Integer recordNumber;
    /** 首次查询时间 */
    private Date firstQueryDate;
    /**  */
    private String bigCode;
    /** 对应的序号 */
    private Integer serialNumber;
    /**  */
    private Integer traceabilityFileId;

    /**
     * 设置：
     */
    public void setCodeRecordId(Integer codeRecordId) {
        this.codeRecordId = codeRecordId;
    }
    /**
     * 获取：
     */
    public Integer getCodeRecordId() {
        return codeRecordId;
    }
    /**
     * 设置：小码
     */
    public void setSmallCode(String smallCode) {
        this.smallCode = smallCode;
    }
    /**
     * 获取：小码
     */
    public String getSmallCode() {
        return smallCode;
    }
    /**
     * 设置：查询次数
     */
    public void setRecordNumber(Integer recordNumber) {
        this.recordNumber = recordNumber;
    }
    /**
     * 获取：查询次数
     */
    public Integer getRecordNumber() {
        return recordNumber;
    }
    /**
     * 设置：首次查询时间
     */
    public void setFirstQueryDate(Date firstQueryDate) {
        this.firstQueryDate = firstQueryDate;
    }
    /**
     * 获取：首次查询时间
     */
    public Date getFirstQueryDate() {
        return firstQueryDate;
    }
    /**
     * 设置：
     */
    public void setBigCode(String bigCode) {
        this.bigCode = bigCode;
    }
    /**
     * 获取：
     */
    public String getBigCode() {
        return bigCode;
    }
    /**
     * 设置：对应的序号
     */
    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }
    /**
     * 获取：对应的序号
     */
    public Integer getSerialNumber() {
        return serialNumber;
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
}
