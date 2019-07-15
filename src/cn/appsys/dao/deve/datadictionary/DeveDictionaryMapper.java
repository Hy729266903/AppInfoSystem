package cn.appsys.dao.deve.datadictionary;

import java.util.List;

import cn.appsys.pojo.DataDictionary;

public interface DeveDictionaryMapper {
	/**
	 * 查询APP状态
	 * @return
	 */
	List<DataDictionary> seleName();
	
	/**
	 * 查询平台
	 * @return
	 */
	List<DataDictionary> selePlatform();
}
