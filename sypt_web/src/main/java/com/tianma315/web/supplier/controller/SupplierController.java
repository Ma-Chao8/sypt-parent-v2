package com.tianma315.web.supplier.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import com.tianma315.core.supplier.VO.SupplierAndTypeVO;
import com.tianma315.core.utils.ResponseUtil;
import com.tianma315.core.base.Result;
import com.tianma315.web.base.BaseController;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.supplier.domain.SupplierDO;
import com.tianma315.core.supplier.service.SupplierService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * <pre>
 * 供应商
 * </pre>
 * <small> 2019-06-24 09:58:51 | Wen</small>
 */
@Controller
@RequestMapping("/supplier/supplier")
public class SupplierController extends BaseController {
	@Autowired
	private SupplierService supplierService;
	
	@GetMapping()
	@RequiresPermissions("supplier:supplier:supplier")
	String Supplier(){
	    return "supplier/supplier/supplier";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("supplier:supplier:supplier")
	public Result<Page<SupplierAndTypeVO>> list(Integer pageNumber, Integer pageSize, SupplierDO supplierDTO){
		// 查询列表数据
		supplierDTO.setCompanyId(getCompanyId());
		Page<SupplierAndTypeVO> page = supplierService.getSupplierDOPage(pageNumber,pageSize,supplierDTO);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("supplier:supplier:add")
	String add(){
	    return "supplier/supplier/add";
	}

	@GetMapping("/edit/{supplierId}")
	@RequiresPermissions("supplier:supplier:edit")
	String edit(@PathVariable("supplierId") Integer supplierId,Model model){
		SupplierDO supplier = supplierService.selectById(supplierId);
		model.addAttribute("supplier", supplier);
	    return "supplier/supplier/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("supplier:supplier:add")
	public Result<String> save(SupplierDO supplier){
		supplier.setCompanyId(getCompanyId());
		supplier.setCreateBy(getUserId());
		supplier.setCreateDate(new Date());
		if(supplierService.addSupplierDO(supplier)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("supplier:supplier:edit")
	public Result<String>  update( SupplierDO supplier){
		supplierService.updateById(supplier);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("supplier:supplier:remove")
	public Result<String>  remove( Integer supplierId){
		if(supplierService.deleteSupplierDOById(supplierId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("supplier:supplier:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] supplierIds){
		for (int i = 0; i < supplierIds.length; i++) {
			supplierService.deleteSupplierDOById(supplierIds[i]);
		}
		return Result.ok();
	}

	/**
	 * 所有数据
	 */
	@GetMapping( "/getSupplierList")
	@ResponseBody
	public Result<List<SupplierDO>>  getSupplierList(SupplierDO supplierDO){
		List<SupplierDO> supplierDOList = supplierService.getSupplierList(getCompanyId());
		return Result.ok(supplierDOList);
	}

	/**
	 * 导出模板
	 */
	@GetMapping( "/exportSupplier")
	public void exportSupplier(HttpServletResponse response){
		String fileName = "供应商导入模板.xls";
		try {
			response = ResponseUtil.getResponse(response,fileName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HSSFWorkbook wb =supplierService.exportSupplier(getCompanyId(),getUserId());
		try(OutputStream os = response.getOutputStream()) {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 进入导入excel
	 */
	@GetMapping( "/supplierImport")
	public String supplierImport(MultipartFile file){
		return "supplier/supplier/supplierImport";
	}

	/**
	 * 导入excel
	 */
	@ResponseBody
	@PostMapping( "/importSupplier")
	public Result importSupplier(MultipartFile file){
		if (!file.isEmpty()){
			supplierService.importSupplier(file,getCompanyId(),getUserId());
		}
		return Result.ok();
	}
	
}
