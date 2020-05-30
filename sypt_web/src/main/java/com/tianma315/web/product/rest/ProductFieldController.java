package com.tianma315.web.product.rest;


import java.util.Arrays;


import com.tianma315.core.base.Result;
import com.tianma315.core.product.domain.pojo.ProductFieldDO;
import com.tianma315.core.product.service.ProductFieldService;
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
 * 产品-字段
 * </pre>
 * <small> 2019-08-06 14:41:19 | Wen</small>
 */
@Controller
@RequestMapping("/product/productField")
public class ProductFieldController {
	@Autowired
	private ProductFieldService productFieldService;
	
	@GetMapping()
	@RequiresPermissions("product:productField:productField")
	String ProductField(){
	    return "product/productField/productField";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("product:productField:productField")
	public Result<Page<ProductFieldDO>> list(Integer pageNumber, Integer pageSize, ProductFieldDO productFieldDTO){
		// 查询列表数据
        Page<ProductFieldDO> page = new Page<>(pageNumber, pageSize);
        
        Wrapper<ProductFieldDO> wrapper = new EntityWrapper<ProductFieldDO>(productFieldDTO);
        page = productFieldService.selectPage(page, wrapper);
        int total = productFieldService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("product:productField:add")
	String add(){
	    return "product/productField/add";
	}

	@GetMapping("/edit/{productId}")
	@RequiresPermissions("product:productField:edit")
	String edit(@PathVariable("productId") Integer productId,Model model){
		ProductFieldDO productField = productFieldService.selectById(productId);
		model.addAttribute("productField", productField);
	    return "product/productField/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("product:productField:add")
	public Result<String> save( ProductFieldDO productField){
		if(productFieldService.insert(productField)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("product:productField:edit")
	public Result<String>  update( ProductFieldDO productField){
		productFieldService.updateById(productField);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("product:productField:remove")
	public Result<String>  remove( Integer productId){
		if(productFieldService.deleteById(productId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("product:productField:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] productIds){
		productFieldService.deleteBatchIds(Arrays.asList(productIds));
		return Result.ok();
	}
	
}
