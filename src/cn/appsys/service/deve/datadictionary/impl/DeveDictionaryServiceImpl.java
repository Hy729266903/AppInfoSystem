package cn.appsys.service.deve.datadictionary.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.deve.datadictionary.DeveDictionaryMapper;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.deve.datadictionary.DeveDictionaryService;

@Service("deveDictionaryService")
public class DeveDictionaryServiceImpl implements DeveDictionaryService {
	
	//定义接口
	@Resource
	private DeveDictionaryMapper deveDictionaryMapper;
	
	/**
	 * 查询app状态
	 */
	@Override
	public List<DataDictionary> seleName() {
		return deveDictionaryMapper.seleName();
	}

	/**
	 * 所属平台
	 */
	@Override
	public List<DataDictionary> selePlatform() {
		return deveDictionaryMapper.selePlatform();
	}

}
