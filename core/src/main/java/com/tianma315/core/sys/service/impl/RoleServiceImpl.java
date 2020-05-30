package com.tianma315.core.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.sys.dao.RoleMapper;
import com.tianma315.core.sys.dao.RoleMenuMapper;
import com.tianma315.core.sys.dao.UserMapper;
import com.tianma315.core.sys.dao.UserRoleMapper;
import com.tianma315.core.sys.domain.RoleDO;
import com.tianma315.core.sys.domain.RoleMenuDO;
import com.tianma315.core.sys.service.RoleService;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public class RoleServiceImpl extends CoreServiceImpl<RoleMapper, RoleDO> implements RoleService {

    public static final String ROLE_ALL_KEY = "\"role_all\"";

    public static final String DEMO_CACHE_NAME = "role";

    @Autowired
    RoleMenuMapper roleMenuMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    RoleMapper roleMapper;


    @Override
    public List<RoleDO> findAll(Long companyId,Long userId) {

        Wrapper<RoleDO> wrapper =  new EntityWrapper<>();
        if (userId == 1L){
            wrapper.eq("company_id",0L);
        }else {
            wrapper.eq("company_id",companyId);
        }

        List<RoleDO> roles = selectList(wrapper);
        return roles;
    }

    @Override
    public List<RoleDO> findListByUserId(Long id,Long companyId,Long userId) {
        List<Long> rolesIds = userRoleMapper.listRoleId(id);
        Wrapper wrapper = new EntityWrapper();
        if (userId== 1L){
            wrapper.eq("company_id",0);
        }else {
            wrapper.eq("company_id",companyId);
        }
        List<RoleDO> roles = new ArrayList<>();
        if (!ObjectUtils.equals(id,userId)){
             roles = selectList(wrapper);
        }
        for (RoleDO roleDO : roles) {
            roleDO.setRoleSign("false");
            for (Long roleId : rolesIds) {
                if (Objects.equals(roleDO.getId(), roleId)) {
                    roleDO.setRoleSign("true");
                    break;
                }
            }
        }
        return roles;
    }

    @Override
    public List<String> selectRoleList(Integer procedureLinkId) {
        return roleMapper.getRoleSignList(procedureLinkId);
    }

    @Override
    public List<RoleDO> findAllByProcedureLinkId(Integer id) {
        return roleMapper.findAllByProcedureLinkId(id);
    }

    @CacheEvict(value = DEMO_CACHE_NAME, key = ROLE_ALL_KEY)
    @Transactional
    @Override
    public boolean insert(RoleDO role) {

        int count = baseMapper.insert(role);
        List<Long> menuIds = role.getMenuIds();
        Long roleId = role.getId();
        List<RoleMenuDO> rms = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenuDO rmDo = new RoleMenuDO();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        roleMenuMapper.removeByRoleId(roleId);
        if (rms.size() > 0) {
            roleMenuMapper.batchSave(rms);
        }
        return retBool(count);
    }

    @CacheEvict(value = DEMO_CACHE_NAME, key = ROLE_ALL_KEY)
    @Transactional
    @Override
    public boolean deleteById(Serializable id) {
        int count = baseMapper.deleteById(id);
        roleMenuMapper.removeByRoleId(id);
        return retBool(count);
    }

    @CacheEvict(value = DEMO_CACHE_NAME, key = ROLE_ALL_KEY)
    @Override
    public boolean updateById(RoleDO role) {
        int r = baseMapper.updateById(role);
        List<Long> menuIds = role.getMenuIds();
        Long roleId = role.getId();
        roleMenuMapper.removeByRoleId(roleId);
        List<RoleMenuDO> rms = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenuDO rmDo = new RoleMenuDO();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        if (rms.size() > 0) {
            roleMenuMapper.batchSave(rms);
        }
        return retBool(r);
    }

}
