package cn.appsys.dao.deve.user;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DevUser;

public interface DeveLoginMapper {
	/**
	 * 登录
	 * @param devCode
	 * @param devPassword
	 * @return
	 */
	DevUser login(@Param("devCode") String devCode,@Param("devPassword") String devPassword);
}
