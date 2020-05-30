package com.tianma315.web.material.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

import com.tianma315.core.material.dto.PurchaseDto;
import com.tianma315.core.material.vo.PurchaseFileVO;
import com.tianma315.core.material.vo.PurchaseVO;
import com.tianma315.core.sys.domain.FileDO;
import com.tianma315.core.sys.service.FileService;
import com.tianma315.core.utils.ResponseUtil;
import com.tianma315.core.base.Result;
import com.tianma315.web.base.BaseController;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
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
import com.tianma315.core.material.domain.PurchaseDO;
import com.tianma315.core.material.service.PurchaseService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * <pre>
 * 原材料进库表
 * </pre>
 * <small> 2019-06-24 11:23:31 | Wen</small>
 */
@Controller
@RequestMapping("/material/purchase")
public class PurchaseController extends BaseController{
	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private FileService fileService;
	
	@GetMapping()
	@RequiresPermissions("material:purchase:purchase")
	String Purchase(){
	    return "material/purchase/purchase";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("material:purchase:purchase")
	public Result<Page<PurchaseVO>> list(Integer pageNumber, Integer pageSize, PurchaseDto purchaseDTO){
		if (getCompanyId() != null) purchaseDTO.setCompanyId(getCompanyId().intValue());
		// 查询列表数据
        Page<PurchaseVO> page = purchaseService.getPurchaseVOPage(pageNumber,pageSize,purchaseDTO);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("material:purchase:add")
	String add(){
	    return "material/purchase/add";
	}

	@GetMapping("/edit/{purchaseId}")
	@RequiresPermissions("material:purchase:edit")
	String edit(@PathVariable("purchaseId") Integer purchaseId,Model model){
		PurchaseDO purchase = purchaseService.selectById(purchaseId);
		model.addAttribute("purchase", purchase);
	    return "material/purchase/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("material:purchase:add")
	public Result<String> save( PurchaseFileVO fileVO){
		MultipartFile imageFile = fileVO.getFile();
		try {
			FileDO fileDO = fileService.upload(getUserId(),imageFile.getBytes(),imageFile.getOriginalFilename());
			PurchaseDO purchase = new PurchaseDO();
			BeanUtils.copyProperties(fileVO,purchase);
			purchase.setReportImg(fileDO.getUrl());
			purchase.setCreateBy(getUserId());
			purchase.setCreateDate(Calendar.getInstance().getTime());
			purchase.setCompanyId(getCompanyId().intValue());
			if(purchaseService.addPurchase(purchase)){
				return Result.ok();
			}

		} catch (IOException e) {
			e.printStackTrace();
			return  Result.build(205,"文件上传失败");
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("material:purchase:edit")
	public Result<String>  update( PurchaseFileVO fileVO){
		MultipartFile imageFile = fileVO.getFile();
		FileDO fileDO = new FileDO();
		PurchaseDO purchase = new PurchaseDO();
		BeanUtils.copyProperties(fileVO,purchase);
		try {
			if (imageFile!= null){
				fileDO = fileService.upload(getUserId(),imageFile.getBytes(),imageFile.getOriginalFilename());
				purchase.setReportImg(fileDO.getUrl());
			}
			purchase.setCreateBy(getUserId());
			purchase.setCreateDate(Calendar.getInstance().getTime());
			if(purchaseService.updateById(purchase)){
				return Result.ok();
			}

		} catch (IOException e) {
			e.printStackTrace();
			return  Result.build(205,"文件上传失败");
		}
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("material:purchase:remove")
	public Result<String>  remove( Integer purchaseId){
		if(purchaseService.deletePurchase(purchaseId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("material:purchase:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] purchaseIds){
//		purchaseService.deleteBatchIds(Arrays.asList(purchaseIds));
		for (Integer id:purchaseIds) {
			purchaseService.deletePurchase(id);
		}
		return Result.ok();
	}

	/**
	 * 导出模板
	 */
	@GetMapping( "/exportPurchase")
	public void exportPurchase(HttpServletResponse response){
		String fileName = "原材料记录导入模板.xls";
		try {
			response = ResponseUtil.getResponse(response,fileName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HSSFWorkbook wb =purchaseService.exportPurchase(getCompanyId());
		try(OutputStream os = response.getOutputStream()) {
			wb.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 进入导入excel
	 */
	@GetMapping( "/purchaseImport")
	public String purchaseImport(MultipartFile file){
		return "material/purchase/purchaseImport";
	}

	/**
	 * 导入excel
	 */
	@ResponseBody
	@PostMapping( "/importPurchase")
	public Result importPurchase(MultipartFile file){
		if (!file.isEmpty()){
			purchaseService.importPurchase(file,getCompanyId(),getUserId());
		}
		return Result.ok();
	}

	@ResponseBody
	@GetMapping("/all")
	public Result<List<PurchaseDO>> all(){
		// 查询列表数据
		List<PurchaseDO> purchaseDOList = purchaseService.getPurchaseDOList(getCompanyId());
		return Result.ok(purchaseDOList);
	}

}
