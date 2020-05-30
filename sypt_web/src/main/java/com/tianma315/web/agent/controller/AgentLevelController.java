package com.tianma315.web.agent.controller;


import java.util.List;

import com.tianma315.core.agent.domain.AgentLevelDO;
import com.tianma315.core.agent.service.AgentLevelService;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.base.Result;
import com.tianma315.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * 
 * <pre>
 * 经销商等级
 * </pre>
 * <small> 2019-07-26 10:05:37 | Wen</small>
 */
@Controller
@RequestMapping("/agent/agentLevel")
public class AgentLevelController extends BaseController{
	@Autowired
	private AgentLevelService agentLevelService;
	
	@GetMapping()
	@RequiresPermissions("agent:agentLevel:agentLevel")
	String AgentLevel(){
	    return "agent/agentLevel/agentLevel";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("agent:agentLevel:agentLevel")
	public Result<Page<AgentLevelDO>> list(Integer pageNumber, Integer pageSize, AgentLevelDO agentLevelDTO){
		agentLevelDTO.setCompanyId(getCompanyId());
		agentLevelDTO.setCreateBy(getUserId());
		// 查询列表数据
        Page<AgentLevelDO> page = agentLevelService.agentLevelDOPage(pageNumber,pageSize,agentLevelDTO);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("agent:agentLevel:add")
	String add(){
	    return "agent/agentLevel/add";
	}

	@GetMapping("/edit/{levelId}")
	@RequiresPermissions("agent:agentLevel:edit")
	String edit(@PathVariable("levelId") Long levelId,Model model){
		AgentLevelDO agentLevel = agentLevelService.selectById(levelId);
		model.addAttribute("agentLevel", agentLevel);
	    return "agent/agentLevel/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("agent:agentLevel:add")
	public Result<String> save( AgentLevelDO agentLevel){
		agentLevel.setCompanyId(getCompanyId());
		agentLevel.setCreateBy(getUserId());
		Boolean checkResult = agentLevelService.checkRepeatAgentLevel(agentLevel);
		if (!checkResult){
			if(agentLevelService.addAgentLevelDO(agentLevel)){
				return Result.ok();
			}
		}else{
			throw new MessageException("等级排序不能重复");
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("agent:agentLevel:edit")
	public Result<String>  update( AgentLevelDO agentLevel){
//		AgentLevelDO agentLevelDO = agentLevelService.selectById(agentLevel.getLevelId());
//		agentLevel.setCompanyId(agentLevelDO.getCompanyId());
		Boolean checkResult = agentLevelService.checkRepeatAgentLevel(agentLevel);
		if (!checkResult){
			agentLevelService.updateById(agentLevel);
		}else{
			throw new MessageException("等级排序不能重复");
		}

		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("agent:agentLevel:remove")
	public Result<String>  remove( Long levelId){
		if(agentLevelService.deleteById(levelId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
//	/**
//	 * 删除
//	 */
//	@PostMapping( "/batchRemove")
//	@ResponseBody
//	@RequiresPermissions("agent:agentLevel:batchRemove")
//	public Result<String>  remove(@RequestParam("ids[]") Long[] levelIds){
////		agentLevelService.deleteBatchIds(Arrays.asList(levelIds));
//		for (Long id:levelIds) {
//			agentLevelService.deleteAgentLevel(id);
//		}
//		return Result.ok();
//	}

	/**
	 * 修改状态
	 */
	@PostMapping( "/status")
	@ResponseBody
	public Result<String>  status( Long levelId,Integer status){
		if(agentLevelService.deleteAgentLevel(levelId,status)){
			return Result.ok();
		}
		return Result.fail();
	}

//	/**
//	 * 批量修改状态
//	 */
//	@PostMapping( "/batchRemove")
//	@ResponseBody
//	public Result<String>  status(@RequestParam("ids[]") Long[] levelIds){
////		agentLevelService.deleteBatchIds(Arrays.asList(levelIds));
//		for (Long id:levelIds) {
//			agentLevelService.deleteAgentLevel(id);
//		}
//		return Result.ok();
//	}

	@GetMapping( "/agentLevelDOList")
	@ResponseBody
	public Result<List<AgentLevelDO>> agentLevelDOList(){
		List<AgentLevelDO> agentLevelDOS = agentLevelService.agentLevelDOList(getCompanyId());
		return Result.ok(agentLevelDOS);
	}
	
}
