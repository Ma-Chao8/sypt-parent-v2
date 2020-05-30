package com.tianma315.web.report.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.report.domain.CheckTypeDO;
import com.tianma315.core.report.service.CheckTypeService;
import com.tianma315.core.base.Result;
import com.tianma315.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;


/**
 * 
 * <pre>
 * 检测类型
 * </pre>
 * <small> 2019-06-27 17:10:49 | Wen</small>
 */
@Controller
@RequestMapping("/check/checkType")
public class CheckTypeController extends BaseController{
	@Autowired
	private CheckTypeService checkTypeService;
	
	@GetMapping()
	@RequiresPermissions("check:checkType:checkType")
	String CheckType(){
	    return "report/checkType/checkType";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("check:checkType:checkType")
	public Result<Page<CheckTypeDO>> list(Integer pageNumber, Integer pageSize, CheckTypeDO checkTypeDTO){
		// 查询列表数据
        Page<CheckTypeDO> page = new Page<>(pageNumber, pageSize);
        
        Wrapper<CheckTypeDO> wrapper = new EntityWrapper<CheckTypeDO>(checkTypeDTO);
        wrapper.eq("state",StateEnum.not_del.getIndex());
        page = checkTypeService.selectPage(page, wrapper);
        int total = checkTypeService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("check:checkType:add")
	String add(){
	    return "report/checkType/add";
	}

	@GetMapping("/edit/{checkTypeId}")
	@RequiresPermissions("check:checkType:edit")
	String edit(@PathVariable("checkTypeId") Long checkTypeId,Model model){
		CheckTypeDO checkType = checkTypeService.selectById(checkTypeId);
		model.addAttribute("checkType", checkType);
	    return "report/checkType/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("check:checkType:add")
	public Result<String> save( CheckTypeDO checkType){
		checkType.setState(StateEnum.not_del.getIndex());
		checkType.setCreateBy(getUserId());
		checkType.setCreateDate(Calendar.getInstance().getTime());
		if(checkTypeService.insert(checkType)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("check:checkType:edit")
	public Result<String>  update( CheckTypeDO checkType){
		checkTypeService.updateById(checkType);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("check:checkType:remove")
	public Result<String>  remove( Long checkTypeId){
		if(checkTypeService.deleteCheckType(checkTypeId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("check:checkType:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Long[] checkTypeIds){
//		checkTypeService.deleteBatchIds(Arrays.asList(checkTypeIds));
		for (Long id : checkTypeIds){
			checkTypeService.deleteCheckType(id);
		}
		return Result.ok();
	}

	@ResponseBody
	@GetMapping("/all")
	public Result<List<CheckTypeDO>> all( CheckTypeDO checkTypeDTO){
		// 查询列表数据
		Wrapper<CheckTypeDO> wrapper = new EntityWrapper<CheckTypeDO>(checkTypeDTO);
		wrapper.eq("state",StateEnum.not_del.getIndex());
		List<CheckTypeDO> checkTypeDOS = checkTypeService.selectList( wrapper);
		return Result.ok(checkTypeDOS);
	}
	
}
