package com.tianma315.core.industry.service;

import com.tianma315.core.base.CoreService;
import com.tianma315.core.industry.domain.IndustryDO;

/**
 * 
 * <pre>
 * 行业
 * </pre>
 * <small> 2019-06-29 14:23:26 | Wen</small>
 */
public interface IndustryService extends CoreService<IndustryDO> {
    Boolean deleteIndustry(Integer id);
}
