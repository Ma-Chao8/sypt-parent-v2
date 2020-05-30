package com.tianma315.core.sys.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.sys.domain.MenuDO;
import com.tianma315.core.sys.domain.RoleMenuDO;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 角色与菜单对应关系
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
public interface RoleMenuMapper extends CoreMapper<RoleMenuDO> {
	
	List<Long> listMenuIdByRoleId(Serializable roleId);
	
	int removeByRoleId(Serializable roleId);
	
	int batchSave(List<RoleMenuDO> list);

	List<MenuDO> selectByRoleId(Long roleId);

	List<MenuDO> selectByUserId(Long userId);
}
