package com.tianma315.core.agent.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.agent.dao.AgentLevelDao;
import com.tianma315.core.agent.domain.AgentLevelDO;
import com.tianma315.core.agent.service.AgentLevelService;
import com.tianma315.core.agent.service.AgentService;
import com.tianma315.core.base.CoreServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;


/**
 * 
 * <pre>
 * 经销商等级
 * </pre>
 * <small> 2019-07-26 10:05:37 | Wen</small>
 */
@Service
public class AgentLevelServiceImpl extends CoreServiceImpl<AgentLevelDao, AgentLevelDO> implements AgentLevelService {


    @Autowired
    private AgentService agentService;

    @Override
    public Page<AgentLevelDO> agentLevelDOPage(Integer pageNumber, Integer pageSize, AgentLevelDO agentLevelDTO) {
        Page<AgentLevelDO> page = new Page<>(pageNumber, pageSize);
        Wrapper<AgentLevelDO> wrapper = new EntityWrapper<AgentLevelDO>();
//        wrapper.eq("state", StateEnum.not_del);
        wrapper.eq("company_id", agentLevelDTO.getCompanyId());
        wrapper.like("level_name",agentLevelDTO.getLevelName());
        wrapper.orderBy("level_weight");

        page = selectPage(page, wrapper);
        int total = selectCount(wrapper);
        page.setTotal(total);
        return page;
    }

    @Override
    public Boolean addAgentLevelDO(AgentLevelDO agentLevelDO) {
        agentLevelDO.setCreateDate(Calendar.getInstance().getTime());
        agentLevelDO.setStatus(StateEnum.usable.getIndex());
        Boolean result = insert(agentLevelDO);
        return result;
    }

    @Override
    public Boolean deleteAgentLevel(Long id,Integer status) {
        AgentLevelDO agentLevelDO = new AgentLevelDO();
        agentLevelDO.setStatus(status);
        agentLevelDO.setLevelId(id);
        Boolean result = updateById(agentLevelDO);
        return result;
    }

    @Override
    public Boolean checkRepeatAgentLevel(AgentLevelDO agentLevelDO) {
        Boolean result;
        EntityWrapper<AgentLevelDO> wrapper = new EntityWrapper();
        wrapper.eq("level_weight",agentLevelDO.getLevelWeight());
        wrapper.eq("company_id",agentLevelDO.getCompanyId());
        //levelId不为空说明是修改而不是增加
        if (agentLevelDO.getLevelId()!=null){
            wrapper.ne("level_id",agentLevelDO.getLevelId());
        }
        List<AgentLevelDO> agentLevelDOList = selectList(wrapper);
        if (!CollectionUtils.isEmpty(agentLevelDOList)){
            result = true;
        }else{
            result = false;
        }
        return result;
    }

    @Override
    public List<AgentLevelDO> agentLevelDOList(Long companyId) {
        EntityWrapper<AgentLevelDO> wrapper = new EntityWrapper<>();
        wrapper.eq("company_id",companyId);
        wrapper.eq("status",StateEnum.usable.getIndex());
        List<AgentLevelDO> result = selectList(wrapper);
        return result;
    }
}
