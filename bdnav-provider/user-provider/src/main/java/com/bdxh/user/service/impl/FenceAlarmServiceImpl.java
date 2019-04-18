package com.bdxh.user.service.impl;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.user.dto.AddFenceAlarmDto;
import com.bdxh.user.dto.FenceAlarmQueryDto;
import com.bdxh.user.dto.UpdateFenceAlarmDto;
import com.bdxh.user.entity.FenceAlarm;
import com.bdxh.user.persistence.FenceAlarmMapper;
import com.bdxh.user.service.FenceAlarmService;
import com.bdxh.user.vo.FenceAlarmVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-04-17 17:29:24
*/
@Service
@Slf4j
public class FenceAlarmServiceImpl extends BaseService<FenceAlarm> implements FenceAlarmService {

	@Autowired
	private FenceAlarmMapper fenceAlarmMapper;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Override
	public PageInfo<FenceAlarmVo> getAllFenceAlarmInfos(FenceAlarmQueryDto fenceAlarmQueryDto) {
		PageHelper.startPage(fenceAlarmQueryDto.getPageNum(),fenceAlarmQueryDto.getPageSize());
		FenceAlarm fenceAlarm=BeanMapUtils.map(fenceAlarmQueryDto,FenceAlarm.class);
		List<FenceAlarmVo> fenceAlarmVoList=fenceAlarmMapper.getAllFenceAlarmInfos(fenceAlarm);
		PageInfo<FenceAlarmVo> pageInfoFamily = new PageInfo<>(fenceAlarmVoList);
		return pageInfoFamily;
	}
	@Override
	public FenceAlarmVo getFenceAlarmInfo(String schoolCode, String cardNumber, String id) {
		return fenceAlarmMapper.getFenceAlarmInfo(schoolCode,cardNumber,id);
	}
	@Override
	public void removeFenceAlarmInfo(String id, String schoolCode, String cardNumber) {
		fenceAlarmMapper.removeFenceAlarmInfo(id,schoolCode,cardNumber);
	}
	@Override
	public void batchRemoveFenceAlarmInfo(String ids, String schoolCodes, String cardNumbers) {
		String[] idAttr=ids.split(",");
		String[] schoolCodeAttr=schoolCodes.split(",");
		String[] cardNumberAttr=cardNumbers.split(",");
		List<Map<String,String>> list=new ArrayList<>();
		for (int i = 0; i < idAttr.length; i++) {
			Map<String,String> map=new HashMap<>();
			map.put("id",idAttr[i]);
			map.put("schoolCode",schoolCodeAttr[i]);
			map.put("cardNumber",cardNumberAttr[i]);
			list.add(map);
		}
		fenceAlarmMapper.batchRemoveFenceAlarmInfo(list);
	}
	@Override
	public void updateFenceAlarmInfo(UpdateFenceAlarmDto updateFenceAlarmDto) {
		FenceAlarm fenceAlarm=BeanMapUtils.map(updateFenceAlarmDto,FenceAlarm.class);
		fenceAlarmMapper.updateFenceAlarmInfo(fenceAlarm);
	}

	@Override
	public void insertFenceAlarmInfo(AddFenceAlarmDto addFenceAlarmDto) {
		addFenceAlarmDto.setId(snowflakeIdWorker.nextId());
		FenceAlarm fenceAlarm=BeanMapUtils.map(addFenceAlarmDto,FenceAlarm.class);
		fenceAlarmMapper.insert(fenceAlarm);
	}
}
