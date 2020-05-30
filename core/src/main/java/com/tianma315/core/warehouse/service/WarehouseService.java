package com.tianma315.core.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.warehouse.domain.WarehouseDO;
import com.tianma315.core.warehouse.domain.dto.WarehouseDto;

import java.util.List;


/**
 * 
 * <pre>
 * 仓库
 * </pre>
 * <small> 2019-08-20 15:09:27 | Lgc</small>
 */
public interface WarehouseService extends CoreService<WarehouseDO> {

    Page<WarehouseDO> getWarehousePage(Integer pageNumber, Integer pageSize, WarehouseDto warehouseDTO);

    Integer selectCountByCompanyId(Long companyId);

    boolean deleteWarehouseStatus(Long warehouseId, Integer status);

    List<WarehouseDO> warehouseDOList(Long companyId);
}
