package com.tianma315.core.company.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.company.domain.pojo.DemoDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianma315.core.company.dao.DemoDao;
import com.tianma315.core.company.service.DemoService;

import java.util.List;


/**
 * <pre>
 *
 * </pre>
 * <small> 2019-09-25 16:51:32 | Lgc</small>
 */
@Service
public class DemoServiceImpl extends CoreServiceImpl<DemoDao, DemoDO> implements DemoService {

    @Autowired
    DemoDao demoDao;


    @Override
    public List<DemoDO> getDemoByCompanyId(Long companyId) {
        return demoDao.getDemoByCompanyId(companyId);
    }

    @Override
    public Page<DemoDO> getPage(long companyId, int pageNumber, int pageSize) {
        Wrapper<DemoDO> wrapper = new EntityWrapper<>();
        wrapper.eq("cd.company_id", companyId);
        wrapper.eq("demo_status", 1);
        Page<DemoDO> page = new Page<>(pageNumber, pageSize);
        List<DemoDO> list = baseMapper.selectDemoPage(page, wrapper);
        page.setRecords(list);
        return page;
    }
}
