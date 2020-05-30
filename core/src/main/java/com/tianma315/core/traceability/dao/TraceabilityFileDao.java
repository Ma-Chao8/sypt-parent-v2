package com.tianma315.core.traceability.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.traceability.domain.TraceabilityFileDO;
import com.tianma315.core.traceability.vo.TraceabilityFileVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 溯源档案
 * </pre>
 * <small> 2019-06-20 13:49:53 | Wen</small>
 */
public interface TraceabilityFileDao extends CoreMapper<TraceabilityFileDO> {
    List<TraceabilityFileVO> selectPage(@Param("page") Page page, TraceabilityFileVO traceabilityFileVO);

    List<TraceabilityFileDO> traceabilityFileCheck(TraceabilityFileDO traceabilityFile);

    List<TraceabilityFileDO> decideTraceabilityFile(@Param("productId") Long productId, @Param("deliverDate") Date date, @Param("merchantId") Integer merchantId);

    /**
     * 查询档案详细信息
     *
     * @param id
     * @return
     */
    TraceabilityFileVO selectInfoId(long id);
}
