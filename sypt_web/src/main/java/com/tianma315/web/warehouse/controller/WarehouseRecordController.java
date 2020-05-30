package com.tianma315.web.warehouse.controller;


import java.util.Arrays;
import java.util.List;

import com.tianma315.core.base.Result;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.utils.BeanHump;
import com.tianma315.core.warehouse.domain.WarehouseRecordCodeDO;
import com.tianma315.core.warehouse.domain.dto.WarehouseRecordDto;
import com.tianma315.core.warehouse.domain.vo.WarehouseRecordVo;
import com.tianma315.core.warehouse.service.WarehouseRecordCodeService;
import com.tianma315.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.warehouse.domain.WarehouseRecordDO;
import com.tianma315.core.warehouse.service.WarehouseRecordService;

/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-08-22 15:33:17 | wen</small>
 */
@Controller
@RequestMapping("/warehouse/warehouseRecord")
public class WarehouseRecordController extends BaseController {
	@Autowired
	private WarehouseRecordService warehouseRecordService;
	@Autowired
	private WarehouseRecordCodeService warehouseRecordCodeService;
	
	@GetMapping()
	@RequiresPermissions("warehouse:warehouseRecord:warehouseRecord")
	String WarehouseRecord(){
	    return "warehouse/warehouseRecord/warehouseRecord";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("warehouse:warehouseRecord:warehouseRecord")
	public Result<Page<WarehouseRecordVo>> list(Integer pageNumber, Integer pageSize, WarehouseRecordDto warehouseRecordDTO){
		// 查询列表数据
		//System.out.println("#####################========="+warehouseRecordDTO.getSearchName());
		warehouseRecordDTO.setSortName(BeanHump.camelToUnderline(warehouseRecordDTO.getSortName()));
		warehouseRecordDTO.setCompanyId(getCompanyId());
        Page<WarehouseRecordVo> page = new Page<>(pageNumber, pageSize,warehouseRecordDTO.getSortName());
		if (warehouseRecordDTO.getSortOrder().equals("desc")){
			page.setAsc(false);
		}
        page = warehouseRecordService.selectRecordPage(page, warehouseRecordDTO);
        return Result.ok(page);
	}

	@ResponseBody
	@GetMapping("/getCodeById/{id}")
	@RequiresPermissions("warehouse:warehouseRecord:warehouseRecord")
	public Result<List<WarehouseRecordCodeDO>> getCodeById(@PathVariable String id){

		Wrapper<WarehouseRecordCodeDO> entity= new EntityWrapper<>();
		entity.eq("warehouse_record_id",id);
		List<WarehouseRecordCodeDO> warehouseRecordCodeDOS = warehouseRecordCodeService.selectList(entity);

		return Result.ok(warehouseRecordCodeDOS);
	}

	/**
	 * 根据id去修改小码
	 * @param id
	 * @param smallCode
	 * @return
	 */
	@PostMapping("/change")
	@ResponseBody
	@RequiresPermissions("warehouse:warehouseRecord:edit")
	Result changeSmallCode(@RequestParam String id, @RequestParam String smallCode) {
		try {
			if (warehouseRecordService.changeSmallCode(id, smallCode))
				return Result.ok();

			return Result.fail();
		} catch (MessageException e) {
			e.printStackTrace();
			return Result.fail(e.getMessage());
		}


	}


	@GetMapping("/selects/{id}")
	String bindProduct(@PathVariable Integer id, Model model){

		model.addAttribute("id",id);
		return "warehouse/warehouseRecord/selects";
	}



}
