package com.tianma315.core.invoice.domain.dto;

import com.tianma315.core.invoice.domain.ReturnedDO;

import java.util.Date;

/**
 * 用来接收时间的参数实体类
 *  by LGC on 2019.08.23
 */
public class ReturnedDto extends ReturnedDO {
    private String beginDate;
    private String endDate;
    private String sortName;
    private String sortOrder;

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
