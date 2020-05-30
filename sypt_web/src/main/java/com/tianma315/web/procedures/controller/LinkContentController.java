package com.tianma315.web.procedures.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.procedures.domain.LinkContentDO;
import com.tianma315.core.procedures.domain.dto.LinkContentDto;
import com.tianma315.core.procedures.service.LinkContentService;
import com.tianma315.core.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 
 * <pre>
 * 环节内容表
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */

@Controller
@RequestMapping("/procedures/linkContent")
public class LinkContentController {
	@Autowired
	private LinkContentService linkContentService;
	
	@GetMapping()
//	@RequiresPermissions("procedures:linkContent:linkContent")
	String LinkContent(){
	    return "procedures/linkContent/linkContent";
	}

	@GetMapping("/{id}")
	String LinkContent(@PathVariable Integer id,Model model){
		model.addAttribute("id",id);
		return "procedures/linkContent/linkContent";
	}
	
	@ResponseBody
	@GetMapping("/list")
//	@RequiresPermissions("procedures:linkContent:linkContent")
	public Result<Page<LinkContentDO>> list(Integer pageNumber, Integer pageSize, LinkContentDO linkContentDTO){
		// 查询列表数据
        Page<LinkContentDO> page = linkContentService.getLinkContentPage(pageNumber,pageSize,linkContentDTO);
        return Result.ok(page);
	}
	
	@GetMapping("/add/{proceduresLinkId}")
//	@RequiresPermissions("procedures:linkContent:add")
	String add(@PathVariable Integer proceduresLinkId,  Model model){
		model.addAttribute("proceduresLinkId",proceduresLinkId);
	    return "procedures/linkContent/add";
	}

	@GetMapping("/edit/{linkContentId}")
//	@RequiresPermissions("procedures:linkContent:edit")
	String edit(@PathVariable("linkContentId") Integer linkContentId,Model model){
		LinkContentDO linkContent = linkContentService.selectById(linkContentId);
		model.addAttribute("linkContent", linkContent);
	    return "procedures/linkContent/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
//	@RequiresPermissions("procedures:linkContent:add")
	public Result<String> save(@RequestBody LinkContentDto linkContent){
		List<LinkContentDO> list = linkContentService.getLinkContentList(linkContent);
		if (list!=null && list.size()>0){
			return Result.fail("排序值不能重复",null);
		}

		if(linkContentService.addLinkContent(linkContent)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
//	@RequiresPermissions("procedures:linkContent:edit")
	public Result<String>  update( LinkContentDO linkContent){
		List<LinkContentDO> list = linkContentService.getLinkContentList(linkContent);
		if (list!=null && list.size()>0){

			return Result.fail("排序值不能重复",null);
		}
		linkContentService.updateById(linkContent);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
//	@RequiresPermissions("procedures:linkContent:remove")
	public Result<String>  remove( Integer linkContentId){
		if(linkContentService.deleteLinkContent(linkContentId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
//	@RequiresPermissions("procedures:linkContent:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] linkContentIds){
		for (Integer id:linkContentIds) {
			linkContentService.deleteLinkContent(id);
		}
		return Result.ok();
	}
	
}
