package com.tianma315.core.supplier.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.base.CoreServiceImpl;
import org.springframework.stereotype.Service;

import com.tianma315.core.supplier.dao.SupplierTypeDao;
import com.tianma315.core.supplier.domain.SupplierTypeDO;
import com.tianma315.core.supplier.service.SupplierTypeService;

import java.util.List;

/**
 * 
 * <pre>
 * 供应商类型
 * </pre>
 * <small> 2019-06-24 09:58:51 | Wen</small>
 */
@Service
public class SupplierTypeServiceImpl extends CoreServiceImpl<SupplierTypeDao, SupplierTypeDO> implements SupplierTypeService {


    @Override
    public boolean deleteSupplierTypeById(Integer id) {
        SupplierTypeDO supplierTypeDO = new SupplierTypeDO();
        supplierTypeDO.setState(StateEnum.del.getIndex());
        supplierTypeDO.setSupplierTypeId(id);
        boolean flag = updateById(supplierTypeDO);
        return flag;
    }

    @Override
    public Page<SupplierTypeDO> getSupplierTypePage(Integer pageNumber, Integer pageSize, SupplierTypeDO supplierTypeDTO) {
        // 查询列表数据
        Page<SupplierTypeDO> page = new Page<>(pageNumber, pageSize);
        Wrapper<SupplierTypeDO> wrapper = new EntityWrapper<SupplierTypeDO>();
        wrapper.eq("state",StateEnum.not_del.getIndex())
                .eq("company_id",supplierTypeDTO.getCompanyId())
                .like("type_name",supplierTypeDTO.getTypeName());

        page = selectPage(page, wrapper);
        int total = selectCount(wrapper);
        page.setTotal(total);
        return page;
    }

    @Override
    public boolean addSupplierType(SupplierTypeDO supplierTypeDO) {
        supplierTypeDO.setState(StateEnum.not_del.getIndex());
        boolean flag = insert(supplierTypeDO);
        return flag;
    }

    @Override
    public List<SupplierTypeDO> getSupplierTypeList(SupplierTypeDO supplierTypeDO) {
        Wrapper<SupplierTypeDO> wrapper = new EntityWrapper<SupplierTypeDO>();
        wrapper.eq("state", StateEnum.not_del.getIndex());
        wrapper.eq("company_id",supplierTypeDO.getCompanyId());
        List<SupplierTypeDO> supplierTypeDOList = selectList(wrapper);
        return supplierTypeDOList;
    }
}
