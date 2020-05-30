package com.tianma315.core.supplier.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.supplier.domain.SupplierTypeDO;

import java.util.List;

/**
 * 
 * <pre>
 * 供应商类型
 * </pre>
 * <small> 2019-06-24 09:58:51 | Wen</small>
 */
public interface SupplierTypeService extends CoreService<SupplierTypeDO> {
    boolean deleteSupplierTypeById(Integer id);

    Page<SupplierTypeDO> getSupplierTypePage(Integer pageNumber, Integer pageSize, SupplierTypeDO supplierTypeDTO);

    boolean addSupplierType(SupplierTypeDO supplierTypeDO);

    List<SupplierTypeDO> getSupplierTypeList(SupplierTypeDO supplierTypeDO);
    
}
