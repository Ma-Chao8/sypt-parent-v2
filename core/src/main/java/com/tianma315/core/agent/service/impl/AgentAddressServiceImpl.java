package com.tianma315.core.agent.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.agent.dao.AgentAddressDao;
import com.tianma315.core.agent.domain.AgentAddressDO;
import com.tianma315.core.agent.service.AgentAddressService;
import com.tianma315.core.agent.vo.AgentAddressVO;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.exception.MessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Calendar;
import java.util.List;


/**
 * 
 * <pre>
 * 经销商地址
 * </pre>
 * <small> 2019-07-26 10:05:37 | Wen</small>
 */
@Service
public class AgentAddressServiceImpl extends CoreServiceImpl<AgentAddressDao, AgentAddressDO> implements AgentAddressService {

    @Autowired
    private AgentAddressDao agentAddressDao;

    @Override
    public Page<AgentAddressVO> getAgentAddressVOPage(Integer pageNumber, Integer pageSize, AgentAddressDO agentAddressDTO) {
        Page<AgentAddressVO> page = new Page<>(pageNumber,pageSize);
        List<AgentAddressVO> list = agentAddressDao.getAgentAddressPage(page,agentAddressDTO);
        page.setRecords(list);
        return page;
    }

    @Override
    public Boolean addAgentAddressDO(AgentAddressDO agentAddressDO) {
        agentAddressDO.setStatus(0);//0是非默认地址
        agentAddressDO.setCreateDate(Calendar.getInstance().getTime());
        Boolean result = insert(agentAddressDO);
        return result;
    }

    @Override
    public Boolean deleteAgentAddressDO(Long id,Integer status) {
        //状态为1时是默认地址，默认地址不能修改状态
        if (status.equals(1)){
            throw new MessageException("默认地址不能删除");
        }else{
            AgentAddressDO agentAddressDO = new AgentAddressDO();
            agentAddressDO.setStatus(2);
            agentAddressDO.setAddressId(id);
            Boolean result = updateById(agentAddressDO);
            return result;
        }
    }

    @Override
    public Boolean updateStatus(Long id, Long agentId) {
        // 把默认地址设置为非默认地址
        EntityWrapper<AgentAddressDO> wrapper = new EntityWrapper();
        wrapper.eq("agent_id",agentId);
        wrapper.eq("status",1);
        AgentAddressDO agentAddressDO = new AgentAddressDO();
        agentAddressDO.setStatus(0);
        update(agentAddressDO,wrapper);

        //把当前记录设置为默认地址
        AgentAddressDO newAgentAddress = new AgentAddressDO();
        newAgentAddress.setStatus(1);
        newAgentAddress.setAddressId(id);
        Boolean result = updateById(newAgentAddress);
        return result;
    }

    @Override
    public List<AgentAddressDO> getAgentAddressByStatus(Long agentId, Integer status) {
        EntityWrapper<AgentAddressDO> entityWrapper = new EntityWrapper();
        entityWrapper.eq("agent_id",agentId);
        entityWrapper.eq("status",status);
        List<AgentAddressDO> agentAddressDOS = selectList(entityWrapper);
        return agentAddressDOS;
    }
}
