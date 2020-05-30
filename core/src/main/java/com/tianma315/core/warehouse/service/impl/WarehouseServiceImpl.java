package com.tianma315.core.warehouse.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.agent.domain.AgentLevelDO;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.utils.BeanHump;
import com.tianma315.core.warehouse.domain.dto.WarehouseDto;
import org.springframework.stereotype.Service;

import com.tianma315.core.warehouse.dao.WarehouseDao;
import com.tianma315.core.warehouse.domain.WarehouseDO;
import com.tianma315.core.warehouse.service.WarehouseService;

import java.util.List;


/**
 * 
 * <pre>
 * 仓库
 * </pre>
 * <small> 2019-08-20 15:09:27 | Lgc</small>
 */
@Service
public class WarehouseServiceImpl extends CoreServiceImpl<WarehouseDao, WarehouseDO> implements WarehouseService {

    @Override
    public Page<WarehouseDO> getWarehousePage(Integer pageNumber, Integer pageSize, WarehouseDto warehouseDTO) {
        warehouseDTO.setSortName(BeanHump.camelToUnderline(warehouseDTO.getSortName()));
        Page<WarehouseDO> page = new Page<>(pageNumber,pageSize,warehouseDTO.getSortName());
        if (warehouseDTO.getSortOrder().equals("desc")){
            page.setAsc(false);
        }
        EntityWrapper<WarehouseDO> wrapper = new EntityWrapper<>();
        if (warehouseDTO.getCompanyId() !=null){
            wrapper.eq("company_id", warehouseDTO.getCompanyId());
        }
        page=selectPage(page,wrapper);
        return page;
    }

    @Override
    public Integer selectCountByCompanyId(Long companyId) {
        EntityWrapper<WarehouseDO> wrapper = new EntityWrapper<>();
        wrapper.eq("company_id",companyId);
        int i = selectCount(wrapper);
        return i;
    }

    @Override
    public boolean deleteWarehouseStatus(Long warehouseId, Integer status) {
        WarehouseDO warehouseDO = new WarehouseDO();
        warehouseDO.setStatus(status);
        warehouseDO.setWarehouseId(warehouseId);
        return updateById(warehouseDO);
    }

    @Override
    public List<WarehouseDO> warehouseDOList(Long companyId) {
        EntityWrapper<WarehouseDO> wrapper = new EntityWrapper<>();
        wrapper.eq("company_id",companyId);
        wrapper.eq("status", StateEnum.usable.getIndex());
        List<WarehouseDO> result = selectList(wrapper);
        return result;
    }
}
