package com.tianma315.core.attr.dao;



import com.tianma315.core.attr.domain.AttrDO;
import com.tianma315.core.base.CoreMapper;


import java.util.List;

/**
 * 
 * <pre>
 * 辅助属性
 * </pre>
 * <small> 2019-08-02 15:38:24 | Wen</small>
 */
public interface AttrDao extends CoreMapper<AttrDO> {

    List<AttrDO> selectByMerchantId(long merchantId);

    int deleteByMerchantId(long merchantId);
}
