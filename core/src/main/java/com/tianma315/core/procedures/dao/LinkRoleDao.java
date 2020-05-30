package com.tianma315.core.procedures.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.procedures.domain.LinkContentDO;
import com.tianma315.core.procedures.domain.LinkRole;

/**
 * @author lgc
 * @createDate 2020/5/20 - 14:35
 */
public interface LinkRoleDao extends CoreMapper<LinkRole> {
    int updateStatusByLinkId(Integer procedureLinkId);
}
