package com.tianma315.core.trace.service;




import com.tianma315.core.base.CoreService;
import com.tianma315.core.trace.domain.TraceOutFieldDO;
import com.tianma315.core.trace.vo.TraceOutAttrFieldVO;

import java.util.List;

/**
 * 
 * <pre>
 * 批次-字段属性
 * </pre>
 * <small> 2019-08-08 15:02:50 | Wb</small>
 */
public interface TraceOutFieldService extends CoreService<TraceOutFieldDO> {
    List<TraceOutAttrFieldVO> getTraceOutAttrFieldByTraceOutId(Integer traceOutId);
}
