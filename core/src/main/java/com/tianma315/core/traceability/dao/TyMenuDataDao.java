package com.tianma315.core.traceability.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.traceability.domain.TyMenuDataDO;
import com.tianma315.core.traceability.vo.TyMenuDataVO;

import java.util.List;

/**
 * 
 * <pre>
 * 菜单数据
 * </pre>
 * <small> 2019-06-20 13:49:53 | Wen</small>
 */
public interface TyMenuDataDao extends CoreMapper<TyMenuDataDO> {

    List<TyMenuDataVO> selectVOList(Integer menuId);
    boolean updateUrlById(TyMenuDataDO tyMenuDataDO);

}
