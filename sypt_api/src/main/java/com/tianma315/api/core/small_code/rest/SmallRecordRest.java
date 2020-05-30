package com.tianma315.api.core.small_code.rest;


import com.tianma315.api.core.controller.BaseRest;
import com.tianma315.core.base.Result;
import com.tianma315.core.small_code.service.SmallRecordService;
import com.tianma315.core.small_code.vo.SmallRecordVO;
import com.tianma315.core.warehouse.domain.vo.WarehouseManagerVo;
import com.tianma315.core.warehouse.service.WarehouseManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 
 * <pre>
 * 外包装管理记录
 * </pre>
 * <small> 2019-08-10 10:06:48 | Wen</small>
 */
@Controller
@RequestMapping("/small_code/smallRecord")
public class SmallRecordRest extends BaseRest{
	@Autowired
	private SmallRecordService smallRecordService;

	@Autowired
	private WarehouseManagerService warehouseManagerService;

	@GetMapping("/product")
//	@RequiresPermissions("small_code:smallRecord:smallRecord")
	String smallBox(){
		return "small_code/smallRecord/product";
	}

	@GetMapping("/bindProductInStock")
	String bindProductInStock(){
		return "small_code/smallRecord/bindProductInStock";
	}

	/**
	 * 保存记录，type为0只关联，type为1关联并入库
	 */
	@ResponseBody
	@PostMapping("/saveRecord")
	public Result<String> saveRecord(@RequestBody SmallRecordVO smallRecord){
		smallRecord.setCompanyId(getCompanyId());
		smallRecord.setCreateBy(getUserId());
		WarehouseManagerVo warehouseManagerVo = warehouseManagerService.selectVoByUserId(getUserId());
		smallRecord.setWarehouseId(warehouseManagerVo.getWarehouseId());
		if (smallRecord.getType().equals(0)){
			if(smallRecordService.addSmallRecord(smallRecord)){
				return Result.ok();
			}
		}else{
			if(smallRecordService.addSmallRecordAndStock(smallRecord)){
				return Result.ok();
			}
		}

		return Result.fail();
	}

	/**
	 * 保存
	 * type为0小码，type为1大码
	 */
	@ResponseBody
	@PostMapping("/inSmallRecord")
	public Result<String> inSmallRecord(@RequestBody SmallRecordVO smallRecord){
		smallRecord.setCompanyId(getCompanyId());
		smallRecord.setCreateBy(getUserId());
		if(smallRecordService.inSmallRecord(smallRecord)){
			return Result.ok();
		}
		return Result.fail();
	}
	
}
