package com.tianma315.core.report.service;


import com.tianma315.core.base.CoreService;
import com.tianma315.core.report.domain.CheckMechanismDO;

import java.util.List;

/**
 * 
 * <pre>
 * 检测机构
 * </pre>
 * <small> 2019-06-27 17:10:49 | Wen</small>
 */
public interface CheckMechanismService extends CoreService<CheckMechanismDO> {
    Boolean deleteCheckMechanism(Long id);

    List<CheckMechanismDO> getAll();

    List<String> getAllName();


    
}
