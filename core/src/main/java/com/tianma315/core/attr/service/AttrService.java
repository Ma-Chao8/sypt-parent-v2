package com.tianma315.core.attr.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.attr.domain.AttrDO;
import com.tianma315.core.base.CoreService;


import java.util.List;

/**
 * 
 * <pre>
 * 辅助属性
 * </pre>
 * <small> 2019-08-02 15:38:24 | Wen</small>
 */
public interface AttrService extends CoreService<AttrDO> {

    List<AttrDO> getAttrList(AttrDO attrDO);

    Page<AttrDO> getAttrDOPage(Integer pageNumber, Integer pageSize, AttrDO attrDTO);

    Boolean addAttrDO(AttrDO attrDO);

    Boolean deleteAttrDO(Integer id);

}
