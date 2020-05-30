package com.tianma315.core.procedures.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.procedures.domain.ProcedureLinkDO;
import com.tianma315.core.procedures.vo.ProcedureLinkFileVO;

import java.util.List;

/**
 * 
 * <pre>
 * 环节
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
public interface ProcedureLinkService extends CoreService<ProcedureLinkDO> {
    Page<ProcedureLinkFileVO> getProcedureLinkDOPage(Integer pageNumber, Integer pageSize, ProcedureLinkDO procedureLinkDTO);

    Boolean addProcedureLink(ProcedureLinkFileVO procedureLinkFileVO);

    Boolean deleteProcedureLink(Integer id);

    List<ProcedureLinkDO> checkProcedureLinkSort(ProcedureLinkDO procedureLinkDO);

    List<ProcedureLinkDO> getProcedureLinkDOByProcedureId(Integer procedureId);


    Boolean updateLinkById(ProcedureLinkFileVO procedureLinkFileVO);
}
