package com.tianma315.web.coderecord.controller;


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
import com.tianma315.core.coderecord.domain.CodeRecordDO;
import com.tianma315.core.coderecord.service.CodeRecordService;

/**
 * 
 * <pre>
 * 溯源码查询记录
 * </pre>
 * <small> 2019-06-26 16:36:01 | Wen</small>
 */
@Controller
@RequestMapping("/coderecord/codeRecord")
public class CodeRecordController {
	@Autowired
	private CodeRecordService codeRecordService;
	
	@GetMapping()
	@RequiresPermissions("coderecord:codeRecord:codeRecord")
	String CodeRecord(){
	    return "coderecord/codeRecord/codeRecord";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("coderecord:codeRecord:codeRecord")
	public Result<Page<CodeRecordDO>> list(Integer pageNumber, Integer pageSize, CodeRecordDO codeRecordDTO){
		// 查询列表数据
        Page<CodeRecordDO> page = new Page<>(pageNumber, pageSize);
        
        Wrapper<CodeRecordDO> wrapper = new EntityWrapper<CodeRecordDO>(codeRecordDTO);
        page = codeRecordService.selectPage(page, wrapper);
        int total = codeRecordService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("coderecord:codeRecord:add")
	String add(){
	    return "coderecord/codeRecord/add";
	}

	@GetMapping("/edit/{codeRecordId}")
	@RequiresPermissions("coderecord:codeRecord:edit")
	String edit(@PathVariable("codeRecordId") Integer codeRecordId,Model model){
		CodeRecordDO codeRecord = codeRecordService.selectById(codeRecordId);
		model.addAttribute("codeRecord", codeRecord);
	    return "coderecord/codeRecord/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("coderecord:codeRecord:add")
	public Result<String> save( CodeRecordDO codeRecord){
		if(codeRecordService.insert(codeRecord)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("coderecord:codeRecord:edit")
	public Result<String>  update( CodeRecordDO codeRecord){
		codeRecordService.updateById(codeRecord);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("coderecord:codeRecord:remove")
	public Result<String>  remove( Integer codeRecordId){
		if(codeRecordService.deleteById(codeRecordId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("coderecord:codeRecord:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] codeRecordIds){
		codeRecordService.deleteBatchIds(Arrays.asList(codeRecordIds));
		return Result.ok();
	}
	
}
