package com.tianma315.web.material.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

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
import com.tianma315.core.material.domain.MaterialDO;
import com.tianma315.core.material.service.MaterialService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * <pre>
 * 原材料
 * </pre>
 * <small> 2019-06-24 11:23:31 | Wen</small>
 */
@Controller
@RequestMapping("/material/material")
public class MaterialController extends BaseController{
	@Autowired
	private MaterialService materialService;
	
	@GetMapping()
	@RequiresPermissions("material:material:material")
	String Material(){
	    return "material/material/material";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("material:material:material")
	public Result<Page<MaterialDO>> list(Integer pageNumber, Integer pageSize, MaterialDO materialDTO){
		// 查询列表数据
		materialDTO.setCompanyId(getCompanyId().intValue());
        Page<MaterialDO> page = materialService.getMaterialAndTemplateVOPage(pageNumber,pageSize,materialDTO);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("material:material:add")
	String add(){
	    return "material/material/add";
	}

	@GetMapping("/edit/{materialId}")
	@RequiresPermissions("material:material:edit")
	String edit(@PathVariable("materialId") Integer materialId,Model model){
		MaterialDO material = materialService.selectById(materialId);
		model.addAttribute("material", material);
	    return "material/material/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("material:material:add")
	public Result<String> save( MaterialDO material){
		material.setCompanyId(getCompanyId().intValue());
		material.setCreateBy(getUserId());
		material.setCreateDate(new Date());
		if(materialService.addMaterialAndTemplate(material)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("material:material:edit")
	public Result<String>  update( MaterialDO material){
		materialService.updateById(material);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("material:material:remove")
	public Result<String>  remove( Integer materialId){
		if(materialService.deleteMaterial(materialId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("material:material:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] materialIds){
//		materialService.deleteBatchIds(Arrays.asList(materialIds));
		for (Integer id:materialIds) {
			materialService.deleteMaterial(id);
		}
		return Result.ok();
	}

	@ResponseBody
	@GetMapping("/getMaterialList")
	public Result<List<MaterialDO>> getMaterialList(){
		List<MaterialDO> materialDOS = materialService.getMaterialList(getCompanyId());
		return Result.ok(materialDOS);
	}

	/**
	 * 导出模板
	 */
	@GetMapping( "/exportMaterial")
	public void exportMaterial(HttpServletResponse response){
		String fileName = "原材料导入模板.xls";
		try {
			response = ResponseUtil.getResponse(response,fileName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HSSFWorkbook wb =materialService.exportMaterial(getCompanyId());
		try(OutputStream os = response.getOutputStream()) {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 进入导入excel
	 */
	@GetMapping( "/materialImport")
	public String materialImport(MultipartFile file){
		return "material/material/materialImport";
	}

	/**
	 * 导入excel
	 */
	@ResponseBody
	@PostMapping( "/importMaterial")
	public Result importMaterial(MultipartFile file){
		if (!file.isEmpty()){
			materialService.importMaterial(file,getCompanyId(),getUserId());
		}
		return Result.ok();
	}
}
