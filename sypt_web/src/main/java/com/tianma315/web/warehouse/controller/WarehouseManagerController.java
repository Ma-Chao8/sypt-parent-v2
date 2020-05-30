package com.tianma315.web.warehouse.controller;


import java.util.Arrays;

import com.tianma315.core.base.Result;
import com.tianma315.core.sys.domain.UserDO;
import com.tianma315.core.sys.domain.vo.UserVo;
import com.tianma315.core.sys.service.UserService;
import com.tianma315.core.warehouse.domain.vo.WarehouseManagerVo;
import com.tianma315.logger.annotation.Logger;
import com.tianma315.web.base.BaseController;
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
import com.tianma315.core.warehouse.domain.WarehouseManagerDO;
import com.tianma315.core.warehouse.service.WarehouseManagerService;


/**
 * 
 * <pre>
 * 产品员工关联
 * </pre>
 * <small> 2019-08-20 15:11:51 | Lgc</small>
 */
@Controller
@RequestMapping("/warehouse/warehouseManager")
public class WarehouseManagerController extends BaseController {
	@Autowired
	private WarehouseManagerService warehouseManagerService;
	@Autowired
	private UserService userService;

	@GetMapping()
	@RequiresPermissions("warehouse:warehouseManager:warehouseManager")
	String WarehouseManager(){
	    return "warehouse/warehouseManager/warehouseManager";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("warehouse:warehouseManager:warehouseManager")
	public Result<Page<WarehouseManagerVo>> list(Integer pageNumber, Integer pageSize, WarehouseManagerDO warehouseManagerDTO){

		warehouseManagerDTO.setCompanyId(getCompanyId());
		Page<WarehouseManagerVo> page=warehouseManagerService.getPage(pageNumber,pageSize,warehouseManagerDTO);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("warehouse:warehouseManager:add")
	String add(){
	    return "warehouse/warehouseManager/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("warehouse:warehouseManager:edit")
	String edit(@PathVariable("id") Long id,Model model){
		WarehouseManagerVo warehouseManager = warehouseManagerService.selectVoById(id);
		model.addAttribute("warehouseManager", warehouseManager);
	    return "warehouse/warehouseManager/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("warehouse:warehouseManager:add")
	public Result<String> save( WarehouseManagerVo warehouseManagervo){
		warehouseManagervo.setCompanyId(getCompanyId());
		if (warehouseManagerService.save(warehouseManagervo))
			return Result.ok();
		return Result.fail();


	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("warehouse:warehouseManager:edit")
	public Result<String>  update( WarehouseManagerVo warehouseManager){
		warehouseManagerService.updateById(warehouseManager);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("warehouse:warehouseManager:remove")
	public Result<String>  remove( Long id){
		if(warehouseManagerService.deleteById(id)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("warehouse:warehouseManager:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Long[] ids){
		warehouseManagerService.deleteBatchIds(Arrays.asList(ids));
		return Result.ok();
	}

	/**
	 * 修改状态
	 */
	@PostMapping( "/status")
	@ResponseBody
	public Result<String>  status ( Long id,Integer status){
		//System.out.println("id==============="+id);
		if(warehouseManagerService.deleteUserStatus(id,status)){
			return Result.ok();
		}
		return Result.fail();
	}

	/**
	 * @param userId
	 * @param model
	 * @return
	 */
	@Logger("请求更改发货员密码")
	@GetMapping("/resetPwd/{id}")
	String resetPwd(@PathVariable("id") Long userId, Model model) {
		UserDO userDO = new UserDO();
		userDO.setId(userId);
		model.addAttribute("user", userDO);
		return  "warehouse/warehouseManager/reset_pwd";
	}
	/**
	 * @param userVO
	 * @return
	 */
	@Logger("提交更改发货员密码")
	@PostMapping("/resetPwd")
	@ResponseBody
	Result<String> resetPwd(UserVo userVO) {
		userService.warehouseManagerResetPwd(userVO);
		return Result.ok();
	}
	
}
