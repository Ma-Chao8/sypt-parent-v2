package com.tianma315.core.trace.vo;



import com.tianma315.core.trace.domain.TraceOutDO;
import com.tianma315.core.trace.domain.TraceOutFieldDO;

import java.util.List;

public class TraceOutAndFieldVO extends TraceOutDO {
    private List<TraceOutFieldDO> traceOutFieldDOList;

    public List<TraceOutFieldDO> getTraceOutFieldDOList() {
        return traceOutFieldDOList;
    }

    public void setTraceOutFieldDOList(List<TraceOutFieldDO> traceOutFieldDOList) {
        this.traceOutFieldDOList = traceOutFieldDOList;
    }
}
