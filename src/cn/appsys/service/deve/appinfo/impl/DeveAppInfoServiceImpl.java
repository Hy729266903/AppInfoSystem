package cn.appsys.service.deve.appinfo.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;

import cn.appsys.dao.deve.appinfo.DeveAppInfoMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.service.deve.appinfo.DeveAppInfoService;

@Service("deveAppInfoService")
public class DeveAppInfoServiceImpl implements DeveAppInfoService {
	
	//注入DAO层接口
	@Resource
	private DeveAppInfoMapper deveAppInfoMapper;

	/**
	 * 查询总记录数
	 */
	@Override	
	public int queryTotalCount(String querySoftwareName, Integer queryStatus, Integer queryFlatformId,
			Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3) {
		return deveAppInfoMapper.queryTotalCount(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3);
	}

	/**
	 * 返回负责按条件并分页查询的App信息列表
	 */
	@Override
	public List<AppInfo> queryAppInfoPage(String querySoftwareName, Integer queryStatus, Integer queryFlatformId,
			Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3, Integer currPage,
			Integer pageSize) {
		//计算起始位置偏移量
		Integer from = (currPage - 1) * pageSize;
		return deveAppInfoMapper.queryAppInfoPage(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, from, pageSize);
	}
	
	/**
	 * 新增app信息
	 */
	@Override
	public int insertAppInfo(AppInfo appInfo) {
		return deveAppInfoMapper.insertAppInfo(appInfo);
	}

	/**
	 * 查询名字是否已存在 
	 */
	@Override
	public int apkexist(String APKName) {
		return deveAppInfoMapper.apkexist(APKName);
	}

	/**
	 * 根据id查询app信息
	 */
	@Override
	public AppInfo queryInfoByIdAndApk(Integer id) {
		return deveAppInfoMapper.queryInfoByIdAndApk(id);
	}

	/**
	 * 根据id修改APP图片
	 */
	@Override
	public int updateLogoPathById(Integer id,String logoPicPath) {
		return deveAppInfoMapper.updateLogoPathById(id,logoPicPath);
	}

	/**
	 * 修改APP信息
	 */
	@Override
	public int modifyAppInfo(AppInfo appInfo) {
		return deveAppInfoMapper.modifyAppInfo(appInfo);
	}

}
