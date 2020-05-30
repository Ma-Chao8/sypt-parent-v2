package com.tianma315.core.sys.service;

import com.tianma315.core.base.CoreService;
import com.tianma315.core.sys.domain.DictDO;
import com.tianma315.core.sys.domain.UserDO;

import java.util.List;

/**
 * <pre>
 * 数据字典
 * </pre>
 * 
 * <small> 2018年1月3日 | Aron</small>
 */
public interface DictService extends CoreService<DictDO> {
    
    List<DictDO> listType();

    String getName(String type, String value);

    /**
     * 获取爱好列表
     * 
     * @return
     * @param userDO
     */
    List<DictDO> getHobbyList(UserDO userDO);

    /**
     * 获取性别列表
     * 
     * @return
     */
    List<DictDO> getSexList();
}
