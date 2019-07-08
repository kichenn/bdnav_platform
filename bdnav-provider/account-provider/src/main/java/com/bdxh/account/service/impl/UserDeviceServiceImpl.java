package com.bdxh.account.service.impl;

import com.bdxh.account.dto.QueryUserDeviceDto;
import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.persistence.UserDeviceMapper;
import com.bdxh.account.service.UserDeviceService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-05-24 14:35:27
*/
@Service
@Slf4j
public class UserDeviceServiceImpl extends BaseService<UserDevice> implements UserDeviceService {

	@Autowired
	private UserDeviceMapper userDeviceMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getUserDeviceAllCount(){
		return userDeviceMapper.getUserDeviceAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelUserDeviceInIds(List<Long> ids){
		return userDeviceMapper.delUserDeviceInIds(ids) > 0;
	}

	@Override
	public List<String> getUserDeviceAll(String schoolCode, String ids) {
		String [] rightControls=ids.split(",");
		List<String> udds=new ArrayList<>();
		for (int i = 0; i <rightControls.length ; i++) {
			List<UserDevice> uds=userDeviceMapper.getUserDeviceAll(schoolCode,Long.valueOf(rightControls[i]));
			if (uds.size()!=0&&uds!=null){
			for (int j = 0; j < uds.size(); j++) {
				udds.add(uds.get(j).getClientId());
			   }
			}
		}
		return udds;
	}

	/*@Override
	public List<UserDevice> getUserDeviceAll(String schoolcode, Long groupId) {
		return userDeviceMapper.getUserDeviceAll(schoolcode,groupId);
	}*/

	@Override
	public UserDevice findUserDeviceByCodeOrCard(String schoolCode, String cardNumber) {
		return userDeviceMapper.findUserDeviceByCodeOrCard(schoolCode,cardNumber);
	}

	@Override
	public List<UserDevice> findUserDeviceList(String schoolCode) {
		return userDeviceMapper.findUserDeviceList(schoolCode);
	}

	/**
	 * 根据条件查找用户设备信息
	 *
	 * @param queryUserDeviceDto
	 * @Author: WanMing
	 * @Date: 2019/7/5 10:16
	 */
	@Override
	public PageInfo<UserDevice> findUserDeviceByCondition(QueryUserDeviceDto queryUserDeviceDto) {
		UserDevice userDevice = new UserDevice();
		BeanUtils.copyProperties(queryUserDeviceDto,userDevice);
		PageHelper.startPage(queryUserDeviceDto.getPageNum(), queryUserDeviceDto.getPageSize());
		List<UserDevice> userDevices = userDeviceMapper.findUserDeviceByCondition(userDevice);
		PageInfo<UserDevice> pageInfo = new PageInfo<>(userDevices);
		return pageInfo;
	}
}
