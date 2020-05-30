package com.tianma315.core.sys.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.sys.domain.UserRoleDO;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 用户与角色对应关系
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
public interface UserRoleMapper extends CoreMapper<UserRoleDO> {

    List<Long> listRoleId(Serializable userId);

    int removeByUserId(Serializable userId);

    int batchSave(List<UserRoleDO> list);

    int batchRemoveByUserId(Long[] ids);
}
