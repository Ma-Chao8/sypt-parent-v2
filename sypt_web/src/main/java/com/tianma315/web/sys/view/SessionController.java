package com.tianma315.web.sys.view;

import java.util.Collection;
import java.util.List;

import com.tianma315.core.base.Result;
import com.tianma315.core.sys.domain.UserOnline;
import com.tianma315.web.sys.service.SessionService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@RequestMapping("/sys/online")
@Controller
public class SessionController {
	@Autowired
    SessionService sessionService;

	@GetMapping()
	public String online() {
		return "sys/online/online";
	}

	@ResponseBody
	@RequestMapping("/list")
	public List<UserOnline> list() {
		return sessionService.list();
	}

	@ResponseBody
	@RequestMapping("/forceLogout/{sessionId}")
	public Result<String> forceLogout(@PathVariable("sessionId") String sessionId, RedirectAttributes redirectAttributes) {
		try {
			sessionService.forceLogout(sessionId);
			return Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail();
		}

	}

	@ResponseBody
	@RequestMapping("/sessionList")
	public Collection<Session> sessionList() {
		return sessionService.sessionList();
	}
}
