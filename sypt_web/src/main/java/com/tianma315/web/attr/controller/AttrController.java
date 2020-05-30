package com.tianma315.web.attr.controller;


import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


import com.tianma315.core.attr.domain.AttrDO;
import com.tianma315.core.attr.service.AttrService;

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


/**
 * 
 * <pre>
 * 辅助属性
 * </pre>
 * <small> 2019-08-02 15:38:24 | Wen</small>
 */
@Controller
@RequestMapping("/attr/attr")
public class AttrController extends BaseController {
	@Autowired
	private AttrService attrService;
	
	@GetMapping()
	@RequiresPermissions("attr:attr:attr")
	String Attr(){
	    return "attr/attr/attr";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("attr:attr:attr")
	public Result<Page<AttrDO>> list(Integer pageNumber, Integer pageSize, AttrDO attrDTO){
		// 查询列表数据
//		attrDTO.setMerchantId(getMerchantId());
        Page<AttrDO> page = attrService.getAttrDOPage(pageNumber,pageSize,attrDTO);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("attr:attr:add")
	String add(){
	    return "attr/attr/add";
	}

	@GetMapping("/edit/{attrId}")
	@RequiresPermissions("attr:attr:edit")
	String edit(@PathVariable("attrId") Integer attrId,Model model){
		AttrDO attr = attrService.selectById(attrId);
		model.addAttribute("attr", attr);
	    return "attr/attr/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("attr:attr:add")
	public Result<String> save( AttrDO attr){
//		attr.setMerchantId(getMerchantId());
		if(attrService.addAttrDO(attr)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("attr:attr:edit")
	public Result<String>  update( AttrDO attr){
		attr.setLsUpdateDate(Calendar.getInstance().getTime());
		attrService.updateById(attr);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("attr:attr:remove")
	public Result<String>  remove( Integer attrId){
		if(attrService.deleteAttrDO(attrId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("attr:attr:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] attrIds){
//		attrService.deleteBatchIds(Arrays.asList(attrIds));
		for (Integer id : attrIds) {
			attrService.deleteAttrDO(id);
		}
		return Result.ok();
	}

	@ResponseBody
	@GetMapping("/all")
	public Result<List<AttrDO>> all(){
		// 查询列表数据
		AttrDO attrDO = new AttrDO();
		attrDO.setCompanyId(getCompanyId());
		List<AttrDO> list = attrService.getAttrList(attrDO);
		return Result.ok(list);
	}
	
}
