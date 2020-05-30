package com.tianma315.core.sys.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.sys.domain.MenuDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <pre>
 * 菜单管理
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
public interface MenuMapper extends CoreMapper<MenuDO> {
	
	List<MenuDO> listMenuByUserId(Long id);
	
	List<String> listUserPerms(Long id);

	/**
	 *
	 * @param username
	 * @return
	 */
    List<MenuDO> selectMenuByUsername(@Param("username") String username);

	List<String> listUserRoles(Long userId);

	Integer selectUserCompanyId(Long userId);
}
