package com.tianma315.core.warehouse.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.invoice.domain.dto.InvoiceDto;
import com.tianma315.core.invoice.domain.dto.ReturnedDto;
import com.tianma315.core.invoice.domain.vo.InvoiceVO;
import com.tianma315.core.invoice.domain.vo.ReturnedVO;
import com.tianma315.core.warehouse.domain.WarehouseRecordDO;
import com.tianma315.core.warehouse.domain.dto.WarehouseRecordDto;
import com.tianma315.core.warehouse.domain.vo.WarehouseRecordVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-08-22 15:33:17 | wen</small>
 */
public interface WarehouseRecordDao extends CoreMapper<WarehouseRecordDO> {

    List<WarehouseRecordVo> selectRecordPage(Page<WarehouseRecordVo> page, WarehouseRecordDto warehouseRecordDTO);

}
