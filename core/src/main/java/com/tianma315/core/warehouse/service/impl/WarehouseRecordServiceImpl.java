package com.tianma315.core.warehouse.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.invoice.domain.vo.ScanCodeVO;
import com.tianma315.core.invoice.vo.ScanCodeVo;
import com.tianma315.core.warehouse.dao.WarehouseRecordCodeDao;
import com.tianma315.core.warehouse.domain.WarehouseRecordCodeDO;
import com.tianma315.core.warehouse.domain.dto.WarehouseRecordDto;
import com.tianma315.core.warehouse.domain.vo.WarehouseRecordForm;
import com.tianma315.core.warehouse.domain.vo.WarehouseRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tianma315.core.warehouse.dao.WarehouseRecordDao;
import com.tianma315.core.warehouse.domain.WarehouseRecordDO;
import com.tianma315.core.warehouse.service.WarehouseRecordService;

import java.util.Date;
import java.util.List;

/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-08-22 15:33:17 | wen</small>
 */
@Service
public class WarehouseRecordServiceImpl extends CoreServiceImpl<WarehouseRecordDao, WarehouseRecordDO> implements WarehouseRecordService {

    @Autowired
    WarehouseRecordDao warehouseRecordDao;
    @Autowired
    WarehouseRecordCodeDao warehouseRecordCodeDao;


    @Override
    public Page<WarehouseRecordVo> selectRecordPage(Page<WarehouseRecordVo> page, WarehouseRecordDto warehouseRecordDTO) {
        List<WarehouseRecordVo> list=warehouseRecordDao.selectRecordPage(page,warehouseRecordDTO);
        page.setRecords(list);
        return page;

    }

    @Override
    public boolean changeSmallCode(String id, String smallCode) {
        WarehouseRecordCodeDO warehouseRecordCodeDO = warehouseRecordCodeDao.selectById(id);
        if (warehouseRecordCodeDO == null)
            throw new MessageException("当前记录不存在");
        warehouseRecordCodeDO.setSmallCode(smallCode);
        if (warehouseRecordCodeDao.update(warehouseRecordCodeDO) != 1)
            throw new MessageException(smallCode + "修改失败");
        return true;
    }

    @Override
    public boolean save(WarehouseRecordForm form , Long userId) {
        if (form == null)
            throw new MessageException("发货数据获取失败");

        if (form.getProducts() == null || form.getProducts().equals(""))
            throw new MessageException("请选择产品");


//        //添加入库单
//        WarehouseRecordDO warehouseRecordDO = new WarehouseRecordDO();
//        warehouseRecordDO.setCreateBy(userId);
//        warehouseRecordDO.setNumber(form.getProducts().getCodes().size());
//        warehouseRecordDO.setCreateDate(new Date());
//        warehouseRecordDO.setProductId((int)form.getProducts().getProductId());
//        if (warehouseRecordDao.insert(warehouseRecordDO)!=1){
//             throw new MessageException("入库单创建失败");
//        }
//
//        //如果有大码就添加一条code数据
//        for(ScanCodeVo scanCodeVo  :  form.getProducts().getCodes()){
//            if (scanCodeVo.getType() == ScanCodeVo.TYPE_BIG_CODE){
//                WarehouseRecordCodeDO warehouseRecordCodeDO = new WarehouseRecordCodeDO();
//                warehouseRecordCodeDO.setWarehouseRecordId(warehouseRecordDO.getId());
//                warehouseRecordCodeDO.setBigCode(scanCodeVo.getBigCode());
//            }
//        }

        return false;
    }
}
