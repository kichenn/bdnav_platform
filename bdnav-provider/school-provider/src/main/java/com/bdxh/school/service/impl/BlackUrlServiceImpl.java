package com.bdxh.school.service.impl;

import com.bdxh.school.service.BlackUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.school.entity.BlackUrl;
import com.bdxh.school.persistence.BlackUrlMapper;
import java.util.List;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-04-11 09:56:14
*/
@Service
@Slf4j
public class BlackUrlServiceImpl extends BaseService<BlackUrl> implements BlackUrlService {

	@Autowired
	private BlackUrlMapper blackUrlMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getBlackUrlAllCount(){
		return blackUrlMapper.getBlackUrlAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelBlackUrlInIds(List<Long> ids){
		return blackUrlMapper.delBlackUrlInIds(ids) > 0;
	}
}
