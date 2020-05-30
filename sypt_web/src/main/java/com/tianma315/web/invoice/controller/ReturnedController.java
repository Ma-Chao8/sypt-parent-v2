package com.tianma315.web.invoice.controller;


import java.util.Arrays;
import java.util.List;

import com.tianma315.core.invoice.domain.dto.ReturnedDto;
import com.tianma315.core.invoice.domain.vo.ReturnedProductVO;
import com.tianma315.core.invoice.domain.vo.ReturnedVO;
import com.tianma315.core.base.Result;
import com.tianma315.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.invoice.domain.ReturnedDO;
import com.tianma315.core.invoice.service.ReturnedService;


/**
 * 
 * <pre>
 * 退货记录
 * </pre>
 * <small> 2019-08-21 14:57:42 | Lgc</small>
 */
@Controller
@RequestMapping("/invoice/returned")
public class ReturnedController extends BaseController {
	@Autowired
	private ReturnedService returnedService;
	
	@GetMapping()
	@RequiresPermissions("invoice:returned:returned")
	String Returned(){
	    return "invoice/returned/returned";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("invoice:returned:returned")
	public Result<Page<ReturnedVO>> list(Integer pageNumber, Integer pageSize, ReturnedDto returnedDTO){
		// 查询列表数据
		Page<ReturnedVO> page = new Page<>(pageNumber, pageSize);
		returnedDTO.setCompanyId(getCompanyId());
		page=returnedService.getPage(page,returnedDTO);
        return Result.ok(page);
	}



	
	@GetMapping("/add")
	@RequiresPermissions("invoice:returned:add")
	String add(){
	    return "invoice/returned/add";
	}

	@GetMapping("/details/{returnedId}")
	@RequiresPermissions("invoice:returned:edit")
	String edit(@PathVariable("returnedId") Long returnedId,Model model){
		//ReturnedDO returned = returnedService.selectById(returnedId);
		model.addAttribute("returnId", returnedId);
	    return "invoice/returned/re_details";
	}

	@ResponseBody
	@GetMapping("/getDetails/{returnedId}")
	Result<List<ReturnedProductVO>> getDetails(@PathVariable("returnedId") Long returnedId, Model model){
		List<ReturnedProductVO> returnedVO = returnedService.getDetails(returnedId);
		System.out.println(returnedVO);
			if (returnedVO != null)
				return Result.ok(returnedVO);
			return Result.fail();
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("invoice:returned:add")
	public Result<String> save( ReturnedDO returned){
		if(returnedService.insert(returned)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("invoice:returned:edit")
	public Result<String>  update( ReturnedDO returned){
		returnedService.updateById(returned);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("invoice:returned:remove")
	public Result<String>  remove( Long returnedId){
		if(returnedService.deleteById(returnedId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("invoice:returned:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Long[] returnedIds){
		returnedService.deleteBatchIds(Arrays.asList(returnedIds));
		return Result.ok();
	}
	
}
