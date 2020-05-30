package com.tianma315.core.agent.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.agent.domain.AgentDO;
import com.tianma315.core.agent.dto.AgentDto;
import com.tianma315.core.agent.vo.AgentVO;
import com.tianma315.core.base.CoreService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import sun.management.Agent;

import java.util.List;

/**
 * 
 * <pre>
 * 经销商
 * </pre>
 * <small> 2019-07-26 10:05:37 | Wen</small>
 */
public interface AgentService extends CoreService<AgentDO> {
    List<Agent> getAgentByLevelId(Long levelId);

    Page<AgentVO> getAgentPage(Integer pageNumber, Integer pageSize, AgentDto agentDTO);

    Boolean addAgentVO(AgentVO agentVO);

    Boolean deleteAgentStatus(Long id,Integer status);

    Boolean UpdateAgentVO(AgentVO agentVO);

    AgentVO getAgentVOById(Long id);

    List<AgentDO> getAgentList(Long companyId);

    HSSFWorkbook exportAgent(Long companyId);

    void importAgent(MultipartFile file, Long companyId, Long userId);
}
