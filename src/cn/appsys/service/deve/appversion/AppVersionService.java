package cn.appsys.service.deve.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface AppVersionService {
	/**
	 * 根据Appinfo的id查询所有版本列表信息
	 * @param id	App的id
	 * @return		返回版本列表
	 */
	List<AppVersion> getAppVersionById(@Param("id")Integer id);
	
	/**
	 * 新增
	 * @param appVersion
	 * @return
	 */
	int insertAppVersion(AppVersion appVersion);
}
