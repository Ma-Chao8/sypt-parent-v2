package com.tianma315.core.procedures.service.impl;

import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.procedures.dao.LinkContentDao;
import com.tianma315.core.procedures.dao.LinkRoleDao;
import com.tianma315.core.procedures.domain.LinkContentDO;
import com.tianma315.core.procedures.domain.LinkRole;
import com.tianma315.core.procedures.service.LinkRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lgc
 * @createDate 2020/5/20 - 14:34
 */
@Service
public class LinkRoleServiceImpl extends CoreServiceImpl<LinkRoleDao, LinkRole> implements LinkRoleService {
}
