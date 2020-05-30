package com.tianma315.core.report.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.report.dao.CheckMechanismDao;
import com.tianma315.core.report.domain.CheckMechanismDO;
import com.tianma315.core.report.service.CheckMechanismService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * <pre>
 * 检测机构
 * </pre>
 * <small> 2019-06-27 17:10:49 | Wen</small>
 */
@Service
public class CheckMechanismServiceImpl extends CoreServiceImpl<CheckMechanismDao, CheckMechanismDO> implements CheckMechanismService {

    @Override
    public Boolean deleteCheckMechanism(Long id) {
        CheckMechanismDO checkMechanismDO = new CheckMechanismDO();
        checkMechanismDO.setCheckMechanismId(id);
        checkMechanismDO.setState(StateEnum.del.getIndex());
        Boolean result = updateById(checkMechanismDO);
        return result;
    }

    @Override
    public List<CheckMechanismDO> getAll() {
        EntityWrapper<CheckMechanismDO> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("state",StateEnum.not_del.getIndex());
        List<CheckMechanismDO> list = selectList(entityWrapper);
        return list;
    }

    @Override
    public List<String> getAllName() {
        EntityWrapper<CheckMechanismDO> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("state",StateEnum.not_del.getIndex());
        List<CheckMechanismDO> list = selectList(entityWrapper);
        List<String> resultList = list.stream().map(item -> item.getCheckMechanismName()).collect(Collectors.toList());
        return resultList;
    }
}
