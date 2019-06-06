package com.bdxh.appmarket.service.impl;

import com.bdxh.appmarket.entity.SystemApp;
import com.bdxh.appmarket.persistence.SystemAppMapper;
import com.bdxh.appmarket.service.SystemAppService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;

/**
* @Description: 业务层实现
* @Date 2019-06-05 15:05:12
*/
@Service
@Slf4j
public class SystemAppServiceImpl extends BaseService<SystemApp> implements SystemAppService {

	@Autowired
	private SystemAppMapper systemAppMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getSystemAppAllCount(){
		return systemAppMapper.getSystemAppAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelSystemAppInIds(List<Long> ids){
		return systemAppMapper.delSystemAppInIds(ids) > 0;
	}

	@Override
	public PageInfo<SystemApp> findAppControl(Map<String, Object> param, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<SystemApp> systemApps= systemAppMapper.findAppControl(param);
		return new PageInfo(systemApps);
	}

	@Override
	public SystemApp versionUpdating() {
		return systemAppMapper.versionUpdating();
	}


}
