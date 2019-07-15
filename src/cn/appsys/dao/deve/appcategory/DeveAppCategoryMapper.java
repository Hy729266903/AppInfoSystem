package cn.appsys.dao.deve.appcategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;

public interface DeveAppCategoryMapper {
	/**
	 * 通过类别ID查询当前类别下的子集类别列表
	 * @param categoryId	类别ID
	 * @return	返回类别ID查询当前类别下的子集类别列表
	 */
	List<AppCategory> queryCategoryList(@Param("categoryId") Integer categoryId);
}
