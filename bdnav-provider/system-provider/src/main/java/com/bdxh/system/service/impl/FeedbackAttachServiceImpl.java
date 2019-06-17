package com.bdxh.system.service.impl;


import com.bdxh.common.support.BaseService;
import com.bdxh.system.entity.FeedbackAttach;
import com.bdxh.system.persistence.FeedbackAttachMapper;
import com.bdxh.system.service.FeedbackAttachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @Description: 业务层实现
* @Author wanMing
* @Date 2019-06-13 11:43:51
*/
@Service
@Slf4j
public class FeedbackAttachServiceImpl extends BaseService<FeedbackAttach> implements FeedbackAttachService {

	@Autowired
	private FeedbackAttachMapper feedbackAttachMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getFeedbackAttachAllCount(){
		return feedbackAttachMapper.getFeedbackAttachAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelFeedbackAttachInIds(List<Long> ids){
		return feedbackAttachMapper.delFeedbackAttachInIds(ids) > 0;
	}
}
