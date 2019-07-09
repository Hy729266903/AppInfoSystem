package cn.appsys.controller.deve;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class DeveLoginController {
	
	@RequestMapping("/toLogin")
	public String login() {
		return "devlogin";
	}
}
