package com.tianma315.web.warehouse.controller;



import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.base.Result;
import com.tianma315.core.warehouse.domain.WarehouseDO;
import com.tianma315.core.warehouse.domain.dto.WarehouseDto;
import com.tianma315.core.warehouse.service.WarehouseService;
import com.tianma315.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 
 * <pre>
 * 仓库
 * </pre>
 * <small> 2019-08-20 15:09:27 | Lgc</small>
 */
@Controller
@RequestMapping("/warehouse/warehouse")
public class WarehouseController extends BaseController {
	@Autowired
	private WarehouseService warehouseService;
	
	@GetMapping()
	@RequiresPermissions("warehouse:warehouse:warehouse")
	String Warehouse(){
	    return "warehouse/warehouse/warehouse";
	}



	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("warehouse:warehouse:warehouse")
	public Result<Page<WarehouseDO>> list(Integer pageNumber, Integer pageSize, WarehouseDto warehouseDTO){
		// 查询列表数据
		warehouseDTO.setCompanyId(getCompanyId());
		Page<WarehouseDO> page = warehouseService.getWarehousePage(pageNumber,pageSize,warehouseDTO);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("warehouse:warehouse:add")
	String add(){
	    return "warehouse/warehouse/add";
	}

	@GetMapping("/edit/{warehouseId}")
	@RequiresPermissions("warehouse:warehouse:edit")
	String edit(@PathVariable("warehouseId") Long warehouseId,Model model){
		WarehouseDO warehouse = warehouseService.selectById(warehouseId);
		model.addAttribute("warehouse", warehouse);
	    return "warehouse/warehouse/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("warehouse:warehouse:add")
	public Result<String> save( WarehouseDO warehouse){
		Integer i=warehouseService.selectCountByCompanyId(getCompanyId());
		if (i>=5){
			throw new MessageException("每个商户最多不能超过5个仓库");
		}
		warehouse.setStatus(1);
		warehouse.setCreateDate(new Date());
		warehouse.setCompanyId(getCompanyId());
		if (warehouseService.insert(warehouse))
			return Result.ok();
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("warehouse:warehouse:edit")
	public Result<String>  update( WarehouseDO warehouse){
		warehouseService.updateById(warehouse);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("warehouse:warehouse:remove")
	public Result<String>  remove( Long warehouseId){
		if(warehouseService.deleteById(warehouseId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("warehouse:warehouse:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Long[] warehouseIds){
		warehouseService.deleteBatchIds(Arrays.asList(warehouseIds));
		return Result.ok();
	}


	/**
	 * 修改状态
	 */
	@PostMapping( "/status")
	@ResponseBody
	public Result<String>  status ( Long warehouseId,Integer status){
		if(warehouseService.deleteWarehouseStatus(warehouseId,status)){
			return Result.ok();
		}
		return Result.fail();
	}

	@GetMapping( "/all")
	@ResponseBody
	public Result<List<WarehouseDO>> all(){
		List<WarehouseDO> warehouseDOS = warehouseService.warehouseDOList(getCompanyId());
//		for (WarehouseDO warehouseDO : warehouseDOS){
////			System.out.println("#####+"+warehouseDO);
////		}
		return Result.ok(warehouseDOS);
	}
}
