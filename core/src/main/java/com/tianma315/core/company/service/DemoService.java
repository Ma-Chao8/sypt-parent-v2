package com.tianma315.core.company.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.company.domain.pojo.DemoDO;

import java.util.List;

/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-09-25 16:51:32 | Lgc</small>
 */
public interface DemoService extends CoreService<DemoDO> {

    List<DemoDO> getDemoByCompanyId(Long companyId);


    /**
     *
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<DemoDO> getPage(long companyId,int pageNumber, int pageSize);


}
