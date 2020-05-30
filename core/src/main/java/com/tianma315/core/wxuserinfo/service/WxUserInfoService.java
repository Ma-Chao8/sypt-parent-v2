package com.tianma315.core.wxuserinfo.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.wxuserinfo.domain.WxUserInfoDO;
import com.tianma315.core.wxuserinfo.vo.WxUserInfoVO;

import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 微信用户数据
 * </pre>
 * <small> 2019-06-27 14:29:48 | Wen</small>
 */
public interface WxUserInfoService extends CoreService<WxUserInfoDO> {
    Integer countMonthNum();

    Page<WxUserInfoDO> getWxUserInfoDOPage(Integer pageNumber, Integer pageSize, WxUserInfoDO wxUserInfoDTO);

    List<WxUserInfoVO> countProvinceUser(Long companyId);

    /**
     * @param userId
     * @param pageNumber
     * @param pageSize
     * @param searchKey
     * @return
     */
    Page getPage(Long userId, int pageNumber, int pageSize, String searchKey);

    /**
     * @param userId
     * @return
     */
    HashMap<String, Object> getRepurchase(Long userId);

    /**
     *
     * @param wxUserInfo
     * @param code
     * @return
     */
    boolean edit(WxUserInfoDO wxUserInfo, String code);
}
