package com.tianma315.web.traceability.controller;


import java.util.Arrays;
import java.util.List;

import com.tianma315.core.traceability.service.TraceabilityFileService;
import com.tianma315.core.traceability.vo.TyMenuDataVO;
import com.tianma315.core.traceability.vo.TyMenuVO;
import com.tianma315.core.base.Result;
import com.tianma315.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.traceability.domain.TyMenuDO;
import com.tianma315.core.traceability.service.TyMenuService;

/**
 * 
 * <pre>
 * 溯源档案菜单(环节)
 * </pre>
 * <small> 2019-06-20 13:49:53 | Wen</small>
 */
@Controller
@RequestMapping("/traceability/tyMenu")
public class TyMenuController extends BaseController{
	@Autowired
	private TyMenuService tyMenuService;

	@Autowired
	private TraceabilityFileService traceabilityFileService;
	
	@GetMapping()
	@RequiresPermissions("traceability:tyMenu:tyMenu")
	String TyMenu(){
	    return "traceability/tyMenu/tyMenu";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("traceability:tyMenu:tyMenu")
	public Result<Page<TyMenuDO>> list(Integer pageNumber, Integer pageSize, TyMenuDO tyMenuDTO){
		// 查询列表数据
        Page<TyMenuDO> page = new Page<>(pageNumber, pageSize);
        
        Wrapper<TyMenuDO> wrapper = new EntityWrapper<TyMenuDO>(tyMenuDTO);
        page = tyMenuService.selectPage(page, wrapper);
        int total = tyMenuService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("traceability:tyMenu:add")
	String add(){
	    return "traceability/tyMenu/add";
	}

	@GetMapping("/edit/{menuId}")
	@RequiresPermissions("traceability:tyMenu:edit")
	String edit(@PathVariable("menuId") Integer menuId,Model model){
		TyMenuDO tyMenu = tyMenuService.selectById(menuId);
		model.addAttribute("tyMenu", tyMenu);
	    return "traceability/tyMenu/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("traceability:tyMenu:add")
	public Result<String> save( TyMenuDO tyMenu){
		if(tyMenuService.insert(tyMenu)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("traceability:tyMenu:edit")
	public Result<String>  update( TyMenuDO tyMenu){
		tyMenuService.updateById(tyMenu);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("traceability:tyMenu:remove")
	public Result<String>  remove( Integer menuId){
		if(tyMenuService.deleteById(menuId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("traceability:tyMenu:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] menuIds){
		tyMenuService.deleteBatchIds(Arrays.asList(menuIds));
		return Result.ok();
	}


	/**
	 * 进入编辑流程页面
	 * @param proceduresId
	 * @param traceabilityFileId
	 * @param model
	 * @return
	 */
	@GetMapping("/toEditProcess/{proceduresId}/{traceabilityFileId}")
	public String toEditProcess(@PathVariable Integer proceduresId,@PathVariable Integer traceabilityFileId,Model model){
		model.addAttribute("proceduresId",proceduresId);
		model.addAttribute("traceabilityFileId",traceabilityFileId);
		return "traceability/tyMenu/edit_process";
	}

	/**
	 * 进入查看流程页面
	 * @param proceduresId
	 * @param traceabilityFileId
	 * @param model
	 * @return
	 */
	@GetMapping("/toViewProcess/{proceduresId}/{traceabilityFileId}")
	public String toViewProcess(@PathVariable Integer proceduresId,@PathVariable Integer traceabilityFileId,Model model){
		model.addAttribute("proceduresId",proceduresId);
		model.addAttribute("traceabilityFileId",traceabilityFileId);
		return "traceability/tyMenu/view_process";
	}

	/**
	 * 获取流程模板信息
	 * @param trId
	 * @return
	 */
//	@ResponseBody
//	@GetMapping("/getTyMenuByTraceabilityFileId/{trId}")
//	public Result getTyMenuByTraceabilityFileId(@PathVariable Integer trId){
//		List<TyMenuVO> tyMenuVOList = tyMenuService.getTyMenuByTraceabilityFileId(trId);
//		return Result.ok(tyMenuVOList);
//	}

	/**
	 * 查看整个流程
	 * @return
	 */
	@ResponseBody
	@GetMapping("/viewProcess/{id}")
	Result viewProcess(@PathVariable("id") Integer id) {
		try {
			return Result.ok(traceabilityFileService.getProceduresInfoView(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail();
		}
	}

	/**
	 * 保存流程
	 *
	 * @param menuVO
	 * @param trId
	 * @return
	 */
//	@ResponseBody
//	@PostMapping("/saveTyMenuVO/{trId}")
//	public Result saveTyMenuVO(@RequestBody TyMenuDataVO[] menuVO, @PathVariable Integer trId){
//		tyMenuService.addTyMenuVO(menuVO,trId,getUserId());
//		return Result.ok();
//
//	}
	
}
