package cn.appsys.dao.deve.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;

import cn.appsys.pojo.AppInfo;

public interface DeveAppInfoMapper {
	
	
	
	/**
	 * 修改APP信息
	 * @param appInfo
	 * @return
	 */
	int modifyAppInfo(AppInfo appInfo);
	
	/**
	 * 根据id修改app信息中图片路径实现删除
	 * @param id
	 * @return
	 */
	int updateLogoPathById(@Param("id")Integer id,@Param("logoPicPath")String logoPicPath);
	
	/**
	 * 根据id查询app信息
	 * @param id
	 * @return
	 */
	AppInfo queryInfoByIdAndApk(@Param("id")Integer id);
	
	
	/**
	 * 新增app信息
	 * @param appInfo
	 * @return
	 */
	int insertAppInfo(AppInfo appInfo);
	
	/**
	 * 查询名字是否存在
	 * @param APKName
	 * @return
	 */
	int apkexist(@Param("APKName") String APKName);
	
	
	
	/**
	 * 负责按条件并分页查询App信息的总记录数
	 * @param querySoftwareName		软件名称
	 * @param queryStatus			软件状态
	 * @param queryFlatformId		所属平台
	 * @param queryCategoryLevel1	一级分类ID
	 * @param queryCategoryLevel2	二级分类ID
	 * @param queryCategoryLevel3	三级分类ID
	 * @return 返回总记录数
	 */
	int queryTotalCount(
			@Param("querySoftwareName") String querySoftwareName,
			@Param("queryStatus") Integer queryStatus,
			@Param("queryFlatformId") Integer queryFlatformId,
			@Param("queryCategoryLevel1") Integer queryCategoryLevel1,
			@Param("queryCategoryLevel2") Integer queryCategoryLevel2,
			@Param("queryCategoryLevel3") Integer queryCategoryLevel3);
			
	
	/**
	 * 负责按条件并分页查询App信息列表信息
	 * @param querySoftwareName 	软件名称
	 * @param queryStatus			软件状态
	 * @param queryFlatformId		所属平台
	 * @param queryCategoryLevel1	一级分类ID
	 * @param queryCategoryLevel2	二级分类ID
	 * @param queryCategoryLevel3	三级分类ID
	 * @param from		起始位置偏移量
	 * @param pageSize	每页显示数据行数
	 * @return	返回负责按条件并分页查询的App信息列表
	 */
	List<AppInfo> queryAppInfoPage(
			@Param("querySoftwareName") String querySoftwareName,
			@Param("queryStatus") Integer queryStatus,
			@Param("queryFlatformId") Integer queryFlatformId,
			@Param("queryCategoryLevel1") Integer queryCategoryLevel1,
			@Param("queryCategoryLevel2") Integer queryCategoryLevel2,
			@Param("queryCategoryLevel3") Integer queryCategoryLevel3,
			@Param("from") Integer from,@Param("pageSize") Integer pageSize
			
			);
	
}
