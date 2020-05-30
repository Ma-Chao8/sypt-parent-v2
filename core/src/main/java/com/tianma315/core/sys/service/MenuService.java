package com.tianma315.core.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.tianma315.core.sys.domain.MenuDO;
import com.tianma315.core.sys.domain.Tree;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public interface MenuService extends IService<MenuDO> {
    Tree<MenuDO> getSysMenuTree(Long id);

    List<Tree<MenuDO>> listMenuTree(Long id);

    Tree<MenuDO> getTreeByUserId(Long userId);

    Tree<MenuDO> getTree(Long id,Long userId);

    Set<String> listPerms(Long userId);


    /**
     * @param username
     * @return
     */
    List<MenuDO> getMenuByUsername(String username);

    Set<String> listRoles(Long userId);
}
