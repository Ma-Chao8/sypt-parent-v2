package com.tianma315.core.agent.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.agent.domain.AgentLevelDO;
import com.tianma315.core.base.CoreService;

import java.util.List;

/**
 * 
 * <pre>
 * 经销商等级
 * </pre>
 * <small> 2019-07-26 10:05:37 | Wen</small>
 */
public interface AgentLevelService extends CoreService<AgentLevelDO> {

    Page<AgentLevelDO> agentLevelDOPage(Integer pageNumber, Integer pageSize, AgentLevelDO agentLevelDTO);

    Boolean addAgentLevelDO(AgentLevelDO agentLevelDO);

    Boolean deleteAgentLevel(Long id,Integer status);

    Boolean checkRepeatAgentLevel(AgentLevelDO agentLevelDO);

    List<AgentLevelDO> agentLevelDOList(Long companyId);


}
