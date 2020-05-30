package com.tianma315.core.traceability.service;

import com.alibaba.fastjson.JSONObject;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.trace.vo.CodeVo;
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
public interface TyMenuDataService extends CoreService<TyMenuDataDO> {

    Boolean insertList(CodeVo codeVo, Integer menuId, Long userId);

    List<TyMenuDataVO> selectVOList(Integer menuId);

    boolean updateUrlById(CodeVo codeVo);

    boolean updateDataById(CodeVo codeVo);
}
