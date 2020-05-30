package com.tianma315.web.wxuserinfo.controller;


import java.util.Arrays;

import com.tianma315.core.base.Result;
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
import com.tianma315.core.wxuserinfo.domain.WxUserInfoDO;
import com.tianma315.core.wxuserinfo.service.WxUserInfoService;

/**
 * 
 * <pre>
 * 微信用户数据
 * </pre>
 * <small> 2019-06-27 14:29:48 | Wen</small>
 */
@Controller
@RequestMapping("/wxuserinfo/wxUserInfo")
public class WxUserInfoController {
	@Autowired
	private WxUserInfoService wxUserInfoService;
	
	@GetMapping()
	@RequiresPermissions("wxuserinfo:wxUserInfo:wxUserInfo")
	String WxUserInfo(){
	    return "wxuserinfo/wxUserInfo/wxUserInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("wxuserinfo:wxUserInfo:wxUserInfo")
	public Result<Page<WxUserInfoDO>> list(Integer pageNumber, Integer pageSize, WxUserInfoDO wxUserInfoDTO){
		// 查询列表数据
        Page<WxUserInfoDO> page = new Page<>(pageNumber, pageSize);

        Wrapper<WxUserInfoDO> wrapper = new EntityWrapper<WxUserInfoDO>(wxUserInfoDTO);
        page = wxUserInfoService.selectPage(page, wrapper);
        int total = wxUserInfoService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("wxuserinfo:wxUserInfo:add")
	String add(){
	    return "wxuserinfo/wxUserInfo/add";
	}

	@GetMapping("/edit/{openId}")
	@RequiresPermissions("wxuserinfo:wxUserInfo:edit")
	String edit(@PathVariable("openId") String openId,Model model){
		WxUserInfoDO wxUserInfo = wxUserInfoService.selectById(openId);
		model.addAttribute("wxUserInfo", wxUserInfo);
	    return "wxuserinfo/wxUserInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("wxuserinfo:wxUserInfo:add")
	public Result<String> save( WxUserInfoDO wxUserInfo){
		if(wxUserInfoService.insert(wxUserInfo)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("wxuserinfo:wxUserInfo:edit")
	public Result<String>  update( WxUserInfoDO wxUserInfo){
		wxUserInfoService.updateById(wxUserInfo);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("wxuserinfo:wxUserInfo:remove")
	public Result<String>  remove( String openId){
		if(wxUserInfoService.deleteById(openId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("wxuserinfo:wxUserInfo:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") String[] openIds){
		wxUserInfoService.deleteBatchIds(Arrays.asList(openIds));
		return Result.ok();
	}
	
}
