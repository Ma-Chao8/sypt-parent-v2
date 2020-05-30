package com.tianma315.web.trace.controller;


import java.util.*;


import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.base.Result;
import com.tianma315.core.procedures.domain.LinkContentDO;
import com.tianma315.core.procedures.domain.ProcedureLinkDO;
import com.tianma315.core.procedures.service.LinkContentService;
import com.tianma315.core.procedures.service.ProcedureLinkService;

import com.tianma315.core.trace.domain.TraceOutDO;
import com.tianma315.core.trace.service.TraceOutService;

import com.tianma315.core.trace.vo.CodeVo;
import com.tianma315.core.trace.vo.TraceOutAndFieldVO;
import com.tianma315.core.trace.vo.TraceOutVO;
import com.tianma315.core.traceability.domain.TyMenuDO;
import com.tianma315.core.traceability.service.TraceabilityFileService;

import com.tianma315.core.traceability.service.TyMenuDataService;
import com.tianma315.core.traceability.service.TyMenuService;
import com.tianma315.core.traceability.vo.TyMenuDataVO;


import com.tianma315.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;


/**
 * 
 * <pre>
 * 溯源批次出库记录
 * </pre>
 * <small> 2019-08-08 15:02:49 | Wb</small>
 */
@Controller
@RequestMapping("/trace/traceOut")
public class TraceOutController extends BaseController {
	@Autowired
	private TraceOutService traceOutService;

	@Autowired
	private LinkContentService linkContentService;

	@Autowired
	private TraceabilityFileService traceabilityFileService;

	@Autowired
	private TyMenuService tyMenuService;

	@Autowired
	private ProcedureLinkService procedureLinkService;

	@Autowired
	private TyMenuDataService tyMenuDataService;
	
