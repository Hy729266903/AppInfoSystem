package cn.appsys.service.deve.user;



import cn.appsys.pojo.DevUser;

public interface DeveLoginService {
	/**
	 * 登录
	 * @param devCode
	 * @param devPassword
	 * @return
	 */
	DevUser login(String devCode,String devPassword);
}
