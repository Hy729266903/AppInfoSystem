package cn.appsys.service.deve.appcategory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.deve.appcategory.DeveAppCategoryMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.service.deve.appcategory.DeveAppCategoryService;
@Service("deveAppCategoryService")
public class DeveAppCategoryServiceImpl implements DeveAppCategoryService {

	@Resource
	private DeveAppCategoryMapper deveAppCategoryMapper;
	
	/**
	 * 通过类别ID查询当前类别下的子集类别列表
	 * 默认将一级分类中的值查询出来
	 */
	@Override
	public List<AppCategory> queryCategoryList(Integer categoryId) {
		return deveAppCategoryMapper.queryCategoryList(categoryId);
	}

}
