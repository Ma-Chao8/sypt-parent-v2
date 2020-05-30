package com.tianma315.web.report.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.report.domain.ReportImgDO;
import com.tianma315.core.report.service.ReportImgService;
import com.tianma315.core.base.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-06-25 13:58:06 | Wen</small>
 */
@Controller
@RequestMapping("/report/reportImg")
public class ReportImgController {
	@Autowired
	private ReportImgService reportImgService;
	
	@GetMapping()
	@RequiresPermissions("report:reportImg:reportImg")
	String ReportImg(){
	    return "report/reportImg/reportImg";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("report:reportImg:reportImg")
	public Result<Page<ReportImgDO>> list(Integer pageNumber, Integer pageSize, ReportImgDO reportImgDTO){
		// 查询列表数据
        Page<ReportImgDO> page = new Page<>(pageNumber, pageSize);
        
        Wrapper<ReportImgDO> wrapper = new EntityWrapper<ReportImgDO>(reportImgDTO);
        page = reportImgService.selectPage(page, wrapper);
        int total = reportImgService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("report:reportImg:add")
	String add(){
	    return "report/reportImg/add";
	}

	@GetMapping("/edit/{reportImgId}")
	@RequiresPermissions("report:reportImg:edit")
	String edit(@PathVariable("reportImgId") Integer reportImgId,Model model){
		ReportImgDO reportImg = reportImgService.selectById(reportImgId);
		model.addAttribute("reportImg", reportImg);
	    return "report/reportImg/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("report:reportImg:add")
	public Result<String> save( ReportImgDO reportImg){
		if(reportImgService.insert(reportImg)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("report:reportImg:edit")
	public Result<String>  update( ReportImgDO reportImg){
		reportImgService.updateById(reportImg);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("report:reportImg:remove")
	public Result<String>  remove( Integer reportImgId){
		if(reportImgService.deleteById(reportImgId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("report:reportImg:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] reportImgIds){
		reportImgService.deleteBatchIds(Arrays.asList(reportImgIds));
		return Result.ok();
	}
	
}
