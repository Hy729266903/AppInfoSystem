package cn.appsys.controller.deve;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.deve.user.DeveLoginService;



@Controller
@RequestMapping("/deve")
public class DeveLoginController {
	
	@Resource(name="deveLoginService")
	private DeveLoginService deveLoginService;
	
	/**
	 * 开发者登录跳转
	 * @return
	 */
	@RequestMapping("/toLogin")
	public String toLogin() {
		return "devlogin";
	}
	
	
	@RequestMapping("/doLogin")
	public String doLogin(
			@RequestParam("devCode") String devCode,
			@RequestParam("devPassword") String devPassword,
			HttpServletRequest request) {
			DevUser user = deveLoginService.login(devCode, devPassword);
		
			return "developer/main";
		
		
	}

}
