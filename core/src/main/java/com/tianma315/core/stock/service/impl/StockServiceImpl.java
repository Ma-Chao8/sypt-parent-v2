package com.tianma315.core.stock.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.product.dao.ProductMapper;
import com.tianma315.core.product.domain.pojo.Product;
import com.tianma315.core.stock.domain.vo.StockTypeVO;
import com.tianma315.core.stock.domain.vo.StockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianma315.core.stock.dao.StockDao;
import com.tianma315.core.stock.domain.StockDO;
import com.tianma315.core.stock.service.StockService;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-08-22 15:46:55 | wen</small>
 */
@Service
public class StockServiceImpl extends CoreServiceImpl<StockDao, StockDO> implements StockService {
    @Autowired
    StockDao stockDao;

    @Autowired
    ProductMapper productMapper;

    @Override
    public Page<StockVO> getPage(Page<StockVO> page, StockVO stockVO) {
       List<StockVO> stockVOlist=stockDao.selectlists(page,stockVO);
       List<StockVO> lists = new ArrayList<>();
        for (StockVO stockVO1 : stockVOlist){
            Product product = productMapper.selectById(stockVO1.getProductId());
            int i = (int) stockVO1.getStockNumber() / product.getBoxSize();
            stockVO1.setBigNumber(i);
            stockVO1.setSmallNumber(stockVO1.getStockNumber() % product.getBoxSize());
            lists.add(stockVO1);
        }
       page.setRecords(lists);
        return page;
    }

    @Override
    public StockVO selectStockById(Long stockId) {
        StockVO stockVO=stockDao.selectStockById(stockId);
        return stockVO;
    }

    /**
     * 待定，暂时不动
     * @param stockTypeVO
     * @return
     */
    @Override
    public Boolean clearStock(StockTypeVO stockTypeVO) {
        EntityWrapper<StockDO> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("company_id",stockTypeVO.getCompanyId());
        //如果仓库id不为空或者不为0说明用户选的择了仓库
        if (stockTypeVO.getWarehouseId()!=null && !stockTypeVO.getWarehouseId().equals(0)){
            entityWrapper.eq("warehouse_id",stockTypeVO.getWarehouseId());
        }
        //type:0清除全部，1清楚为负数的记录
        if (stockTypeVO.getType().equals(1)){
            entityWrapper.lt("big_number",0);
        }
        return null;
    }
}
