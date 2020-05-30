package com.tianma315.core.procedures.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.procedures.domain.ProceduresDO;
import com.tianma315.core.procedures.vo.ProceduresVO;

import java.util.List;

/**
 * 
 * <pre>
 * 溯源流程模板
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
public interface ProceduresService extends CoreService<ProceduresDO> {
    Page<ProceduresVO> selectPage(Integer pageNumber, Integer pageSize, ProceduresDO proceduresDTO);

    Boolean addProcedures(ProceduresDO proceduresDO);

    Boolean updateProcedures(ProceduresDO proceduresDO);

    Boolean deleteProcedures(Integer id);

    List<ProceduresDO> selectList(ProceduresDO proceduresDTO);

    ProceduresDO getProcedureDO(Integer id);
    
}
