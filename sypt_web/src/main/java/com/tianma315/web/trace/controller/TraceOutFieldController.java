package com.tianma315.web.trace.controller;


import java.util.Arrays;


import com.tianma315.core.base.Result;
import com.tianma315.core.trace.domain.TraceOutFieldDO;
import com.tianma315.core.trace.service.TraceOutFieldService;
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
 * 批次-字段属性
 * </pre>
 * <small> 2019-08-08 15:02:50 | Wb</small>
 */
@Controller
@RequestMapping("/trace/traceOutField")
public class TraceOutFieldController {
	@Autowired
	private TraceOutFieldService traceOutFieldService;
	
	@GetMapping()
	@RequiresPermissions("trace:traceOutField:traceOutField")
	String TraceOutField(){
	    return "trace/traceOutField/traceOutField";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("trace:traceOutField:traceOutField")
	public Result<Page<TraceOutFieldDO>> list(Integer pageNumber, Integer pageSize, TraceOutFieldDO traceOutFieldDTO){
		// 查询列表数据
        Page<TraceOutFieldDO> page = new Page<>(pageNumber, pageSize);
        
        Wrapper<TraceOutFieldDO> wrapper = new EntityWrapper<TraceOutFieldDO>(traceOutFieldDTO);
        page = traceOutFieldService.selectPage(page, wrapper);
        int total = traceOutFieldService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("trace:traceOutField:add")
	String add(){
	    return "trace/traceOutField/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("trace:traceOutField:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		TraceOutFieldDO traceOutField = traceOutFieldService.selectById(id);
		model.addAttribute("traceOutField", traceOutField);
	    return "trace/traceOutField/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("trace:traceOutField:add")
	public Result<String> save( TraceOutFieldDO traceOutField){
		if(traceOutFieldService.insert(traceOutField)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("trace:traceOutField:edit")
	public Result<String>  update( TraceOutFieldDO traceOutField){
		traceOutFieldService.updateById(traceOutField);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("trace:traceOutField:remove")
	public Result<String>  remove( Integer id){
		if(traceOutFieldService.deleteById(id)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("trace:traceOutField:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] ids){
		traceOutFieldService.deleteBatchIds(Arrays.asList(ids));
		return Result.ok();
	}
	
}
