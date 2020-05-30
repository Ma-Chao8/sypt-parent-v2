package com.tianma315.core.attr.service;



import com.tianma315.core.attr.domain.AttrFieldDO;
import com.tianma315.core.attr.vo.AttrFieldAndFieldVO;
import com.tianma315.core.attr.vo.AttrFieldVO;
import com.tianma315.core.base.CoreService;


import java.util.List;

/**
 * 
 * <pre>
 * 辅助属性字段
 * </pre>
 * <small> 2019-08-02 15:38:24 | Wen</small>
 */
public interface AttrFieldService extends CoreService<AttrFieldDO> {

    List<AttrFieldAndFieldVO> getAttrFieldVO(AttrFieldDO attrFieldDO);
    AttrFieldAndFieldVO getAttrFieldById(Integer id);
    Boolean addAttrFieldVO(AttrFieldVO attrFieldVO);
    Boolean deleteAttrFieldDO(Integer id);
    Boolean updateAttrFieldDO(AttrFieldVO attrFieldVO);
    Boolean checkAttrRepeat(AttrFieldDO attrFieldDO);

    
}
