package com.tianma315.web.attr.controller;


import java.util.Arrays;

import com.tianma315.core.attr.domain.FieldValueDO;
import com.tianma315.core.attr.service.FieldValueService;

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


/**
 * 
 * <pre>
 * 字段-值
 * </pre>
 * <small> 2019-08-02 15:38:24 | Wen</small>
 */
@Controller
@RequestMapping("/attr/fieldValue")
public class FieldValueController {
	@Autowired
	private FieldValueService fieldValueService;
	
	@GetMapping()
	@RequiresPermissions("attr:fieldValue:fieldValue")
	String FieldValue(){
	    return "attr/fieldValue/fieldValue";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("attr:fieldValue:fieldValue")
	public Result<Page<FieldValueDO>> list(Integer pageNumber, Integer pageSize, FieldValueDO fieldValueDTO){
		// 查询列表数据
        Page<FieldValueDO> page = new Page<>(pageNumber, pageSize);
        
        Wrapper<FieldValueDO> wrapper = new EntityWrapper<FieldValueDO>(fieldValueDTO);
        page = fieldValueService.selectPage(page, wrapper);
        int total = fieldValueService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("attr:fieldValue:add")
	String add(){
	    return "attr/fieldValue/add";
	}

	@GetMapping("/edit/{fieldValueId}")
	@RequiresPermissions("attr:fieldValue:edit")
	String edit(@PathVariable("fieldValueId") Integer fieldValueId,Model model){
		FieldValueDO fieldValue = fieldValueService.selectById(fieldValueId);
		model.addAttribute("fieldValue", fieldValue);
	    return "attr/fieldValue/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("attr:fieldValue:add")
	public Result<String> save( FieldValueDO fieldValue){
		if(fieldValueService.insert(fieldValue)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("attr:fieldValue:edit")
	public Result<String>  update( FieldValueDO fieldValue){
		fieldValueService.updateById(fieldValue);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("attr:fieldValue:remove")
	public Result<String>  remove( Integer fieldValueId){
		if(fieldValueService.deleteById(fieldValueId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("attr:fieldValue:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] fieldValueIds){
		fieldValueService.deleteBatchIds(Arrays.asList(fieldValueIds));
		return Result.ok();
	}
	
}
