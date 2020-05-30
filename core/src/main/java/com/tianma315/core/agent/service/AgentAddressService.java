package com.tianma315.core.agent.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.agent.domain.AgentAddressDO;
import com.tianma315.core.agent.vo.AgentAddressVO;
import com.tianma315.core.base.CoreService;

import java.util.List;

/**
 * 
 * <pre>
 * 经销商地址
 * </pre>
 * <small> 2019-07-26 10:05:37 | Wen</small>
 */
public interface AgentAddressService extends CoreService<AgentAddressDO> {
    Page<AgentAddressVO> getAgentAddressVOPage(Integer pageNumber, Integer pageSize, AgentAddressDO agentAddressDTO);

    Boolean addAgentAddressDO(AgentAddressDO agentAddressDO);

    Boolean deleteAgentAddressDO(Long id,Integer status);

    Boolean updateStatus(Long id,Long agentId);

    List<AgentAddressDO> getAgentAddressByStatus(Long agentId, Integer status);
}
