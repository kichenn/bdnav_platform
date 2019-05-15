package com.bdxh.appmarket.service.impl;


import com.bdxh.appmarket.entity.App;
import com.bdxh.appmarket.persistence.AppMapper;
import com.bdxh.appmarket.persistence.AppVersionMapper;
import com.bdxh.appmarket.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.appmarket.entity.AppVersion;
import java.util.List;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-05-14 12:15:05
*/
@Service
@Slf4j
public class AppVersionServiceImpl extends BaseService<AppVersion> implements AppVersionService {

	@Autowired
	private AppVersionMapper appVersionMapper;

	@Autowired
	private AppMapper appMapper;
	/*
	 *查询总条数
	 */
	@Override
	public Integer getAppVersionAllCount(){
		return appVersionMapper.getAppVersionAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelAppVersionInIds(List<Long> ids){
		return appVersionMapper.delAppVersionInIds(ids) > 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addAppVersionInfo(AppVersion appVersion) {
		appVersionMapper.insertSelective(appVersion);
		App app=new App();
		app.setId(appVersion.getAppId());
		app.setAppVersion(appVersion.getAppVersion());
		app.setAppPackage(appVersion.getApkName());
		app.setAppDesc(appVersion.getApkDesc());
		appMapper.updateByPrimaryKeySelective(app);
	}

	@Override
	public List<AppVersion> findAppVersion(Long appId) {
		return appVersionMapper.findAppVersion(appId);
	}

	@Override
	public AppVersion findNewAppVersion(Long appId) {
		return appVersionMapper.findNewAppVersion(appId);
	}
}
