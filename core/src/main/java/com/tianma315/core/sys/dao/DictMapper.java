package com.tianma315.core.sys.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.sys.domain.DictDO;

import java.util.List;

/**
 * 字典表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 15:45:42
 */
public interface DictMapper extends CoreMapper<DictDO> {

    List<DictDO> listType();
    
}
