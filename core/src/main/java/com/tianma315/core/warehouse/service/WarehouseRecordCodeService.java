package com.tianma315.core.warehouse.service;


import com.tianma315.core.base.CoreService;
import com.tianma315.core.warehouse.domain.WarehouseRecordCodeDO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface WarehouseRecordCodeService  extends CoreService<WarehouseRecordCodeDO> {



    WarehouseRecordCodeDO getWarehouseRecordCodeDOByCode(String code);
}
