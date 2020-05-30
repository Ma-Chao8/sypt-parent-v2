package com.tianma315.web.supplier.controller;


import java.util.List;

import com.tianma315.core.base.Result;
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

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.supplier.domain.SupplierTypeDO;
import com.tianma315.core.supplier.service.SupplierTypeService;

/**
 * 
 * <pre>
 * 供应商类型
 * </pre>
 * <small> 2019-06-24 09:58:51 | Wen</small>
 */
@Controller
@RequestMapping("/supplier/supplierType")
public class SupplierTypeController extends BaseController{
	@Autowired
	private SupplierTypeService supplierTypeService;
	
	@GetMapping()
	@RequiresPermissions("supplier:supplierType:supplierType")
	String SupplierType(){
	    return "supplier/supplierType/supplierType";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("supplier:supplierType:supplierType")
	public Result<Page<SupplierTypeDO>> list(Integer pageNumber, Integer pageSize, SupplierTypeDO supplierTypeDTO){
		// 查询列表数据
		supplierTypeDTO.setCompanyId(getCompanyId());
		Page page = supplierTypeService.getSupplierTypePage(pageNumber,pageSize,supplierTypeDTO);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("supplier:supplierType:add")
	String add(){
	    return "supplier/supplierType/add";
	}

	@GetMapping("/edit/{supplierTypeId}")
	@RequiresPermissions("supplier:supplierType:edit")
	String edit(@PathVariable("supplierTypeId") Integer supplierTypeId,Model model){
		SupplierTypeDO supplierType = supplierTypeService.selectById(supplierTypeId);
		model.addAttribute("supplierType", supplierType);
	    return "supplier/supplierType/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("supplier:supplierType:add")
	public Result<String> save( SupplierTypeDO supplierType){
		supplierType.setCompanyId(getCompanyId());
//		supplierType.se(getUserId());
//		supplierType.setCreateDate(new Date());
		if(supplierTypeService.addSupplierType(supplierType)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("supplier:supplierType:edit")
	public Result<String>  update( SupplierTypeDO supplierType){
		supplierTypeService.updateById(supplierType);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("supplier:supplierType:remove")
	public Result<String>  remove( Integer supplierTypeId){
		if(supplierTypeService.deleteSupplierTypeById(supplierTypeId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("supplier:supplierType:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] supplierTypeIds){
		for (int i = 0; i < supplierTypeIds.length; i++) {
			supplierTypeService.deleteSupplierTypeById(supplierTypeIds[i]);
		}
		return Result.ok();
	}

	@ResponseBody
	@GetMapping("/getSupplierList")
	public Result<List<SupplierTypeDO>> getSupplierList(SupplierTypeDO supplierTypeDO){
		supplierTypeDO.setCompanyId(getCompanyId());
		// 查询列表数据
		List list = supplierTypeService.getSupplierTypeList(supplierTypeDO);
		return Result.ok(list);
	}
	
}
