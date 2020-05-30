package com.tianma315.api.core.agent.rest;


//                   _ooOoo_
//                  o8888888o
//                  88" . "88
//                  (| -_- |)
//                  O\  =  /O
//               ____/`---'\____
//             .'  \\|     |//  `.
//            /  \\|||  :  |||//  \
//           /  _||||| -:- |||||-  \
//           |   | \\\  -  /// |   |
//           | \_|  ''\---/''  |   |
//           \  .-\__  `-`  ___/-. /
//         ___`. .'  /--.--\  `. . __
//      ."" '<  `.___\_<|>_/___.'  >'"".
//     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//     \  \ `-.   \_ __\ /__ _/   .-` /  /
//======`-.____`-.___\_____/___.-`____.-'======
//                   `=---='
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//         佛祖保佑       永无BUG

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.api.core.controller.BaseRest;
import com.tianma315.core.agent.domain.AgentDO;
import com.tianma315.core.agent.dto.AgentDto;
import com.tianma315.core.agent.service.AgentService;
import com.tianma315.core.agent.vo.AgentVO;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.base.Result;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 经销商
 * <p>
 * Created by zcm on 2018/5/14.
 */
@RestController
@RequestMapping("/agent")
public class AgentRest extends BaseRest{
    @Autowired
    private AgentService agentService;

    @GetMapping("/page")
//    @RequiresPermissions("agent:agent:agent")
    @RequiresAuthentication
    Result<Page<AgentVO>> page(Integer pageNumber, Integer pageSize, AgentDto agentDTO) {
        try {
            agentDTO.setCompanyId(getCompanyId());
            Page<AgentVO> page = agentService.getAgentPage(pageNumber, pageSize, agentDTO);
            return Result.ok(page);
        } catch (MessageException e) {
            e.printStackTrace();
            return Result.fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

}
