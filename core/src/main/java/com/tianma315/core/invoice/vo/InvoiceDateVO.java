package com.tianma315.core.invoice.vo;

import java.io.Serializable;

public class InvoiceDateVO  implements Serializable {
    private String year;
    private String month;
    private Long companyId;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "InvoiceDateVO{" +
                "year=" + year +
                ", month=" + month +
                ", companyId=" + companyId +
                '}';
    }
}
