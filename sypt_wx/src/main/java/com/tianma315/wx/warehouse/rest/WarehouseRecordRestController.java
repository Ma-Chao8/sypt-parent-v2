package com.tianma315.wx.warehouse.rest;

import com.tianma315.core.base.Result;
import com.tianma315.core.warehouse.domain.vo.WarehouseRecordForm;
import com.tianma315.core.warehouse.service.WarehouseRecordService;
import com.tianma315.wx.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by Lgc on 2019/09/06
 */
@RestController
@RequestMapping("/warehouseRecord")
public class WarehouseRecordRestController extends BaseController {
    @Autowired
    WarehouseRecordService warehouseRecordService;

    /**
     * 扫码入库
     *
     * @param form
     * @return
     */

    @PostMapping("/save")
    Result save(@RequestBody WarehouseRecordForm form){
        if (warehouseRecordService.save(form, getUserDo().getId())){
            Result.ok("已成功入库");
        }
     return Result.fail();
    }
}
