package com.tianma315.core.traceability.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.traceability.domain.TraceabilityFileDO;
import com.tianma315.core.traceability.vo.ProceduresDetailVO;
import com.tianma315.core.traceability.vo.TraceInfoVo;
import com.tianma315.core.traceability.vo.TraceabilityFileVO;

import java.util.List;

/**
 * <pre>
 * 溯源档案
 * </pre>
 * <small> 2019-06-20 13:49:53 | Wen</small>
 */
public interface TraceabilityFileService extends CoreService<TraceabilityFileDO> {
    Page<TraceabilityFileVO> getTraceabilityFilePage(Integer pageNumber, Integer pageSize, TraceabilityFileVO traceabilityFileVO);

    List<TraceabilityFileDO> getTraceabilityFileList(TraceabilityFileDO TraceabilityFileDO);

    TraceabilityFileDO getTraceabilityFileById(Integer id);

    Boolean updateTraceabilityFile(TraceabilityFileDO traceabilityFile);

    Boolean cancelTraceabilityFile(Integer id, Integer state);

    Boolean addTraceabilityFile(TraceabilityFileDO traceabilityFile);

    List<TraceabilityFileDO> traceabilityFileCheck(TraceabilityFileDO traceabilityFile);

    ProceduresDetailVO getProceduresInfoView(Integer proceduresId);

    /**
     * 获取分页数据
     *
     * @param pageNumber
     * @param pageSize
     * @param searchKey
     * @param companyId
     * @param sortName
     * @param sortOrder
     * @return
     */
    Page<? extends TraceabilityFileDO> getPage(int pageNumber, int pageSize, String searchKey, Long companyId, String sortName, String sortOrder);

    boolean updateStateById(Integer traceabilityFileId);

    List<TraceabilityFileDO> getTraceabilityFileDOList();

    JSONObject getDataByTraceabilityFileId(Integer id);

    /**
     * 获取溯源档案信息
     *
     * @param id
     * @return
     */
    TraceabilityFileVO getTraceabilityFileInfo(long id);

    boolean inserts(TraceabilityFileDO traceabilityFile);

    TraceInfoVo getTraceInfo(String ident, String code);
}
