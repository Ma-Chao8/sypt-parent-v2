package com.tianma315.core.traceability.vo;

import java.util.Date;

/**
 * @author lgc
 * @createDate 2020/5/15 - 11:22
 */
public class TraceInfoVo extends TraceabilityFileVO{
    private String code;
    private int queryNumber; //查询次数
    private Date firstQueryDate; //首次查询时间

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQueryNumber() {
        return queryNumber;
    }

    public void setQueryNumber(int queryNumber) {
        this.queryNumber = queryNumber;
    }

    public Date getFirstQueryDate() {
        return firstQueryDate;
    }

    public void setFirstQueryDate(Date firstQueryDate) {
        this.firstQueryDate = firstQueryDate;
    }
}
