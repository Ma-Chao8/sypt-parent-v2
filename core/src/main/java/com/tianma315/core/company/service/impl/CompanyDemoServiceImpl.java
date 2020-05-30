package com.tianma315.core.company.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.company.dao.CompanyDemoDao;
import com.tianma315.core.company.domain.pojo.CompanyDemoDO;
import com.tianma315.core.company.domain.pojo.CompanyDemoVO;
import com.tianma315.core.company.service.CompanyDemoService;
import com.tianma315.core.procedures.domain.ProceduresProductDO;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-08-26 09:52:18 | Aron</small>
 */
@Service
public class CompanyDemoServiceImpl extends CoreServiceImpl<CompanyDemoDao, CompanyDemoDO> implements CompanyDemoService {

    @Override
    public void addCompanyDemo(CompanyDemoVO cdemo) {
        List<Integer> demoIdList = cdemo.getDemoIdList();
        demoIdList.forEach(s ->System.out.println("3333333333333333333333"+s.toString()));

        Integer companyId = cdemo.getCompanyId();
        EntityWrapper<CompanyDemoDO> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("company_id",companyId);
        //每次操作都要删除旧数据，保证数据最新
        delete(entityWrapper);

        List<Integer> list = cdemo.getDemoIdList();
        for (Integer demoId:list){
            CompanyDemoDO companyDemoDO = new CompanyDemoDO();
            companyDemoDO.setCompanyId(companyId);
            companyDemoDO.setDemoId(demoId);
            insert(companyDemoDO);
        }

    }
}
