package com.tianma315.core.invoice.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.invoice.domain.ReturnedProductDO;
import com.tianma315.core.invoice.domain.vo.ReturnedProductVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-08-22 15:48:52 | Lgc</small>
 */
public interface ReturnedProductDao extends CoreMapper<ReturnedProductDO> {

    /**
     * 查询退货单对应的产品
     *
     * @param returnedId
     * @return
     */
    List<ReturnedProductVO> selectByReturnedId(@Param("returnedId") long returnedId);
}
