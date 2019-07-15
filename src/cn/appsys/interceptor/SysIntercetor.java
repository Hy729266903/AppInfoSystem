package cn.appsys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.appsys.pojo.BackendUser;

public class SysIntercetor extends HandlerInterceptorAdapter {
	private Logger logger = Logger.getLogger(SysIntercetor.class);
	public boolean perHandle(HttpServletRequest request,HttpServletResponse response,
			Object handler)throws Exception {
		logger.debug("SysInterceptor preHandle!");
		HttpSession session = request.getSession();
	 BackendUser  backendUser=(BackendUser) session.getAttribute("user");
	 if(null==backendUser) {
		 response.sendRedirect(request.getContextPath()+"/403.jsp");
		 return false;
	 }
		return true;		
	}
}
