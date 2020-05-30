package com.tianma315.core.traceability.service;

import com.tianma315.core.base.CoreService;
import com.tianma315.core.traceability.domain.TyMenuDO;
import com.tianma315.core.traceability.vo.TyMenuDataVO;
import com.tianma315.core.traceability.vo.TyMenuVO;

import java.util.List;

/**
 * 
 * <pre>
 * 溯源档案菜单(环节)
 * </pre>
 * <small> 2019-06-20 13:49:53 | Wen</small>
 */
public interface TyMenuService extends CoreService<TyMenuDO> {

//    Boolean deleteTyMenu(Integer traceabilityFileId);
//
    List<TyMenuVO> getTyMenuByTraceabilityFileId(Integer traceabilityFileId);

//    TyMenuVO addTyMenuVO(TyMenuDataVO[] menuVO, Integer traceabilityFileId, Long userId);
}
