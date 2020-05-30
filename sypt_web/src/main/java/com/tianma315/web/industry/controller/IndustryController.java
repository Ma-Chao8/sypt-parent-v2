package com.tianma315.web.industry.controller;


import java.util.Calendar;
import java.util.List;

import com.tianma315.commons.enumutil.StateEnum;
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

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.industry.domain.IndustryDO;
import com.tianma315.core.industry.service.IndustryService;

/**
 * 
 * <pre>
 * 行业
 * </pre>
 * <small> 2019-06-29 14:23:26 | Wen</small>
 */
@Controller
@RequestMapping("/industry/industry")
public class IndustryController extends BaseController{
	@Autowired
	private IndustryService industryService;
	
	@GetMapping()
	@RequiresPermissions("industry:industry:industry")
	String Industry(){
	    return "industry/industry/industry";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("industry:industry:industry")
	public Result<Page<IndustryDO>> list(Integer pageNumber, Integer pageSize, IndustryDO industryDTO){
		// 查询列表数据
        Page<IndustryDO> page = new Page<>(pageNumber, pageSize);
        
        Wrapper<IndustryDO> wrapper = new EntityWrapper<IndustryDO>(industryDTO);
        wrapper.eq("state", StateEnum.not_del.getIndex());
        page = industryService.selectPage(page, wrapper);
        int total = industryService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("industry:industry:add")
	String add(){
	    return "industry/industry/add";
	}

	@GetMapping("/edit/{industryId}")
	@RequiresPermissions("industry:industry:edit")
	String edit(@PathVariable("industryId") Integer industryId,Model model){
		IndustryDO industry = industryService.selectById(industryId);
		model.addAttribute("industry", industry);
	    return "industry/industry/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("industry:industry:add")
	public Result<String> save( IndustryDO industry){
		industry.setCreateBy(getUserId());
		industry.setCreateDate(Calendar.getInstance().getTime());
		industry.setState(StateEnum.not_del.getIndex());
		if(industryService.insert(industry)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("industry:industry:edit")
	public Result<String>  update( IndustryDO industry){
		industryService.updateById(industry);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("industry:industry:remove")
	public Result<String>  remove( Integer industryId){
		if(industryService.deleteIndustry(industryId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("industry:industry:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] industryIds){
//		industryService.deleteBatchIds(Arrays.asList(industryIds));
		for (Integer id:industryIds) {
			industryService.deleteIndustry(id);
		}
		return Result.ok();
	}

	@GetMapping("/all")
	@ResponseBody
	public Result all(){
		Wrapper<IndustryDO> wrapper = new EntityWrapper<IndustryDO>();
		wrapper.eq("state", StateEnum.not_del.getIndex());
		List<IndustryDO> industryDOList = industryService.selectList(wrapper);
		return Result.ok(industryDOList);
	}
	
}
