package com.tianma315.core.sys.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.sys.domain.RoleDO;

import java.util.List;

/**
 * <pre>
 * 角色
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
public interface RoleMapper extends CoreMapper<RoleDO> {

    List<String> getRoleSignList(Integer id);

    List<RoleDO> findAllByProcedureLinkId(Integer id);
}
