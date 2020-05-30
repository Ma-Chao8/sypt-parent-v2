package com.tianma315.core.wxuserinfo.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.company.domain.pojo.Company;
import com.tianma315.core.company.service.CompanyService;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.social.dao.UserConnectionMapper;
import com.tianma315.core.wxuserinfo.dao.WxUserInfoDao;
import com.tianma315.core.wxuserinfo.domain.WxUserInfoDO;
import com.tianma315.core.wxuserinfo.service.WxUserInfoService;
import com.tianma315.core.wxuserinfo.vo.WxUserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <pre>
 * 微信用户数据
 * </pre>
 * <small> 2019-06-27 14:29:48 | Wen</small>
 */
@Service
public class WxUserInfoServiceImpl extends CoreServiceImpl<WxUserInfoDao, WxUserInfoDO> implements WxUserInfoService {

    @Autowired
    private WxUserInfoDao wxUserInfoDao;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserConnectionMapper userConnectionMapper;
//    @Autowired
//    private TraceFileService traceFileService;
//

    @Override
    public Integer countMonthNum() {
        return wxUserInfoDao.countMonthNum();
    }

    @Override
    public Page<WxUserInfoDO> getWxUserInfoDOPage(Integer pageNumber, Integer pageSize, WxUserInfoDO wxUserInfoDTO) {
        // 查询列表数据
        Page<WxUserInfoDO> page = new Page<>(pageNumber, pageSize);

        Wrapper<WxUserInfoDO> wrapper = new EntityWrapper<WxUserInfoDO>(wxUserInfoDTO);
        page = selectPage(page, wrapper);
        int total = selectCount(wrapper);

//        List<WxUserInfoDO> wxUserInfoDOS = page.getRecords();

//        List<WxUserInfoDO> result = wxUserInfoDOS.stream().map(item -> )
        page.setTotal(total);
        return page;
    }

    @Override
    public List<WxUserInfoVO> countProvinceUser(Long companyId) {
        List<WxUserInfoVO> wxUserInfoVOS = wxUserInfoDao.countProvinceUser(companyId);
        return wxUserInfoVOS;
    }

    @Override
    public Page getPage(Long userId, int pageNumber, int pageSize, String searchKey) {
        Company company = companyService.getByUserId(userId);
        if (company == null) {
            throw new MessageException("商户信息获取失败");
        }
        Page page = new Page(pageNumber, pageSize);
        Wrapper<WxUserInfoDO> wrapper = new EntityWrapper<WxUserInfoDO>()
                .eq("company_id", company.getCompanyId())
                .like("nickname", searchKey);
        selectPage(page, wrapper);
        return page;
    }

    @Override
    public HashMap<String, Object> getRepurchase(Long userId) {
        Company company = companyService.getByUserId(userId);
        if (company == null) {
            throw new MessageException("商户信息获取失败");
        }

        int touristNum = baseMapper.selectTouristNum(company.getCompanyId());    //游客数量
        int consumerFNum = baseMapper.selectConsumerFNum(company.getCompanyId());    //初次消费者数量
        int consumerSNum = baseMapper.selectConsumerSNum(company.getCompanyId());    //复购消费者数量
        Map<String, Integer> map = new HashMap<>();
        map.put("游客", touristNum);
        map.put("初次消费", consumerFNum);
        map.put("复购", consumerSNum);

        List<Map<String, Object>> pieDatas = new ArrayList<>();
        for (String key : map.keySet()) {
            pieDatas.add(new HashMap<String, Object>() {{
                put("name", key);
                put("value", map.get(key));
            }});
        }

        return new HashMap<String, Object>() {{
            put("bar", map);
            put("pie", pieDatas);
        }};
    }

    @Override
    public boolean edit(WxUserInfoDO wxUserInfo, String code) {
//        TraceInfoVo traceInfo = traceFileService.getTraceInfo(null, code);
//        wxUserInfo.setCompanyId(traceInfo.getCompanyId());

        Wrapper<WxUserInfoDO> wrapper = new EntityWrapper<>();
        wrapper.eq("openid", wxUserInfo.getOpenid())
                .eq("company_id", wxUserInfo.getCompanyId());
        List<WxUserInfoDO> list = selectList(wrapper);
        if (list == null || list.isEmpty()) {
            wxUserInfo.setCreatedDate(new Date());
            insert(wxUserInfo);
        } else {
            WxUserInfoDO userInfo = list.get(0);
            wxUserInfo.setWxId(userInfo.getWxId());
            wxUserInfo.setCreatedDate(userInfo.getCreatedDate());
            updateById(wxUserInfo);
        }
        return true;
    }


}
