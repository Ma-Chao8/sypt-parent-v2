package com.tianma315.web.report.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.report.domain.CheckMechanismDO;
import com.tianma315.core.report.service.CheckMechanismService;
import com.tianma315.core.base.Result;
import com.tianma315.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 
 * <pre>
 * 检测机构
 * </pre>
 * <small> 2019-06-27 17:10:49 | Wen</small>
 */
@Controller
@RequestMapping("/check/checkMechanism")
public class CheckMechanismController extends BaseController{
	@Autowired
	private CheckMechanismService checkMechanismService;
	
	@GetMapping()
	@RequiresPermissions("check:checkMechanism:checkMechanism")
	String CheckMechanism(){
	    return "report/checkMechanism/checkMechanism";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("check:checkMechanism:checkMechanism")
	public Result<Page<CheckMechanismDO>> list(Integer pageNumber, Integer pageSize, CheckMechanismDO checkMechanismDTO){
		// 查询列表数据
        Page<CheckMechanismDO> page = new Page<>(pageNumber, pageSize);
        
        Wrapper<CheckMechanismDO> wrapper = new EntityWrapper<CheckMechanismDO>(checkMechanismDTO);
        wrapper.eq("state",StateEnum.not_del.getIndex());
        page = checkMechanismService.selectPage(page, wrapper);
        int total = checkMechanismService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("check:checkMechanism:add")
	String add(){
	    return "report/checkMechanism/add";
	}

	@GetMapping("/edit/{checkMechanismId}")
	@RequiresPermissions("check:checkMechanism:edit")
	String edit(@PathVariable("checkMechanismId") Long checkMechanismId,Model model){
		CheckMechanismDO checkMechanism = checkMechanismService.selectById(checkMechanismId);
		model.addAttribute("checkMechanism", checkMechanism);
	    return "report/checkMechanism/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("check:checkMechanism:add")
	public Result<String> save( CheckMechanismDO checkMechanism){
		checkMechanism.setState(StateEnum.not_del.getIndex());
		checkMechanism.setCreateBy(getUserId());
		checkMechanism.setCreateDate(new Date());
		if(checkMechanismService.insert(checkMechanism)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("check:checkMechanism:edit")
	public Result<String>  update( CheckMechanismDO checkMechanism){
		checkMechanismService.updateById(checkMechanism);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("check:checkMechanism:remove")
	public Result<String>  remove( Long checkMechanismId){
		if(checkMechanismService.deleteCheckMechanism(checkMechanismId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("check:checkMechanism:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Long[] checkMechanismIds){
//		checkMechanismService.deleteBatchIds(Arrays.asList(checkMechanismIds));
		for (Long id : checkMechanismIds){
			checkMechanismService.deleteCheckMechanism(id);
		}
		return Result.ok();
	}

	@ResponseBody
	@GetMapping("/all")
	public Result<List<CheckMechanismDO>> all(String checkMechanismName){
		// 查询列表数据
		//System.out.println("####################"+checkMechanismName);
		Wrapper<CheckMechanismDO> wrapper = new EntityWrapper<CheckMechanismDO>();
		wrapper.like("check_mechanism_name",checkMechanismName);
		wrapper.eq("state",StateEnum.not_del.getIndex());
		List<CheckMechanismDO> checkMechanismDOS = checkMechanismService.selectList( wrapper);
		return Result.ok(checkMechanismDOS);
	}
	
}
