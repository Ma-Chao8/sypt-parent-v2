package com.tianma315.core.report.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.report.dao.CheckTypeDao;
import com.tianma315.core.report.domain.CheckTypeDO;
import com.tianma315.core.report.service.CheckTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * <pre>
 * 检测类型
 * </pre>
 * <small> 2019-06-27 17:10:49 | Wen</small>
 */
@Service
public class CheckTypeServiceImpl extends CoreServiceImpl<CheckTypeDao, CheckTypeDO> implements CheckTypeService {

    @Override
    public Boolean deleteCheckType(Long id) {
        CheckTypeDO checkTypeDO = new CheckTypeDO();
        checkTypeDO.setCheckTypeId(id);
        checkTypeDO.setState(StateEnum.del.getIndex());
        Boolean result = updateById(checkTypeDO);
        return result;
    }

    @Override
    public List<CheckTypeDO> getAll() {
        EntityWrapper<CheckTypeDO> entityWrapper = new EntityWrapper();
        entityWrapper.eq("state",StateEnum.not_del.getIndex());
        List<CheckTypeDO> list = selectList(entityWrapper);
        return list;
    }

    @Override
    public List<String> getAllName() {
        EntityWrapper<CheckTypeDO> entityWrapper = new EntityWrapper();
        entityWrapper.eq("state",StateEnum.not_del.getIndex());
        List<CheckTypeDO> list = selectList(entityWrapper);
        List<String> resultList = list.stream().map(item -> item.getCheckTypeName()).collect(Collectors.toList());
        return resultList;
    }
}
