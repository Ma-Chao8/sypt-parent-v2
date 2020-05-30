package com.tianma315.core.procedures.service;


import com.tianma315.core.base.CoreService;
import com.tianma315.core.procedures.domain.ProceduresProductDO;
import com.tianma315.core.procedures.vo.ProceduresProductVO;

import java.util.List;

/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
public interface ProceduresProductService extends CoreService<ProceduresProductDO> {

    List<ProceduresProductDO> getProceduresProductDOlist(ProceduresProductDO proceduresProductDO);

    void addProceduresProduct(ProceduresProductVO proceduresProductVO);
    
}
