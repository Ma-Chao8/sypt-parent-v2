package com.tianma315.web.small_code.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tianma315.core.small_code.domain.SmallRecordDO;
import com.tianma315.core.small_code.domain.dto.SmallRecordDto;
import com.tianma315.core.small_code.service.SmallRecordService;
import com.tianma315.core.small_code.vo.SmallRecordListVO;
import com.tianma315.core.small_code.vo.SmallRecordVO;
import com.tianma315.core.base.Result;
import com.tianma315.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * 
 * <pre>
 * 外包装管理记录
 * </pre>
 * <small> 2019-08-10 10:06:48 | Wen</small>
 */
@Controller
@RequestMapping("/small_code/smallRecord")
public class SmallRecordController extends BaseController{
	@Autowired
	private SmallRecordService smallRecordService;
	
	@GetMapping()
	@RequiresPermissions("small_code:smallRecord:smallRecord")
	String SmallRecord(){
	    return "small_code/smallRecord/smallRecord";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("small_code:smallRecord:smallRecord")
	public Result<Page<SmallRecordVO>> list(Integer pageNumber, Integer pageSize, SmallRecordDto smallRecordDTO){
		// 查询列表数据
//        Page<SmallRecordDO> page = new Page<>(pageNumber, pageSize);
//
//        Wrapper<SmallRecordDO> wrapper = new EntityWrapper<SmallRecordDO>();
//        wrapper.eq("company_id",getCompanyId());
//        wrapper.like("big_code",smallRecordDTO.getBigCode());
//        wrapper.groupBy("big_code");
//        page = smallRecordService.selectPage(page, wrapper);
//        int total = smallRecordService.selectCount(wrapper);
//        page.setTotal(total);
		smallRecordDTO.setCompanyId(getCompanyId());
		Page<SmallRecordVO> smallRecordVDOPage = smallRecordService.getRecordVOPage(pageNumber,pageSize,smallRecordDTO);

		List<SmallRecordVO> smallRecordVOList = smallRecordVDOPage.getRecords();
		List<SmallRecordVO> smallRecordVOS = new ArrayList<>();
		for (SmallRecordVO smallRecordVO:smallRecordVOList) {
			SmallRecordListVO smallRecordListVO =smallRecordService.getSmallRecordListVOByBigCode(smallRecordVO.getBigCode(),getCompanyId());
			smallRecordVO.setSmallCodes(smallRecordListVO.getSmallCodes());
			smallRecordVOS.add(smallRecordVO);
		}

		smallRecordVDOPage.setRecords(smallRecordVOS);
        return Result.ok(smallRecordVDOPage);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("small_code:smallRecord:add")
	String add(){
	    return "small_code/smallRecord/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("small_code:smallRecord:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		SmallRecordDO smallRecord = smallRecordService.selectById(id);
		model.addAttribute("smallRecord", smallRecord);
	    return "small_code/smallRecord/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("small_code:smallRecord:add")
	public Result<String> save( SmallRecordDO smallRecord){
		if(smallRecordService.insert(smallRecord)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("small_code:smallRecord:edit")
	public Result<String>  update( SmallRecordDO smallRecord){
		smallRecordService.updateById(smallRecord);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("small_code:smallRecord:remove")
	public Result<String>  remove( String bigCode){
		EntityWrapper wrapper = new EntityWrapper();
		wrapper.eq("big_code",bigCode);
		wrapper.eq("company_id",getCompanyId());

		if(smallRecordService.delete(wrapper)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("small_code:smallRecord:batchRemove")
	public Result<String>  remove(@RequestParam("bigCodes[]") String[] bigCodes){
//		smallRecordService.deleteBatchIds(Arrays.asList(ids));
		for (String bigCode:bigCodes){
			remove(bigCode);
		}
		return Result.ok();
	}

	@GetMapping("/product")
//	@RequiresPermissions("small_code:smallRecord:smallRecord")
	String smallBox(){
		return "small_code/smallRecord/product";
	}

	@GetMapping("/bindProductInStock")
	String bindProductInStock(){
		return "small_code/smallRecord/bindProductInStock";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/saveRecord")
	public Result<String> saveRecord(@RequestBody SmallRecordVO smallRecord){
		smallRecord.setCompanyId(getCompanyId());
		smallRecord.setCreateBy(getUserId());
		if(smallRecordService.addSmallRecord(smallRecord)){
			return Result.ok();
		}
		return Result.fail();
	}

	@GetMapping("/getSmallRecordListVOBySmallCode")
	@ResponseBody
	public Result<SmallRecordListVO> getSmallRecordListVOBySmallCode(String smallCode){
		SmallRecordListVO smallRecordListVO = smallRecordService.getSmallRecordListVOBySmallCode(smallCode,getCompanyId());
		return Result.ok(smallRecordListVO);
	}

	@GetMapping("/getSmallRecordListVOByBigCode")
	@ResponseBody
	public Result<SmallRecordListVO> getSmallRecordListVOByBigCode(String bigCode){
		SmallRecordListVO smallRecordListVO = smallRecordService.getSmallRecordListVOByBigCode(bigCode,getCompanyId());
		return Result.ok(smallRecordListVO);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/saveRecordAndProduct")
	public Result<String> saveRecordAndProduct(@RequestBody SmallRecordVO smallRecord){
		smallRecord.setCompanyId(getCompanyId());
		smallRecord.setCreateBy(getUserId());
		if(smallRecordService.addSmallRecordAndStock(smallRecord)){
			return Result.ok();
		}
		return Result.fail();
	}
	
}
