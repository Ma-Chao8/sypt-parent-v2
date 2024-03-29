package com.tianma315.core.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.sys.dao.DeptMapper;
import com.tianma315.core.sys.domain.DeptDO;
import com.tianma315.core.sys.domain.Tree;
import com.tianma315.core.sys.service.DeptService;
import com.tianma315.core.utils.BuildTree;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public class DeptServiceImpl extends CoreServiceImpl<DeptMapper, DeptDO> implements DeptService {

    @Override
    public Tree<DeptDO> getTree(Long userId,Long companyId) {
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        Wrapper<DeptDO> wrapper = new EntityWrapper<>();
        if (userId != 1) wrapper.eq("company_id",companyId);
        List<DeptDO> sysDepts = baseMapper.selectList(wrapper);
        for (DeptDO sysDept : sysDepts) {
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(sysDept.getId().toString());
            tree.setParentId(sysDept.getParentId().toString());
            tree.setText(sysDept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<DeptDO> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public boolean checkDeptHasUser(Long deptId) {
        // 查询部门以及此部门的下级部门
        int result = baseMapper.getDeptUserNumber(deptId);
        return result == 0;
    }

}
