package com.tianma315.core.attr.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.attr.dao.AttrDao;
import com.tianma315.core.attr.domain.AttrDO;
import com.tianma315.core.attr.service.AttrService;

import com.tianma315.core.base.CoreServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * <pre>
 * 辅助属性
 * </pre>
 * <small> 2019-08-02 15:38:24 | Wen</small>
 */
@Service
public class AttrServiceImpl extends CoreServiceImpl<AttrDao, AttrDO> implements AttrService {

    @Override
    public List<AttrDO> getAttrList(AttrDO attrDO) {
        Wrapper<AttrDO> wrapper = new EntityWrapper<>();
        wrapper.eq("state", StateEnum.not_del.getIndex());
//        wrapper.eq("merchant_id",attrDO.getMerchantId());
        List<AttrDO> attrDOS= selectList(wrapper);
        return attrDOS;
    }

    @Override
    public Page<AttrDO> getAttrDOPage(Integer pageNumber, Integer pageSize, AttrDO attrDTO) {
        // 查询列表数据
        Page<AttrDO> page = new Page<>(pageNumber, pageSize);

        Wrapper<AttrDO> wrapper = new EntityWrapper<>();
        wrapper.eq("state", StateEnum.not_del.getIndex());
//        wrapper.eq("merchant_id",attrDTO.getMerchantId());
        wrapper.like("attr_name",attrDTO.getAttrName());
        page = selectPage(page, wrapper);
        int total = selectCount(wrapper);
        page.setTotal(total);
        return page;
    }

    @Override
    public Boolean addAttrDO(AttrDO attrDO) {
        attrDO.setState(StateEnum.not_del.getIndex());
        Boolean result = insert(attrDO);
        return result;
    }

    @Override
    public Boolean deleteAttrDO(Integer id) {
        AttrDO attrDO = new AttrDO();
        attrDO.setState(StateEnum.del.getIndex());
        attrDO.setAttrId(id);
        Boolean result = updateById(attrDO);
        return result;
    }
}
