package com.tianma315.core.sys.service.impl;

import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.sys.dao.ConfigMapper;
import com.tianma315.core.sys.domain.ConfigDO;
import com.tianma315.core.sys.service.ConfigService;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * </pre>
 * <small> 2018年4月6日 | Aron</small>
 */
@Service
public class ConfigServiceImpl extends CoreServiceImpl<ConfigMapper, ConfigDO> implements ConfigService {

    @Override
    public ConfigDO getByKey(String k) {
        ConfigDO entity = new ConfigDO();
        entity.setK(k);
        return baseMapper.selectOne(entity);
    }

    @Override
    public String getValuByKey(String k) {
        ConfigDO bean = this.getByKey(k);
        return bean == null ? "" : bean.getV();
    }

}
