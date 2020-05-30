package com.tianma315.core.trace.service;

import com.baomidou.mybatisplus.plugins.Page;


import com.tianma315.core.base.CoreService;
import com.tianma315.core.procedures.domain.ProcedureLinkDO;
import com.tianma315.core.trace.domain.TraceOutDO;
import com.tianma315.core.trace.vo.TraceOutAndFieldVO;
import com.tianma315.core.trace.vo.TraceOutApiVO;
import com.tianma315.core.trace.vo.TraceOutVO;

import com.tianma315.core.traceability.vo.TyMenuDataVO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

/**
 * 
 * <pre>
 * 溯源批次出库记录
 * </pre>
 * <small> 2019-08-08 15:02:49 | Wb</small>
 */
public interface TraceOutService extends CoreService<TraceOutDO> {
    Page<TraceOutVO> getTraceOutPage(Integer pageNumber, Integer pageSize, TraceOutDO traceOutDTO, Date beginDate, Date endDate);
    TraceOutAndFieldVO getTraceOutById(Integer id);
    Boolean addTraceOut(TraceOutAndFieldVO traceOutAndFieldVO);
    Boolean updateTraceOut(TraceOutAndFieldVO traceOutAndFieldVO);
    Boolean deleteTraceOut(Integer id);
    Boolean checkSerial(Integer serial);
    TraceOutApiVO getTraceOutInfo(String code);
    TraceOutDO getTraceOutDOBySerial( Integer serial);

    List<ProcedureLinkDO> selectContent(Long userId,Integer productId, Integer traceabilityFileId);


    List<TyMenuDataVO> getTyMenuDataDOS(Integer id, Integer traceabilityFileId);
}
