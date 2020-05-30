package com.tianma315.core.stock.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.stock.domain.StockDO;
import com.tianma315.core.stock.domain.vo.StockVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-08-22 15:46:55 | wen</small>
 */
public interface StockDao extends CoreMapper<StockDO> {

    List<StockVO> selectlists(Page<StockVO> page, StockVO stockVO);

    StockVO selectStockById(Long stockId);


    StockDO selectByProductId(Map map);
}
