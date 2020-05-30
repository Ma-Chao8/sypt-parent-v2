package com.tianma315.core.small_code.vo;

import java.util.Date;
import java.util.List;

public class SmallRecordListVO {
    private String bigCode;
    private List<String> smallCodes;
    private Integer id;
    private Integer productId;
    private Date createDate;

    public String getBigCode() {
        return bigCode;
    }

    public void setBigCode(String bigCode) {
        this.bigCode = bigCode;
    }

    public List<String> getSmallCodes() {
        return smallCodes;
    }

    public void setSmallCodes(List<String> smallCodes) {
        this.smallCodes = smallCodes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
