package com.tianma315.web.stock.controller;


import java.util.Arrays;

import com.tianma315.core.base.Result;
import com.tianma315.core.product.domain.pojo.Product;
import com.tianma315.core.product.service.ProductService;
import com.tianma315.core.stock.dao.StockDao;
import com.tianma315.core.stock.domain.vo.StockTypeVO;
import com.tianma315.core.stock.domain.vo.StockVO;
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

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.stock.domain.StockDO;
import com.tianma315.core.stock.service.StockService;

/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-08-22 15:46:55 | wen</small>
 */
@Controller
@RequestMapping("/stock/stock")
public class StockController extends BaseController {
	@Autowired
	private StockService stockService;

	@Autowired
	private ProductService productService;

	@GetMapping("/clearStock")
	String clear(){
	    return "stock/stock/clearStock";
	}

	@GetMapping()
	@RequiresPermissions("stock:stock:stock")
	String Stock(){
		return "stock/stock/stock";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("stock:stock:stock")
	public Result<Page<StockVO>> list(Integer pageNumber, Integer pageSize, StockVO stockVO){
		// 查询列表数据
		stockVO.setCompanyId(getCompanyId());
        Page<StockVO> page = new Page<>(pageNumber, pageSize);
		page=stockService.getPage(page,stockVO);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("stock:stock:add")
	String add(){
	    return "stock/stock/add";
	}

	@GetMapping("/edit/{stockId}")
	@RequiresPermissions("stock:stock:edit")
	String edit(@PathVariable("stockId") Long stockId,Model model){
		StockVO stock = stockService.selectStockById(stockId);
		Product product = productService.selectById(stock.getProductId());
		int i = (int) stock.getStockNumber() / product.getBoxSize();
		stock.setBigNumber(i);
		stock.setSmallNumber(stock.getStockNumber() % product.getBoxSize());
		model.addAttribute("stock", stock);
	    return "stock/stock/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("stock:stock:add")
	public Result<String> save( StockDO stock){
		if(stockService.insert(stock)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("stock:stock:edit")
	public Result<String>  update( StockVO stockVO){
		Product product = productService.selectById(stockVO.getProductId());
		int cunt = stockVO.getBigNumber() * product.getBoxSize();
		cunt = cunt+stockVO.getSmallNumber();
		stockVO.setSmallNumber(cunt);
		StockDO stockDO = new StockDO();
		stockDO.setStockId(stockVO.getStockId());
		stockDO.setStockNumber(cunt);
		stockService.updateById(stockDO);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("stock:stock:remove")
	public Result<String>  remove( Long stockId){
		if(stockService.deleteById(stockId)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("stock:stock:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Long[] stockIds){
		stockService.deleteBatchIds(Arrays.asList(stockIds));
		return Result.ok();
	}


	/**
	 * 清除
	 */
	@ResponseBody
	@PostMapping("/clear")
	public Result<String> clear( StockTypeVO stockTypeVO){
		return Result.fail();
	}
}
