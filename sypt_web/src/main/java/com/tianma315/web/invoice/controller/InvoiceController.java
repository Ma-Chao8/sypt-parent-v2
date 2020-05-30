package com.tianma315.web.invoice.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


import com.alibaba.fastjson.JSONObject;
import com.tianma315.core.invoice.domain.dto.InvoiceDto;
import com.tianma315.core.invoice.domain.vo.InvoiceVO;
import com.tianma315.core.base.Result;
import com.tianma315.core.invoice.vo.InvoiceDateVO;
import com.tianma315.core.utils.ResponseUtil;
import com.tianma315.web.base.BaseController;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.invoice.domain.InvoiceDO;
import com.tianma315.core.invoice.service.InvoiceService;

import javax.servlet.http.HttpServletResponse;


/**
 * 
 * <pre>
 * 货单
 * </pre>
 * <small> 2019-08-21 14:56:27 | Lgc</small>
 */
@Controller
@RequestMapping("/invoice/invoice")
public class InvoiceController extends BaseController {
	@Autowired
	private InvoiceService invoiceService;
	
	@GetMapping()
	@RequiresPermissions("invoice:invoice:invoice")
	String Invoice(){
	    return "invoice/invoice/invoice";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("invoice:invoice:invoice")
	public Result<Page<InvoiceVO>> list(Integer pageNumber, Integer pageSize, InvoiceDto invoiceDTO){
		//System.out.println("invoiceDTO============"+invoiceDTO);
		// 查询列表数据
		invoiceDTO.setCompanyId(getCompanyId());
		Page<InvoiceVO> page=invoiceService.selectIncoicelist(pageNumber,pageSize,invoiceDTO);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("invoice:invoice:add")
	String add(){
	    return "invoice/invoice/add";
	}

	//跳转详情页面
	@GetMapping("/details/{invoiceId}")
	@RequiresPermissions("invoice:invoice:edit")
	String details(@PathVariable("invoiceId") Long invoiceId,Model model){
		//InvoiceVO invoiceVo = invoiceService.getInvoiceDetails(invoiceId);
		model.addAttribute("invoiceId", invoiceId);
	    return "invoice/invoice/in_details";
	}

	@ResponseBody
	@GetMapping("/getDetails/{invoiceId}")
	Result<InvoiceVO> detail(@PathVariable("invoiceId") Long invoiceId,Model model){
		InvoiceVO invoiceVo = invoiceService.getInvoiceDetails(invoiceId);
		if (invoiceVo != null)
			return Result.ok(invoiceVo);
		return Result.fail();
	}

	/**
	 * 订单打印
	 * @param invoiceId
	 * @param model
	 * @return
	 */
	@GetMapping("/Printing/{invoiceId}")
	@RequiresPermissions("invoice:invoice:invoice")
	public String Printing(@PathVariable("invoiceId") Long invoiceId, Model model) {
		// 根据发货ID查询货单信息
//		OrderDetails orderDetails=ordersService.selectOrderDetails(orderId);
//		model.addAttribute("orderDetails", orderDetails);
		//		return "orders/orderPrinting";
		model.addAttribute("invoiceId", invoiceId);
		return "invoice/invoice/invoicePrinting";

	}

	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("invoice:invoice:add")
	public Result<String> save( InvoiceDO invoice){
		if(invoiceService.insert(invoice)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("invoice:invoice:edit")
	public Result<String>  update( InvoiceDO invoice){
		invoiceService.updateById(invoice);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("invoice:invoice:remove")
	public Result<String>  remove( Long invoiceId){
		if(invoiceService.deleteById(invoiceId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("invoice:invoice:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Long[] invoiceIds){
		invoiceService.deleteBatchIds(Arrays.asList(invoiceIds));
		return Result.ok();
	}

	/**
	 * 查找数据
	 */
	@PostMapping( "/count")
	@ResponseBody
	public Result<JSONObject> count(@RequestBody InvoiceDateVO invoiceDateVO){
		invoiceDateVO.setCompanyId(getCompanyId());
		JSONObject result = new JSONObject();
		//如果年份为空
		if (invoiceDateVO.getYear()==null || invoiceDateVO.getYear().equals("")){
			Calendar calendar = Calendar.getInstance();
			String year = String.valueOf(calendar.get(Calendar.YEAR));
			invoiceDateVO.setYear(year);
		}
		//月份不为空时说明用户是按月统计
		if (invoiceDateVO.getMonth()!=null && !invoiceDateVO.getMonth().equals("")){
			result = invoiceService.countByMonth(invoiceDateVO);
		}else{
			result = invoiceService.countByYear(invoiceDateVO);
		}
		return Result.ok(result);
	}

	/**
	 * 查找数据
	 */
	@PostMapping( "/countCode")
	@ResponseBody
	public Result<JSONObject> countCode(@RequestBody InvoiceDateVO invoiceDateVO){
		invoiceDateVO.setCompanyId(getCompanyId());
		JSONObject result = new JSONObject();
		//如果年份为空
		if (invoiceDateVO.getYear()==null || invoiceDateVO.getYear().equals("")){
			Calendar calendar = Calendar.getInstance();
			String year = String.valueOf(calendar.get(Calendar.YEAR));
			invoiceDateVO.setYear(year);
		}
		//月份不为空时说明用户是按月统计
		if (invoiceDateVO.getMonth()!=null && !invoiceDateVO.getMonth().equals("")){
			result = invoiceService.countCodeByMonth(invoiceDateVO);
		}else{
			result = invoiceService.countCodeByYear(invoiceDateVO);
		}
		return Result.ok(result);
	}

	/**
	 * 详情excel导出
	 *
	 * @param invoiceId  订单ID
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/invoiceInfoExecl/{invoiceId}")
	@RequiresPermissions("invoice:invoice:invoice")
	public void orderInfoExecl(@PathVariable("invoiceId") Long invoiceId, HttpServletResponse response) throws IOException {
		String fileName = "发货单详情导出.xls";
		try {
			response = ResponseUtil.getResponse(response,fileName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HSSFWorkbook wb =invoiceService.exportInvoice(invoiceId);
		try(OutputStream os = response.getOutputStream()) {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
