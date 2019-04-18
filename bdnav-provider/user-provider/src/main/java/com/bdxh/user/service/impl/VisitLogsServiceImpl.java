package com.bdxh.user.service.impl;

import com.bdxh.user.dto.UpdateVisitLogsDto;
import com.bdxh.user.dto.VisitLogsQueryDto;
import com.bdxh.user.entity.VisitLogs;
import com.bdxh.user.persistence.VisitLogsMapper;
import com.bdxh.user.service.VisitLogsService;
import com.bdxh.user.vo.VisitLogsVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-04-17 17:29:24
*/
@Service
@Slf4j
public class VisitLogsServiceImpl extends BaseService<VisitLogs> implements VisitLogsService {

	@Autowired
	private VisitLogsMapper visitLogsMapper;


	@Override
	public PageInfo<VisitLogsVo> getVisitLogsInfos(VisitLogsQueryDto visitLogsQueryDto) {
		return null;
	}

	@Override
	public VisitLogsVo getVisitLogsInfo(String schoolCode, String cardNumber, String id) {
		return null;
	}

	@Override
	public void updateVisitLogsInfo(UpdateVisitLogsDto updateVisitLogsDto) {

	}

	@Override
	public void removeVisitLogsInfo(String schoolCode, String cardNumber, String id) {

	}

	@Override
	public void batchRemoveVisitLogsInfo(String schoolCodes, String cardNumbers, String ids) {

	}
}
