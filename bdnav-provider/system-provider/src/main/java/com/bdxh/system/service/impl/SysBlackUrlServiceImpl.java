package com.bdxh.system.service.impl;

import com.bdxh.system.SysBlackUrlQueryDto;
import com.bdxh.system.service.SysBlackUrlService;
import com.bdxh.system.vo.SysBlackUrlVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.system.entity.SysBlackUrl;
import com.bdxh.system.persistence.SysBlackUrlMapper;

import java.util.ArrayList;
import java.util.List;

/**
* @Description: 业务层实现
* @Author wanMing
* @Date 2019-06-20 11:32:50
*/
@Service
@Slf4j
public class SysBlackUrlServiceImpl extends BaseService<SysBlackUrl> implements SysBlackUrlService {

	@Autowired
	private SysBlackUrlMapper sysBlackUrlMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getSysBlackUrlAllCount(){
		return sysBlackUrlMapper.getSysBlackUrlAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelSysBlackUrlInIds(List<Long> ids){
		return sysBlackUrlMapper.delSysBlackUrlInIds(ids) > 0;
	}

	/**
	 * 分页+添加查询系统黑名单即病毒库
	 *
	 * @param sysBlackUrlQueryDto
	 * @Author: WanMing
	 * @Date: 2019/6/20 12:16
	 */
	@Override
	public PageInfo<SysBlackUrlVo> findSysBlackUrlByCondition(SysBlackUrlQueryDto sysBlackUrlQueryDto) {
		PageHelper.startPage(sysBlackUrlQueryDto.getPageNum(),sysBlackUrlQueryDto.getPageSize());
		SysBlackUrl sysBlackUrl = new SysBlackUrl();
		BeanUtils.copyProperties(sysBlackUrlQueryDto,sysBlackUrl);
		List<SysBlackUrl> sysBlackUrls = sysBlackUrlMapper.findSysBlackUrlByCondition(sysBlackUrl);
		List<SysBlackUrlVo> sysBlackUrlVos = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(sysBlackUrls)){
			sysBlackUrls.forEach(blackUrl->{
				SysBlackUrlVo sysBlackUrlVo = new SysBlackUrlVo();
				BeanUtils.copyProperties(blackUrl, sysBlackUrlVo);
				sysBlackUrlVos.add(sysBlackUrlVo);
			});
		}
		return new PageInfo(sysBlackUrlVos);
	}

	/**
	 * 判断url是否在黑名单库
	 *
	 * @param url
	 * @Author: WanMing
	 * @Date: 2019/6/20 15:46
	 */
	@Override
	public Boolean querySysBlackUrlByUrl(String url) {
		return sysBlackUrlMapper.querySysBlackUrlByUrl(url)==null?Boolean.FALSE:Boolean.TRUE;
	}
}
