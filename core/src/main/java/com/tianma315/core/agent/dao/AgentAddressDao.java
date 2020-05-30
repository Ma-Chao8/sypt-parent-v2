package com.tianma315.core.agent.dao;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.agent.domain.AgentAddressDO;
import com.tianma315.core.agent.vo.AgentAddressVO;
import com.tianma315.core.base.CoreMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <pre>
 * 经销商地址
 * </pre>
 * <small> 2019-07-26 10:05:37 | Wen</small>
 */
public interface AgentAddressDao extends CoreMapper<AgentAddressDO> {
    List<AgentAddressVO> getAgentAddressPage(@Param("page")Page page, AgentAddressDO agentAddressDO);
}
