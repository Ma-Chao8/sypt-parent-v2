package com.tianma315.core.trace.service.impl;


import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.trace.dao.TraceOutFieldDao;
import com.tianma315.core.trace.domain.TraceOutFieldDO;
import com.tianma315.core.trace.service.TraceOutFieldService;

import com.tianma315.core.trace.vo.TraceOutAttrFieldVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;

/**
 * 
 * <pre>
 * 批次-字段属性
 * </pre>
 * <small> 2019-08-08 15:02:50 | Wb</small>
 */
@Service
public class TraceOutFieldServiceImpl extends CoreServiceImpl<TraceOutFieldDao, TraceOutFieldDO> implements TraceOutFieldService {

    @Autowired
    private TraceOutFieldDao traceOutFieldDao;

    @Override
    public List<TraceOutAttrFieldVO> getTraceOutAttrFieldByTraceOutId(Integer traceOutId) {
        List<TraceOutAttrFieldVO> list = traceOutFieldDao.getTraceOutAttrFieldByTraceOutId(traceOutId);
        return list;

    }
}
