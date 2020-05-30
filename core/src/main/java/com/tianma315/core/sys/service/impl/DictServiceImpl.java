package com.tianma315.core.sys.service.impl;

import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.sys.dao.DictMapper;
import com.tianma315.core.sys.domain.DictDO;
import com.tianma315.core.sys.domain.UserDO;
import com.tianma315.core.sys.service.DictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public class DictServiceImpl extends CoreServiceImpl<DictMapper, DictDO> implements DictService {
    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<DictDO> listType() {
        return dictMapper.listType();
    }

    @Override
    public String getName(String type, String value) {
        DictDO selectOne = DictDO.dao.selectOne("type = {0} and value = {1}", type, value);
        return selectOne == null ? "" : selectOne.getName();
    }

    @Override
    public List<DictDO> getHobbyList(UserDO userDO) {
        List<DictDO> hobbyList = DictDO.dao.selectList("type = {0}", "hobby");

        if (StringUtils.isNotEmpty(userDO.getHobby())) {
            String userHobbys[] = userDO.getHobby().split(";");
            for (String userHobby : userHobbys) {
                for (DictDO hobby : hobbyList) {
                    if (!Objects.equals(userHobby, hobby.getId().toString())) {
                        continue;
                    }
                    hobby.setRemarks("true");
                    break;
                }
            }
        }

        return hobbyList;
    }

    @Override
    public List<DictDO> getSexList() {
        return DictDO.dao.selectList("type = {0}", "sex");
    }

}
