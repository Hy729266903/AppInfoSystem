package cn.appsys.service.deve.datadictionary;

import java.util.List;

import cn.appsys.pojo.DataDictionary;

public interface DeveDictionaryService {
	/**
	 * 查询app状态
	 * @return
	 */
	List<DataDictionary> seleName();
	
	/**
	 * 所属平台
	 * @return
	 */
	List<DataDictionary> selePlatform();
}
