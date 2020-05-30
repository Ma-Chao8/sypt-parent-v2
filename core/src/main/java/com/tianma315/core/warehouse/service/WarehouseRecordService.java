package com.tianma315.core.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.warehouse.domain.WarehouseRecordDO;
import com.tianma315.core.warehouse.domain.dto.WarehouseRecordDto;
import com.tianma315.core.warehouse.domain.vo.WarehouseRecordForm;
import com.tianma315.core.warehouse.domain.vo.WarehouseRecordVo;

/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-08-22 15:33:17 | wen</small>
 */
public interface WarehouseRecordService extends CoreService<WarehouseRecordDO> {


    Page<WarehouseRecordVo> selectRecordPage(Page<WarehouseRecordVo> page, WarehouseRecordDto warehouseRecordDTO);

    boolean changeSmallCode(String id, String smallCode);

    //扫码入库
    boolean save(WarehouseRecordForm form, Long userId);
}
