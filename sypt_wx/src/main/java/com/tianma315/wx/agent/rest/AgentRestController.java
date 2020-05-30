package com.tianma315.wx.agent.rest;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.agent.domain.AgentDO;
import com.tianma315.core.agent.service.AgentService;
import com.tianma315.core.agent.vo.AgentVO;
import com.tianma315.core.base.Result;
import com.tianma315.core.exception.MessageException;
import com.tianma315.wx.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 经销商
 *  * <p>
 *  * Created by LGC on 2019/9/05.
 */
@RestController
@RequestMapping("/agent")
public class AgentRestController extends BaseController {
    @Autowired
    private AgentService agentService;

    /**
     * 分页
     *
     *
     * @return
     */
    @GetMapping("/page")
    Result<Page<AgentDO>> page(Integer start,Integer pageSize,String agentName) {
        Page<AgentDO> page = new Page<>(start,pageSize);
        Wrapper<AgentDO> wrapper = new EntityWrapper<>();
        wrapper.like("agent_name",agentName);
        wrapper.eq("company_id",getCompany().getCompanyId());
        try {
            page=agentService.selectPage(page,wrapper);
            return Result.ok(page);
        } catch (MessageException e) {
            e.printStackTrace();
            return Result.fail(e.getMessage(), page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail(page);
    }

}
