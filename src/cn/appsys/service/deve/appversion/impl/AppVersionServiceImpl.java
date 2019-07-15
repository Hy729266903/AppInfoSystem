package cn.appsys.service.deve.appversion.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import cn.appsys.dao.deve.appversion.AppVersionMapper;
import cn.appsys.pojo.AppVersion;
import cn.appsys.service.deve.appversion.AppVersionService;

@Controller
@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {
	
	//注入接口
	@Resource
	private AppVersionMapper appVersionMapper;
	
	/**
	 * 根据App的id查询版本列表信息
	 */
	@Override
	public List<AppVersion> getAppVersionById(Integer id) {
		return appVersionMapper.getAppVersionById(id);
	}

	/**
	 * 保存信息修改
	 */
	@Override
	public int insertAppVersion(AppVersion appVersion) {
		return appVersionMapper.insertAppVersion(appVersion);
	}
	
	/**
	 * 通过app版本id查询app版本的详情对象信息
	 */
	@Override
	public AppVersion getAppVersionByVid(Integer vid) {
		return appVersionMapper.getAppVersionByVid(vid);
	}

	/**
	 * 删除apk文件
	 */
	@Override
	public int deleteApkFile(Integer id) {
		return appVersionMapper.deleteApkFile(id);
	}

	/**
	 * 保存修改
	 */
	@Override
	public int modifyAppVersion(AppVersion appVersion) {
		return appVersionMapper.modifyAppVersion(appVersion);
	}

	/**
	 * 删除
	 */
	@Override
	public int deleteAppInfoById(Integer delId) {
		return appVersionMapper.deleteAppInfoById(delId);
	}

}
