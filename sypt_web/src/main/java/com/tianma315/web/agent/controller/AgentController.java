package com.tianma315.web.agent.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import com.tianma315.core.agent.domain.AgentDO;
import com.tianma315.core.agent.dto.AgentDto;
import com.tianma315.core.agent.service.AgentService;
import com.tianma315.core.agent.vo.AgentVO;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * <pre>
 * 经销商
 * </pre>
 * <small> 2019-07-26 10:05:37 | Wen</small>
 */
@Controller
@RequestMapping("/agent/agent")
public class AgentController extends BaseController{
	@Autowired
	private AgentService agentService;
	
	@GetMapping()
	@RequiresPermissions("agent:agent:agent")
	String Agent(){
	    return "agent/agent/agent";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("agent:agent:agent")
	public Result<Page<AgentVO>> list(Integer pageNumber, Integer pageSize, AgentDto agentDTO){
		// 查询列表数据
		agentDTO.setCompanyId(getCompanyId());
        Page<AgentVO> page = agentService.getAgentPage(pageNumber,pageSize,agentDTO);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("agent:agent:add")
	String add(){
	    return "agent/agent/add";
	}

	@GetMapping("/edit/{agentId}")
	@RequiresPermissions("agent:agent:edit")
	String edit(@PathVariable("agentId") Long agentId,Model model){
		AgentVO agent = agentService.getAgentVOById(agentId);
		model.addAttribute("agent", agent);
	    return "agent/agent/edit";
	}

	/**
	 * 导出模板
	 */
	@GetMapping( "/exportAgent")
	public void exportMaterial(HttpServletResponse response){
		String fileName = "经销商导入模板.xls";
		try {
			response = ResponseUtil.getResponse(response,fileName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HSSFWorkbook wb =agentService.exportAgent(getCompanyId());
		try(OutputStream os = response.getOutputStream()) {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 进入导入excel
	 */
	@GetMapping( "/agentImport")
	public String agentImport(MultipartFile file){
		return "agent/agent/agentImport";
	}

	/**
	 * 导入excel
	 */
	@ResponseBody
	@PostMapping( "/importAgent")
	public Result importAgent(MultipartFile file){
		if (!file.isEmpty()){
			agentService.importAgent(file,getCompanyId(),getUserId());
		}
		return Result.ok();
	}



	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("agent:agent:add")
	public Result<String> save( AgentVO agent){
		agent.setInviteUserId(getUserId());
		agent.setParentUserId(getUserId());
		agent.setCompanyId(getCompanyId());
		if(agentService.addAgentVO(agent)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("agent:agent:edit")
	public Result<String>  update( AgentVO agent){
		agentService.UpdateAgentVO(agent);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("agent:agent:remove")
	public Result<String>  remove( Long agentId){
		if(agentService.deleteById(agentId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("agent:agent:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Long[] agentIds){
		agentService.deleteBatchIds(Arrays.asList(agentIds));
		return Result.ok();
	}

	/**
	 * 修改状态
	 */
	@PostMapping( "/status")
	@ResponseBody
	public Result<String>  status( Long agentId,Integer status){
		if(agentService.deleteAgentStatus(agentId,status)){
			return Result.ok();
		}
		return Result.fail();
	}

	@ResponseBody
	@GetMapping("/all")
	public Result<List<AgentDO>> list(){
		List<AgentDO> list = agentService.getAgentList(getCompanyId());
		return Result.ok(list);
	}
	
}
