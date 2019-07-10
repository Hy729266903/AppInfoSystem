package cn.appsys.controller.deve;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/info")
public class DeveAppController {
	
	/**
	 * 跳转到app信息管理维护
	 * @return
	 */
	@RequestMapping("/appInfoList")
	public String appInfoList() {
		return "developer/appinfolist";
	}
	
	/**
	 * 新增app基础信息
	 * @return
	 */
	@RequestMapping("/appInfoAdd")
	public String appinfoAdd() {
		return "developer/appinfoadd";
	}
}
