package com.tianma315.core.sys.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.IService;
import com.tianma315.core.sys.domain.RoleDO;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public interface RoleService extends IService<RoleDO> {
    List<RoleDO> findAll(Long companyId,Long userId);
    List<RoleDO> findListByUserId(Long id,Long companyId,Long userId);

    List<String> selectRoleList(Integer procedureLinkId);

    List<RoleDO> findAllByProcedureLinkId(Integer id);
}
