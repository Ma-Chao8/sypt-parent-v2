package com.tianma315.web.procedures.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.procedures.domain.ProceduresProductDO;
import com.tianma315.core.procedures.service.ProceduresProductService;
import com.tianma315.core.procedures.vo.ProceduresProductVO;
import com.tianma315.core.base.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
@Controller
@RequestMapping("/procedures/proceduresProduct")
public class ProceduresProductController {
	@Autowired
	private ProceduresProductService proceduresProductService;
	
	@GetMapping()
	@RequiresPermissions("procedures:proceduresProduct:proceduresProduct")
	String ProceduresProduct(){
	    return "procedures/proceduresProduct/proceduresProduct";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("procedures:proceduresProduct:proceduresProduct")
	public Result<Page<ProceduresProductDO>> list(Integer pageNumber, Integer pageSize, ProceduresProductDO proceduresProductDTO){
		// 查询列表数据
        Page<ProceduresProductDO> page = new Page<>(pageNumber, pageSize);
        
        Wrapper<ProceduresProductDO> wrapper = new EntityWrapper<ProceduresProductDO>(proceduresProductDTO);
        page = proceduresProductService.selectPage(page, wrapper);
        int total = proceduresProductService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("procedures:proceduresProduct:add")
	String add(){
	    return "procedures/proceduresProduct/add";
	}

	@GetMapping("/edit/{procedureProductId}")
	@RequiresPermissions("procedures:proceduresProduct:edit")
	String edit(@PathVariable("procedureProductId") Integer procedureProductId,Model model){
		ProceduresProductDO proceduresProduct = proceduresProductService.selectById(procedureProductId);
		model.addAttribute("proceduresProduct", proceduresProduct);
	    return "procedures/proceduresProduct/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("procedures:proceduresProduct:add")
	public Result<String> save(ProceduresProductVO proceduresProduct){
		proceduresProductService.addProceduresProduct(proceduresProduct);
		return Result.ok();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("procedures:proceduresProduct:edit")
	public Result<String>  update( ProceduresProductDO proceduresProduct){
		proceduresProductService.updateById(proceduresProduct);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("procedures:proceduresProduct:remove")
	public Result<String>  remove( Integer procedureProductId){
		if(proceduresProductService.deleteById(procedureProductId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("procedures:proceduresProduct:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] procedureProductIds){
		proceduresProductService.deleteBatchIds(Arrays.asList(procedureProductIds));
		return Result.ok();
	}

	@GetMapping("/getProducesProductByProducesId/{id}")
	@ResponseBody
	public Result<List<ProceduresProductDO>> getProducesProductByProducesId(@PathVariable Integer id, ProceduresProductDO proceduresProductDO){
		proceduresProductDO.setProcedureId(id);
		List<ProceduresProductDO> list = proceduresProductService.getProceduresProductDOlist(proceduresProductDO);
		return Result.ok(list);
	}

	
}
