package com.tianma315.core.material.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.material.domain.PurchaseDO;
import com.tianma315.core.material.dto.PurchaseDto;
import com.tianma315.core.material.vo.PurchaseVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 
 * <pre>
 * 原材料进库表
 * </pre>
 * <small> 2019-06-24 11:23:31 | Wen</small>
 */
public interface PurchaseDao extends CoreMapper<PurchaseDO> {
    List<PurchaseVO> getPurchaseVO(@Param("page")Page page, PurchaseDto purchaseDTO);

//    List<PurchaseAndArrtVO> statisticsMaterialTable(@Param("materialId")Integer materialId,
//                                                    @Param("beginDate")Date beginDate, @Param("endDate")Date endDate);

}
