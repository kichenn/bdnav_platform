package com.bdxh.system.service.impl;


import com.bdxh.system.dto.AddControlConfig;
import com.bdxh.system.dto.ModifyControlConfig;
import com.bdxh.system.entity.ControlConfig;
import com.bdxh.system.persistence.ControlConfigMapper;
import com.bdxh.system.service.ControlConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-05-30 09:36:37
*/
@Service
@Slf4j
public class ControlConfigServiceImpl extends BaseService<ControlConfig> implements ControlConfigService {

	@Autowired
	private ControlConfigMapper controlConfigMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getControlConfigAllCount(){
		return controlConfigMapper.getControlConfigAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelControlConfigInIds(List<Long> ids){
		return controlConfigMapper.delControlConfigInIds(ids) > 0;
	}

	@Override
	public List<ControlConfig> findAppType(Byte appType) {
		return controlConfigMapper.findAppType(appType);
	}

	@Override
	public PageInfo<ControlConfig> findListPage(Map<String, Object> param, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<ControlConfig> appLogs = controlConfigMapper.getByCondition(param);
		PageInfo<ControlConfig> pageInfo=new PageInfo<>(appLogs);
		return pageInfo;
	}

}
