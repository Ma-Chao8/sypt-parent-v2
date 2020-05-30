package com.tianma315.core.stock.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.stock.domain.StockDO;
import com.tianma315.core.stock.domain.vo.StockTypeVO;
import com.tianma315.core.stock.domain.vo.StockVO;

/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-08-22 15:46:55 | wen</small>
 */
public interface StockService extends CoreService<StockDO> {

    Page<StockVO> getPage(Page<StockVO> page, StockVO stockVO);

    StockVO selectStockById(Long stockId);

    Boolean clearStock(StockTypeVO stockTypeVO);


}
