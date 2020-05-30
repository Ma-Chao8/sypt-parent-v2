package com.tianma315.core.supplier.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.supplier.VO.SupplierAndTypeVO;
import com.tianma315.core.supplier.domain.SupplierDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <pre>
 * 供应商
 * </pre>
 * <small> 2019-06-24 09:58:51 | Wen</small>
 */
public interface SupplierDao extends CoreMapper<SupplierDO> {
    List<SupplierAndTypeVO> getSupplierAndTypeVO(@Param("page")Page page, SupplierDO supplierDO);
}
