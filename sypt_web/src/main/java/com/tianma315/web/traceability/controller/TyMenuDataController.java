package com.tianma315.web.traceability.controller;


import java.util.Arrays;

import com.tianma315.core.base.Result;
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
import com.tianma315.core.traceability.domain.TyMenuDataDO;
import com.tianma315.core.traceability.service.TyMenuDataService;

/**
 * 
 * <pre>
 * 菜单数据
 * </pre>
 * <small> 2019-06-20 13:49:53 | Wen</small>
 */
@Controller
@RequestMapping("/traceability/tyMenuData")
public class TyMenuDataController {
	@Autowired
	private TyMenuDataService tyMenuDataService;
	
	@GetMapping()
	@RequiresPermissions("traceability:tyMenuData:tyMenuData")
	String TyMenuData(){
	    return "traceability/tyMenuData/tyMenuData";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("traceability:tyMenuData:tyMenuData")
	public Result<Page<TyMenuDataDO>> list(Integer pageNumber, Integer pageSize, TyMenuDataDO tyMenuDataDTO){
		// 查询列表数据
        Page<TyMenuDataDO> page = new Page<>(pageNumber, pageSize);
        
        Wrapper<TyMenuDataDO> wrapper = new EntityWrapper<TyMenuDataDO>(tyMenuDataDTO);
        page = tyMenuDataService.selectPage(page, wrapper);
        int total = tyMenuDataService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("traceability:tyMenuData:add")
	String add(){
	    return "traceability/tyMenuData/add";
	}

	@GetMapping("/edit/{menuDataId}")
	@RequiresPermissions("traceability:tyMenuData:edit")
	String edit(@PathVariable("menuDataId") Integer menuDataId,Model model){
		TyMenuDataDO tyMenuData = tyMenuDataService.selectById(menuDataId);
		model.addAttribute("tyMenuData", tyMenuData);
	    return "traceability/tyMenuData/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("traceability:tyMenuData:add")
	public Result<String> save( TyMenuDataDO tyMenuData){
		if(tyMenuDataService.insert(tyMenuData)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("traceability:tyMenuData:edit")
	public Result<String>  update( TyMenuDataDO tyMenuData){
		tyMenuDataService.updateById(tyMenuData);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("traceability:tyMenuData:remove")
	public Result<String>  remove( Integer menuDataId){
		if(tyMenuDataService.deleteById(menuDataId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("traceability:tyMenuData:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] menuDataIds){
		tyMenuDataService.deleteBatchIds(Arrays.asList(menuDataIds));
		return Result.ok();
	}
	
}