	@GetMapping()
	@RequiresPermissions("trace:traceOut:traceOut")
	String TraceOut(){
	    return "trace/traceOut/traceOut";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("trace:traceOut:traceOut")
	public Result<Page<TraceOutVO>> list(Integer pageNumber, Integer pageSize, TraceOutDO traceOutDTO,
										 @RequestParam Date beginDate, @RequestParam Date endDate){
//		traceOutDTO.setMerchantId(getMerchantId());
		// 查询列表数据
        Page<TraceOutVO> page = traceOutService.getTraceOutPage(pageNumber,pageSize,traceOutDTO,beginDate,endDate);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("trace:traceOut:add")
	String add(){
	    return "trace/traceOut/add";
	}

	@GetMapping("/edit/{traceOutId}")
	@RequiresPermissions("trace:traceOut:edit")
	String edit(@PathVariable("traceOutId") Integer traceOutId,Model model){
		TraceOutAndFieldVO traceOut = traceOutService.getTraceOutById(traceOutId);
		model.addAttribute("traceOut", traceOut);
	    return "trace/traceOut/edit";
	}

	@GetMapping("/toOutContent/{productId}/{traceabilityFileId}")
//	@RequiresPermissions("trace:traceOut:traceOut")
	String toOutContent(@PathVariable("productId") Integer productId,@PathVariable("traceabilityFileId") Integer traceabilityFileId,Model model){
		List<ProcedureLinkDO> contentList=traceOutService.selectContent(getUserId(),productId,traceabilityFileId);

		model.addAttribute("contentList", contentList);
		model.addAttribute("traceabilityFileId", traceabilityFileId);
		return "trace/traceOut/toOutContent";
	}

	@GetMapping("/linkContent/{id}/{traceabilityFileId}")
//	@RequiresPermissions("trace:traceOut:traceOut")
	String linkContent(@PathVariable("id")Integer id,@PathVariable("traceabilityFileId")Integer traceabilityFileId,Model model){
		Wrapper<LinkContentDO>  wrapper = new EntityWrapper<LinkContentDO>();
		wrapper.eq("procedures_link_id",id);
		wrapper.eq("state", StateEnum.not_del);
		wrapper.orderBy("sort");
		List<LinkContentDO> linkContentDOS = linkContentService.selectList(wrapper);
		//
		for (LinkContentDO linkContentDO : linkContentDOS) {
			if (linkContentDO.getType()==1){
				String defaultValue = linkContentDO.getDefaultValue();
				if (!defaultValue.isEmpty()){
					String[] split = defaultValue.split(",");
					linkContentDO.setPics(Arrays.asList(split));
				}
			}
		}
		model.addAttribute("likeContentAll",linkContentDOS);
		model.addAttribute("traceabilityFileId",traceabilityFileId);
		model.addAttribute("proceduresLinkId",id);

		return "trace/traceOut/toOutContentLike";
	}
	@GetMapping("/updateLinkContent/{id}/{traceabilityFileId}")
//	@RequiresPermissions("trace:traceOut:traceOut")
	String updateLinkContent(@PathVariable("id")Integer id,@PathVariable("traceabilityFileId")Integer traceabilityFileId,Model model){

		List<TyMenuDataVO> tyMenuDataDOS = traceOutService.getTyMenuDataDOS(id,traceabilityFileId);
		
		model.addAttribute("tyMenuDataDOS",tyMenuDataDOS);
		model.addAttribute("traceabilityFileId",traceabilityFileId);
		model.addAttribute("proceduresLinkId",id);

		return "trace/traceOut/updateContentLike";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("trace:traceOut:add")
	public Result<String> save( TraceOutAndFieldVO traceOut){
		traceOut.setCreateBy(getUserId().toString());
		traceOut.setCompanyId(getCompanyId());
		if(traceOutService.addTraceOut(traceOut)){
				return Result.ok();
		}
		return Result.fail();
	}



	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("trace:traceOut:edit")
	public Result<String>  update( TraceOutAndFieldVO traceOut){
		traceOutService.updateTraceOut(traceOut);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("trace:traceOut:remove")
	public Result<String>  remove( Integer traceOutId){
		if(traceOutService.deleteTraceOut(traceOutId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("trace:traceOut:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] traceOutIds){
//		traceOutService.deleteBatchIds(Arrays.asList(traceOutIds));
		for (Integer id : traceOutIds){
			traceOutService.deleteTraceOut(id);
		}
		return Result.ok();
	}

	/**
	 * 检测开始序号是否重复
	 */
	@GetMapping( "/getCheckBeginSerialResult")
	@ResponseBody
	public Boolean  getCheckBeginSerialResult(@RequestParam("startSerial") Integer startSerial){
		Boolean flag = traceOutService.checkSerial(startSerial);
		return flag;
	}

	/**
	 * 检测结束序号是否重复
	 */
	@GetMapping( "/getCheckEndSerialResult")
	@ResponseBody
	public Boolean  getCheckEndSerialResult(@RequestParam("endSerial") Integer endSerial){
		return traceOutService.checkSerial(endSerial);
	}



	@RequestMapping("/codeUpdate")
	@ResponseBody
	@Transactional
	public Result<String> codeUpdate(@RequestBody CodeVo codeVo){
		log.info("codeVo:{}",codeVo);
		try {
			ProcedureLinkDO procedureLinkDO = procedureLinkService.selectById(codeVo.getProceduresLinkId());
			TyMenuDO tyMenuDO = new TyMenuDO();
			tyMenuDO.setCreateBy(getUserId());
			tyMenuDO.setCreateDate(Calendar.getInstance().getTime());
			tyMenuDO.setMenuName(procedureLinkDO.getProcedureLinkName());
			tyMenuDO.setMenuLevel(0);
			tyMenuDO.setParentMenuId(0);
			tyMenuDO.setTraceabilityFileId(codeVo.getTraceabilityFileId());
			tyMenuDO.setProcedureLinkId(codeVo.getProceduresLinkId());
			tyMenuDO.setIcon(procedureLinkDO.getIcon());
			tyMenuService.insert(tyMenuDO);

			if (tyMenuDataService.insertList(codeVo,tyMenuDO.getMenuId(),getUserId()))
				return Result.ok();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("codeUpdate ：{} error:{}",e.getMessage(),e);
		}
		return Result.fail();
	}

	@RequestMapping("/updateData")
	@ResponseBody
	@Transactional
	public Result<String> updateData(@RequestBody CodeVo codeVo){
		log.info("codeVo:{}",codeVo);
//		(codeVo,tyMenuDO.getMenuId(),getUserId()))
		try {
			            tyMenuDataService.updateDataById(codeVo);
			            tyMenuDataService.updateUrlById(codeVo);
			            return Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("codeUpdate ：{} error:{}",e.getMessage(),e);
		}
		return Result.fail();
	}
}
