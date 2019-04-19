package com.bdxh.user.service.impl;

import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.user.dto.AddVisitLogsDto;
import com.bdxh.user.dto.UpdateVisitLogsDto;
import com.bdxh.user.dto.VisitLogsQueryDto;
import com.bdxh.user.entity.VisitLogs;
import com.bdxh.user.persistence.mongoPersistence.VisitLogsMongoMapper;
import com.bdxh.user.service.VisitLogsService;
import com.bdxh.user.vo.VisitLogsVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import lombok.extern.slf4j.Slf4j;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-04-17 17:29:24
*/
@Service
@Slf4j
public class VisitLogsServiceImpl extends BaseService<VisitLogs> implements VisitLogsService {

	@Autowired
	private VisitLogsMongoMapper visitLogsMongoMapper;


	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;



	@Override
	public PageInfo<VisitLogsVo> getVisitLogsInfos(VisitLogsQueryDto visitLogsQueryDto) {
		return visitLogsMongoMapper.getVisitLogsInfos(visitLogsQueryDto);
	}

	@Override
	public VisitLogsVo getVisitLogsInfo(String schoolCode, String cardNumber, String id) {
		return visitLogsMongoMapper.getVisitLogsInfo(schoolCode,cardNumber,id);
	}

	@Override
	public void updateVisitLogsInfo(UpdateVisitLogsDto updateVisitLogsDto) {
		visitLogsMongoMapper.updateVisitLogsInfo(updateVisitLogsDto);
	}

	@Override
	public void removeVisitLogsInfo(String schoolCode, String cardNumber, String id) {
		visitLogsMongoMapper.removeVisitLogsInfo(schoolCode,cardNumber,id);
	}

	@Override
	public void batchRemoveVisitLogsInfo(String schoolCodes, String cardNumbers, String ids) {
		visitLogsMongoMapper.batchRemoveVisitLogsInfo(schoolCodes,cardNumbers,ids);
	}

	@Override
	public void insertVisitLogsInfo(AddVisitLogsDto addVisitLogsDto) {
		addVisitLogsDto.setId(snowflakeIdWorker.nextId());
		visitLogsMongoMapper.insertVisitLogsInfo(addVisitLogsDto);
	}
}
