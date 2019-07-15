package cn.appsys.service.deve.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface AppVersionService {
	
	/**
	 * 删除
	 * @param delId
	 * @return
	 */
	int deleteAppInfoById(@Param(value="id")Integer delId);
	
	/**
	 * 保存修改
	 * @param appVersion
	 * @return
	 */
	int modifyAppVersion(AppVersion appVersion);
	
	/**
	 * @param id app版本id
	 * @return 受影响的行数
	 */
	public int deleteApkFile(@Param("id")Integer id);
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
	
	/**
	 * 通过app版本id查询app版本的详情对象信息
	 * @param vid 
	 * @return
	 */
	AppVersion getAppVersionByVid(@Param("vid")Integer vid);
}
