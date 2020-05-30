//package com.tianma315.web.traceability.controller;
//
//
//import java.util.Arrays;
//import java.util.List;
//
//import com.tianma315.core.utils.Result;
//import com.tianma315.web.base.BaseController;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.baomidou.mybatisplus.mapper.EntityWrapper;
//import com.baomidou.mybatisplus.mapper.Wrapper;
//import com.baomidou.mybatisplus.plugins.Page;
//import com.tianma315.core.traceability.domain.ReportDO;
//import com.tianma315.core.traceability.service.ReportService;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// *
// * <pre>
// * 检测报告
// * </pre>
// * <small> 2019-06-20 13:49:53 | Wen</small>
// */
//@Controller
////@RequestMapping("/traceability/report")
//public class ReportController extends BaseController{
//	@Autowired
//	private ReportService reportService;
//
//	@GetMapping()
//	@RequiresPermissions("traceability:report:report")
//	String Report(){
//	    return "traceability/report/report";
//	}
//
//	@ResponseBody
//	@GetMapping("/list")
//	@RequiresPermissions("traceability:report:report")
//	public Result<Page<ReportDO>> list(Integer pageNumber, Integer pageSize, ReportDO reportDTO){
//		// 查询列表数据
//        Page<ReportDO> page = new Page<>(pageNumber, pageSize);
//
//        Wrapper<ReportDO> wrapper = new EntityWrapper<ReportDO>(reportDTO);
//
//        page = reportService.selectPage(page, wrapper);
//        int total = reportService.selectCount(wrapper);
//        page.setTotal(total);
//        return Result.ok(page);
//	}
//
//	@GetMapping("/add")
//	@RequiresPermissions("traceability:report:add")
//	String add(){
//	    return "traceability/report/add";
//	}
//
//	@GetMapping("/edit/{reportId}")
//	@RequiresPermissions("traceability:report:edit")
//	String edit(@PathVariable("reportId") Integer reportId,Model model){
//		ReportDO report = reportService.selectById(reportId);
//		model.addAttribute("report", report);
//	    return "traceability/report/edit";
//	}
//
//	/**
//	 * 保存
//	 */
//	@ResponseBody
//	@PostMapping("/save")
//	@RequiresPermissions("traceability:report:add")
//	public Result<String> save( ReportDO report){
//		if(reportService.insert(report)){
//			return Result.ok();
//		}
//		return Result.fail();
//	}
//	/**
//	 * 修改
//	 */
//	@ResponseBody
//	@RequestMapping("/update")
//	@RequiresPermissions("traceability:report:edit")
//	public Result<String>  update( ReportDO report){
//		reportService.updateById(report);
//		return Result.ok();
//	}
//
//	/**
//	 * 删除
//	 */
//	@PostMapping( "/remove")
//	@ResponseBody
//	@RequiresPermissions("traceability:report:remove")
//	public Result<String>  remove( Integer reportId){
//		if(reportService.deleteById(reportId)){
//            return Result.ok();
//		}
//		return Result.fail();
//	}
//
//	/**
//	 * 删除
//	 */
//	@PostMapping( "/batchRemove")
//	@ResponseBody
//	@RequiresPermissions("traceability:report:batchRemove")
//	public Result<String>  remove(@RequestParam("ids[]") Integer[] reportIds){
//		reportService.deleteBatchIds(Arrays.asList(reportIds));
//		return Result.ok();
//	}
//
//
//	@GetMapping("toBindReport/{id}")
//	public String toBindReport(@PathVariable("id") Integer id,Model model){
//		model.addAttribute("id",id);
//		return "traceability/report/bind_report";
//	}
//
//	@GetMapping("/toReport/{traceabilityId}")
//	String Report(@PathVariable("traceabilityId")Integer traceabilityId,Model model){
//		model.addAttribute("traceabilityId",traceabilityId);
//		return "traceability/report/report";
//	}
//
//	/**
//	 * 绑定报告
//	 * @return
//	 */
//	@PostMapping("/bindReport/{id}")
//	@ResponseBody
//	Result bindReport(HttpServletRequest request, @PathVariable("id") Integer id) {
//		ReportDO report = new ReportDO();
//		report.setCreateBy(getUserId());
//		report.setTraceabilityFileId(id);
//		try {
//			reportService.uploadPictures(request,report);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return Result.fail();
//		}
//		return Result.ok();
//	}
//
//	/**
//	 * 查看报告
//	 * @return
//	 */
//	@GetMapping("/checkReport/{id}")
//	@ResponseBody
//	Result<Page<ReportDO>> checkReport(@PathVariable("id") Integer id,Integer pageNumber, Integer pageSize, ReportDO reportDTO) {
//		Page<ReportDO> page = new Page(pageNumber,pageSize);
//		page = reportService.getReportDOList(page,id);
//		return Result.ok(page);
//	}
//}
