package com.tianma315.core.warehouse.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.warehouse.domain.WarehouseManagerDO;
import com.tianma315.core.warehouse.domain.vo.WarehouseManagerVo;

import java.util.List;


/**
 * 
 * <pre>
 * 产品员工关联
 * </pre>
 * <small> 2019-08-20 15:11:51 | Lgc</small>
 */
public interface WarehouseManagerDao extends CoreMapper<WarehouseManagerDO> {

    List<WarehouseManagerVo> getPagelist(WarehouseManagerDO warehouseManagerDTO);

    WarehouseManagerVo selectVoById(Long id);

    WarehouseManagerVo selectVoByUserId(Long userId);

}
