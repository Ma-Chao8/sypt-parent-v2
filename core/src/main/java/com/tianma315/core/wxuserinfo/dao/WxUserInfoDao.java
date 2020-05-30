package com.tianma315.core.wxuserinfo.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.wxuserinfo.domain.WxUserInfoDO;
import com.tianma315.core.wxuserinfo.vo.WxUserInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <pre>
 * 微信用户数据
 * </pre>
 * <small> 2019-06-27 14:29:48 | Wen</small>
 */
public interface WxUserInfoDao extends CoreMapper<WxUserInfoDO> {
    Integer countMonthNum();

    List<WxUserInfoVO> countProvinceUser(@Param("companyId") Long companyId);

    /**
     * @param company_id
     * @return
     */
    int selectTouristNum(@Param("company_id") Long company_id);

    /**
     * @param company_id
     * @return
     */
    int selectConsumerFNum(@Param("company_id") Long company_id);

    /**
     * @param company_id
     * @return
     */
    int selectConsumerSNum(@Param("company_id") Long company_id);
}
