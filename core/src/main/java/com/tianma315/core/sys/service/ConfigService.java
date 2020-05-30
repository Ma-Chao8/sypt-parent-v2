package com.tianma315.core.sys.service;

import com.tianma315.core.base.CoreService;
import com.tianma315.core.sys.domain.ConfigDO;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年4月6日 | Aron</small>
 */
public interface ConfigService extends CoreService<ConfigDO> {
    ConfigDO getByKey(String k);

    String getValuByKey(String k);
}
