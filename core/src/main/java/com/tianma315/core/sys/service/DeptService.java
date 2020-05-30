package com.tianma315.core.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.tianma315.core.sys.domain.Tree;
import com.tianma315.core.sys.domain.DeptDO;

/**
 * <pre>
 * 部门管理
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
public interface DeptService extends IService<DeptDO> {
    
	Tree<DeptDO> getTree(Long userId,Long companyId);
	
	boolean checkDeptHasUser(Long deptId);
}
