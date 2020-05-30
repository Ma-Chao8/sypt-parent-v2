package com.tianma315.web.attr.controller;


import java.util.Arrays;
import java.util.List;


import com.tianma315.core.attr.domain.AttrFieldDO;
import com.tianma315.core.attr.service.AttrFieldService;
import com.tianma315.core.attr.vo.AttrFieldAndFieldVO;
import com.tianma315.core.attr.vo.AttrFieldVO;

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
 * 辅助属性字段
 * </pre>
 * <small> 2019-08-02 15:38:24 | Wen</small>
 */
@Controller
@RequestMapping("/attr/attrField")
public class AttrFieldController {
	@Autowired
	private AttrFieldService attrFieldService;
	
	@GetMapping()
//	@RequiresPermissions("attr:attrField:attrField")
	String AttrField(){
	    return "attr/attrField/attrField";
	}

	@GetMapping("/attrFieldMain/{attrId}")
//	@RequiresPermissions("attr:attrField:attrField")
	String attrFieldMain(@PathVariable("attrId")Integer attrId,Model model){
		model.addAttribute("attrId",attrId);
		return "attr/attrField/attrFieldMain";
	}
	
	@ResponseBody
	@GetMapping("/list")
//	@RequiresPermissions("attr:attrField:attrField")
	public Result<Page<AttrFieldDO>> list(Integer pageNumber, Integer pageSize, AttrFieldDO attrFieldDTO){
		// 查询列表数据
        Page<AttrFieldDO> page = new Page<>(pageNumber, pageSize);
        
        Wrapper<AttrFieldDO> wrapper = new EntityWrapper<AttrFieldDO>(attrFieldDTO);
        page = attrFieldService.selectPage(page, wrapper);
        int total = attrFieldService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
	}
	
	@GetMapping("/add/{attrId}")
//	@RequiresPermissions("attr:attrField:add")
	String add(@PathVariable("attrId")Integer attrId,Model model){
		model.addAttribute("attrId",attrId);
	    return "attr/attrField/add";
	}

	@GetMapping("/edit/{attrFieldId}")
//	@RequiresPermissions("attr:attrField:edit")
	String edit(@PathVariable("attrFieldId") Integer attrFieldId,Model model){
		AttrFieldAndFieldVO attrField = attrFieldService.getAttrFieldById(attrFieldId);
		model.addAttribute("attrField", attrField);
	    return "attr/attrField/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
//	@RequiresPermissions("attr:attrField:add")
	public Result<String> save( AttrFieldVO attrField){
		if(attrFieldService.addAttrFieldVO(attrField)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
//	@RequiresPermissions("attr:attrField:edit")
	public Result<String>  update( AttrFieldVO attrField){
		attrFieldService.updateAttrFieldDO(attrField);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
//	@RequiresPermissions("attr:attrField:remove")
	public Result<String>  remove( Integer attrFieldId){
		if(attrFieldService.deleteAttrFieldDO(attrFieldId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
//	@RequiresPermissions("attr:attrField:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] attrFieldIds){
		attrFieldService.deleteBatchIds(Arrays.asList(attrFieldIds));
		return Result.ok();
	}

	@PostMapping("getAttrFieldByAttr")
	@ResponseBody
	public Result<List<AttrFieldAndFieldVO>> getAttrFieldByAttr(AttrFieldDO attrFieldDO){
		List<AttrFieldAndFieldVO> attrFieldAndFieldVOList = attrFieldService.getAttrFieldVO(attrFieldDO);
		return Result.ok(attrFieldAndFieldVOList);
	}
	
}
