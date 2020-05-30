package com.tianma315.core.sys.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.sys.domain.DeptDO;

/**
 * <pre>
 * 部门管理
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
public interface DeptMapper extends CoreMapper<DeptDO> {

	Long[] listParentDept();

	int getDeptUserNumber(Long deptId);
}
