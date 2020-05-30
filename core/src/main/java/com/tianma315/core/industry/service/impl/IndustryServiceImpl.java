package com.tianma315.core.industry.service.impl;

import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.base.CoreServiceImpl;
import org.springframework.stereotype.Service;

import com.tianma315.core.industry.dao.IndustryDao;
import com.tianma315.core.industry.domain.IndustryDO;
import com.tianma315.core.industry.service.IndustryService;

/**
 * 
 * <pre>
 * 行业
 * </pre>
 * <small> 2019-06-29 14:23:26 | Wen</small>
 */
@Service
public class IndustryServiceImpl extends CoreServiceImpl<IndustryDao, IndustryDO> implements IndustryService {

    @Override
    public Boolean deleteIndustry(Integer id) {
        IndustryDO industryDO = new IndustryDO();
        industryDO.setIndustryId(id);
        industryDO.setState(StateEnum.del.getIndex());
        Boolean result = updateById(industryDO);
        return result;
    }
}
