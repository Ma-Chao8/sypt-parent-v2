package com.tianma315.core.report.service;


import com.tianma315.core.base.CoreService;
import com.tianma315.core.report.domain.CheckTypeDO;

import java.util.List;

/**
 * 
 * <pre>
 * 检测类型
 * </pre>
 * <small> 2019-06-27 17:10:49 | Wen</small>
 */
public interface CheckTypeService extends CoreService<CheckTypeDO> {

     Boolean deleteCheckType(Long id);

     List<CheckTypeDO> getAll();

     List<String> getAllName();
    
}
