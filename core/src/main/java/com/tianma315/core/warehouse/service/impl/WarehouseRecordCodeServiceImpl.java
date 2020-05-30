package com.tianma315.core.warehouse.service.impl;

import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.warehouse.dao.WarehouseRecordCodeDao;
import com.tianma315.core.warehouse.domain.WarehouseRecordCodeDO;
import com.tianma315.core.warehouse.service.WarehouseRecordCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseRecordCodeServiceImpl  extends CoreServiceImpl<WarehouseRecordCodeDao, WarehouseRecordCodeDO> implements WarehouseRecordCodeService {

    @Autowired
    WarehouseRecordCodeDao warehouseRecordCodeDao;


    @Override
    public WarehouseRecordCodeDO getWarehouseRecordCodeDOByCode(String codes) {
        return warehouseRecordCodeDao.getWarehouseRecordCodeDOByCode(codes);
    }
}
