package com.tianma315.core.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.warehouse.domain.WarehouseManagerDO;
import com.tianma315.core.warehouse.domain.vo.WarehouseManagerVo;


/**
 * 
 * <pre>
 * 产品员工关联
 * </pre>
 * <small> 2019-08-20 15:11:51 | Lgc</small>
 */
public interface WarehouseManagerService extends CoreService<WarehouseManagerDO> {

    Page<WarehouseManagerVo> getPage(Integer pageNumber, Integer pageSize, WarehouseManagerDO warehouseManagerDTO);

    boolean deleteUserStatus(Long id, Integer status);


    boolean save(WarehouseManagerVo warehouseManagervo);

    WarehouseManagerVo selectVoById(Long id);

    WarehouseManagerVo selectVoByUserId(Long userId);

}
