package cn.appsys.controller.deve;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.deve.datadictionary.DeveDictionaryService;
import cn.appsys.service.deve.user.DeveLoginService;



@Controller
@RequestMapping("/deve")
public class DeveLoginController {
	
	//登录用户Service
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
			HttpSession session) {
			DevUser user = deveLoginService.login(devCode, devPassword);	//调用方法获得用户信息
			if (user != null) {
				session.setAttribute("user", user);
				
				return "developer/main";
			}else {
				model.addAttribute("error", "账号或密码错误！");
				return "devlogin";
			}
		
	}
	
	/**
	 * 注销
	 * @param session
	 * @return
	 */
	@RequestMapping("/logOut")
	public String logOut(HttpSession session) {
		session.removeAttribute("user");
		return "devlogin";
	}


}
