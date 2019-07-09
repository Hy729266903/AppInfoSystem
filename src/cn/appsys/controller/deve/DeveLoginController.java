package cn.appsys.controller.deve;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	/**
	 * 登录方法
	 * @param devCode
	 * @param devPassword
	 * @param request
	 * @return
	 */
	@RequestMapping("/doLogin")
	public String doLogin(Model model,
			@RequestParam("devCode") String devCode,
			@RequestParam("devPassword") String devPassword,
			HttpServletRequest request) {
			DevUser user = deveLoginService.login(devCode, devPassword);
			if (user != null) {
				request.setAttribute("user", user);
				return "developer/main";
			}else {
				model.addAttribute("error", "账号或密码错误！");
				return "devlogin";
			}
		
			
		
		
	}

}
