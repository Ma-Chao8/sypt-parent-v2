package com.tianma315.core.trace.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreMapper;



import com.tianma315.core.trace.domain.TraceOutDO;

import com.tianma315.core.trace.vo.TraceOutVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 
 * <pre>
 * 溯源批次出库记录
 * </pre>
 * <small> 2019-08-08 15:02:49 | Wb</small>
 */
public interface TraceOutDao extends CoreMapper<TraceOutDO> {

    List<TraceOutVO> getTraceOutVO(@Param("page") Page page, @Param("traceOutDO")TraceOutDO traceOutDO, @Param("beginDate")Date beginDate, @Param("endDate")Date endDate);

}
