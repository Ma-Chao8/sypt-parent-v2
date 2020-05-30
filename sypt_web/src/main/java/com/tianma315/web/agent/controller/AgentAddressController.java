package com.tianma315.web.agent.controller;


import com.tianma315.core.agent.domain.AgentAddressDO;
import com.tianma315.core.agent.service.AgentAddressService;
import com.tianma315.core.agent.vo.AgentAddressVO;
import com.tianma315.core.base.Result;
import com.tianma315.web.base.BaseController;
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

/**
 * 
 * <pre>
 * 经销商地址
 * </pre>
 * <small> 2019-07-26 10:05:37 | Wen</small>
 */
@Controller
@RequestMapping("/agent/agentAddress")
public class AgentAddressController extends BaseController{
	@Autowired
	private AgentAddressService agentAddressService;
	
	@GetMapping()
//	@RequiresPermissions("agent:agentAddress:agentAddress")
	String AgentAddress(){
	    return "agent/agentAddress/agentAddress";
	}
	
	@ResponseBody
	@GetMapping("/list")
//	@RequiresPermissions("agent:agentAddress:agentAddress")
	public Result<Page<AgentAddressVO>> list(Integer pageNumber, Integer pageSize, AgentAddressDO agentAddressDTO){
		// 查询列表数据
		agentAddressDTO.setCompanyId(getCompanyId());
        Page<AgentAddressVO> page = agentAddressService.getAgentAddressVOPage(pageNumber,pageSize,agentAddressDTO);
        return Result.ok(page);
	}
	
	@GetMapping("/add/{agentId}")
//	@RequiresPermissions("agent:agentAddress:add")
	String add(@PathVariable("agentId")Long agentId,Model model){
		model.addAttribute("agentId",agentId);
	    return "agent/agentAddress/add";
	}

	@GetMapping("/edit/{addressId}")
//	@RequiresPermissions("agent:agentAddress:edit")
	String edit(@PathVariable("addressId") Long addressId,Model model){
		AgentAddressDO agentAddress = agentAddressService.selectById(addressId);
		model.addAttribute("agentAddress", agentAddress);
	    return "agent/agentAddress/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
//	@RequiresPermissions("agent:agentAddress:add")
	public Result<String> save( AgentAddressDO agentAddress){
		agentAddress.setCompanyId(getCompanyId());
		if(agentAddressService.addAgentAddressDO(agentAddress)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
//	@RequiresPermissions("agent:agentAddress:edit")
	public Result<String>  update( AgentAddressDO agentAddress){
		agentAddressService.updateById(agentAddress);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
//	@RequiresPermissions("agent:agentAddress:remove")
	public Result<String>  remove( Long addressId,Integer status){
		if(agentAddressService.deleteAgentAddressDO(addressId,status)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
//	@RequiresPermissions("agent:agentAddress:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Long[] addressIds,@RequestParam("status[]")Integer[] status){
//		agentAddressService.deleteBatchIds(Arrays.asList(addressIds));
		for (int i=0;i< addressIds.length;i++) {
			agentAddressService.deleteAgentAddressDO(addressIds[i],status[i]);
		}
		return Result.ok();
	}

	@GetMapping("/agentAddress/{agentId}")
	String AgentAddress(@PathVariable("agentId") Integer agentId, Model model){
		model.addAttribute("agentId",agentId);
		return "agent/agentAddress/agentAddress";
	}

	/**
	 * 删除
	 */
	@PostMapping( "/status")
	@ResponseBody
	public Result<String>  status( Long addressId,Long agentId){
		if(agentAddressService.updateStatus(addressId,agentId)){
			return Result.ok();
		}
		return Result.fail();
	}
	
}
