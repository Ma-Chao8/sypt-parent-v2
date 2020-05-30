package com.tianma315.web.report.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.report.domain.ReportDO;
import com.tianma315.core.report.domain.ReportImgDO;
import com.tianma315.core.report.dto.ReportDto;
import com.tianma315.core.report.service.ReportImgService;
import com.tianma315.core.report.service.ReportService;
import com.tianma315.core.report.vo.ReportVO;
import com.tianma315.core.base.Result;
import com.tianma315.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * 
 * <pre>
 * 检测报告
 * </pre>
 * <small> 2019-06-25 13:58:06 | Wen</small>
 */
@Controller
@RequestMapping("/report/report")
public class ReportController extends BaseController{
	@Autowired
	private ReportService reportService;

	@Autowired
	private ReportImgService reportImgService;

	@GetMapping("/panel")
	@RequiresPermissions("report:report:panel")
	String panel(){return "report/report/panel";}

	@GetMapping()
	@RequiresPermissions("report:report:report")
	String Report(){
	    return "report/report/report";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("report:report:report")
	public Result<Page<ReportVO>> list(Integer pageNumber, Integer pageSize, ReportDto reportDTO,
									   @RequestParam Date beginDate,@RequestParam Date endDate){
		// 查询列表数据
		Long companyId = getCompanyId();
		if (companyId != null){
			reportDTO.setCompanyId(companyId.intValue());
		}

        Page<ReportVO> page = reportService.getReportDOPage(pageNumber,pageSize,reportDTO,beginDate,endDate);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("report:report:add")
	String add(){
	    return "report/report/add";
	}

	@GetMapping("/edit/{reportId}")
	@RequiresPermissions("report:report:edit")
	String edit(@PathVariable("reportId") Integer reportId,Model model){
		ReportDO report = reportService.selectById(reportId);
		model.addAttribute("report", report);
	    return "report/report/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("report:report:add")
	public Result<String> save( ReportDO report){
		report.setCompanyId(getCompanyId().intValue());
		report.setCreateBy(getUserId());
		report.setCreateDate(new Date());
		if(reportService.addReportDO(report)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("report:report:edit")
	public Result<String>  update( ReportDO report){
		ReportDO reportDO = reportService.selectById(report.getReportId());
		report.setCompanyId(reportDO.getCompanyId());
		report.setCreateBy(reportDO.getCreateBy());
		report.setCreateDate(reportDO.getCreateDate());
		report.setState(StateEnum.not_del.getIndex());
		reportService.updateAllColumnById(report);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("report:report:remove")
	public Result<String>  remove( Integer reportId){
		if(reportService.deleteReportDO(reportId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("report:report:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] reportIds){
		for (Integer id:reportIds) {
			reportService.deleteReportDO(id);
		}
		return Result.ok();
	}

	@GetMapping("/to_addReportImg/{reportId}")
	String toAddReportImg(@PathVariable("reportId")Integer reportId,Model model){
		model.addAttribute("id",reportId);
		return "report/report/uploadview";
	}

	@PostMapping("/addReportImg/{reportId}")
	@ResponseBody
	Result addReportImg(HttpServletRequest request, ReportImgDO reportImgDO){
		reportImgService.insertImg(request,reportImgDO,getUserId());
		return Result.ok();
	}

	@GetMapping("/deleteImg/{reportImgId}")
	@ResponseBody
	Result deleteImg(@PathVariable("reportImgId")Integer reportImgId){
		Boolean result= reportImgService.deleteReportImg(reportImgId);
		if (result){
			return Result.ok();
		}
		return Result.fail();
	}

	@GetMapping("/to_updateReportImg/{reportId}")
	String toUpdateReportImg(@PathVariable("reportId")Integer reportId,Model model){
		List<ReportImgDO> list = reportImgService.getReportImgByReportId(reportId);
		model.addAttribute("list",list);
		model.addAttribute("id",reportId);
		return "report/report/updateimg";
	}

	@ResponseBody
	@GetMapping("/all")
	public Result<List<ReportDO>> all(){
		// 查询列表数据
		Long companyId = getCompanyId();
		List<ReportDO> reportDOList = reportService.getReportDOList(companyId);
		return Result.ok(reportDOList);
	}
}
