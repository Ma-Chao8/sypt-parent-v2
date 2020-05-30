package com.tianma315.core.material.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.material.domain.MaterialDO;
import com.tianma315.core.material.vo.MaterialAndTemplateVO;

import java.util.List;

/**
 * 
 * <pre>
 * 原材料
 * </pre>
 * <small> 2019-06-24 11:23:31 | Wen</small>
 */
public interface MaterialDao extends CoreMapper<MaterialDO> {
    List<MaterialAndTemplateVO> getMaterialAndTemplateVOPage();
}
