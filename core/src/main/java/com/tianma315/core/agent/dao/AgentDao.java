package com.tianma315.core.agent.dao;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.agent.domain.AgentDO;
import com.tianma315.core.agent.dto.AgentDto;
import com.tianma315.core.agent.vo.AgentVO;
import com.tianma315.core.base.CoreMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <pre>
 * 经销商
 * </pre>
 * <small> 2019-07-26 10:05:37 | Wen</small>
 */
public interface AgentDao extends CoreMapper<AgentDO> {

    List<AgentVO> getAgentVO(@Param("page")Page page, AgentDto agentDO);
}
