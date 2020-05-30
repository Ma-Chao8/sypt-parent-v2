package com.tianma315.core.warehouse.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.warehouse.domain.WarehouseRecordCodeDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WarehouseRecordCodeDao extends CoreMapper<WarehouseRecordCodeDO> {
   List<WarehouseRecordCodeDO> selectWarehouseRecordCodeDO (@Param("id") Integer id);
   int update(WarehouseRecordCodeDO warehouseRecordCodeDO);


   WarehouseRecordCodeDO getWarehouseRecordCodeDOByCode(String code);
}
