package com.tianma315.core.coderecord.domain.pojo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

@TableName("scan_record")
public class ScanRecord {
    @TableId
    private Long scanRecordId;//` bigint(20) NOT NULL AUTO_INCREMENT,
    private String openId;//` varchar(255) DEFAULT NULL,
    private String code;//` varchar(255) DEFAULT NULL COMMENT '码',
    private Date createdDate;//` datetime DEFAULT NULL,
    private Long provinceId;//` int(11) DEFAULT NULL,
    private Long productId;//` int(11) DEFAULT NULL,
    private Long companyId;//` bigint(20) DEFAULT NULL,
    private Long traceFileId;//` bigint(20) DEFAULT NULL COMMENT '档案id',
    private Integer scanNumber;//` int(11) DEFAULT NULL COMMENT '扫码次数',

    public Long getScanRecordId() {
        return scanRecordId;
    }

    public void setScanRecordId(Long scanRecordId) {
        this.scanRecordId = scanRecordId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getTraceFileId() {
        return traceFileId;
    }

    public void setTraceFileId(Long traceFileId) {
        this.traceFileId = traceFileId;
    }

    public Integer getScanNumber() {
        return scanNumber;
    }

    public void setScanNumber(Integer scanNumber) {
        this.scanNumber = scanNumber;
    }
}
