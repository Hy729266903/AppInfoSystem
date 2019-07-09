package cn.appsys.service.deve.user.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.deve.user.DeveLoginMapper;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.deve.user.DeveLoginService;
/**
 * 实现接口类
 * @author A
 *
 */
@Service("deveLoginService")
public class DeveLoginServiceImpl implements DeveLoginService {
	
	/**
	 * 定义接口
	 */
	@Resource(name="deveLoginMapper")
	private DeveLoginMapper deveLoginMapper;

	/**
	 * 实现登录
	 */
	@Override
	public DevUser login(String devCode, String devPassword) {
		return deveLoginMapper.login(devCode, devPassword);
	}

}
