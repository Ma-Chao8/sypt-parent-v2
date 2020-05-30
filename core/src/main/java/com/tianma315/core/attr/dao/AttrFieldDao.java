package com.tianma315.core.attr.dao;



import com.tianma315.core.attr.domain.AttrFieldDO;
import com.tianma315.core.attr.vo.AttrFieldAndFieldVO;
import com.tianma315.core.base.CoreMapper;

import java.util.List;

/**
 * 
 * <pre>
 * 辅助属性字段
 * </pre>
 * <small> 2019-08-02 15:38:24 | Wen</small>
 */
public interface AttrFieldDao extends CoreMapper<AttrFieldDO> {

    List<AttrFieldAndFieldVO> getAttrFieldAndField(AttrFieldDO attrFieldDO);

}
