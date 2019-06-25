package com.bdxh.user.service.impl;

import com.bdxh.user.dto.FamilyBlackUrlQueryDto;
import com.bdxh.user.enums.FamliyBlackUrlStatusEnum;
import com.bdxh.user.service.FamilyBlackUrlService;
import com.bdxh.user.vo.FamilyBlackUrlVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.user.entity.FamilyBlackUrl;
import com.bdxh.user.persistence.FamilyBlackUrlMapper;

import java.util.ArrayList;
import java.util.List;

/**
* @Description: 家长端黑名单业务层实现
* @Author WanMing
* @Date 2019-06-25 10:17:12
*/
@Service
@Slf4j
public class FamilyBlackUrlServiceImpl extends BaseService<FamilyBlackUrl> implements FamilyBlackUrlService {

	@Autowired
	private FamilyBlackUrlMapper familyBlackUrlMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getFamilyBlackUrlAllCount(){
		return familyBlackUrlMapper.getFamilyBlackUrlAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelFamilyBlackUrlInIds(List<Long> ids){
		return familyBlackUrlMapper.delFamilyBlackUrlInIds(ids) > 0;
	}

	/**
	 * 添加家长黑名单
	 *
	 * @param familyBlackUrl
	 * @return
	 */
	@Override
	public Boolean addFamilyBlackUrl(FamilyBlackUrl familyBlackUrl) {
		return familyBlackUrlMapper.insertSelective(familyBlackUrl)>0;
	}

	/**
	 * 删除家长端黑名单
	 * @param schoolCode
	 * @param cardNumber
	 * @param id
	 * @return
	 */
	@Override
	public Boolean delFamilyBlackUrl(String schoolCode, String cardNumber, String id) {
		return familyBlackUrlMapper.delFamilyBlackUrl(schoolCode,cardNumber,id)>0;
	}

	/**
	 * 修改家长端黑名单
	 * @param familyBlackUrl
	 * @Author: WanMing
	 * @Date: 2019/6/25 11:41
	 */
	@Override
	public Boolean modifyFamilyBlackUrl(FamilyBlackUrl familyBlackUrl) {
		return familyBlackUrlMapper.updateByPrimaryKeySelective(familyBlackUrl)>0;
	}

	/**
	 * 分页查询家长端黑名单列表
	 * @param familyBlackUrlQueryDto
	 * @Author: WanMing
	 * @Date: 2019/6/25 11:58
	 */
	@Override
	public PageInfo<FamilyBlackUrlVo> findFamilyBlackUrlByCondition(FamilyBlackUrlQueryDto familyBlackUrlQueryDto) {
		PageHelper.startPage(familyBlackUrlQueryDto.getPageNum(),familyBlackUrlQueryDto.getPageSize());
		//数据拷贝
		FamilyBlackUrl familyBlackUrl = new FamilyBlackUrl();
		BeanUtils.copyProperties(familyBlackUrlQueryDto,familyBlackUrl);
		familyBlackUrl.setStatus(familyBlackUrlQueryDto.getStatus()==null?null:familyBlackUrlQueryDto.getStatus().getKey());
		List<FamilyBlackUrl> familyBlackUrls = familyBlackUrlMapper.findFamilyBlackUrlByCondition(familyBlackUrl);
		//数据转换
		List<FamilyBlackUrlVo> familyBlackUrlVos  = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(familyBlackUrls)){
			familyBlackUrls.forEach(blackUrl->{
				FamilyBlackUrlVo familyBlackUrlVo = new FamilyBlackUrlVo();
				BeanUtils.copyProperties(blackUrl, familyBlackUrlVo);
				familyBlackUrlVos.add(familyBlackUrlVo);
			});
		}
		return new PageInfo<>(familyBlackUrlVos);
	}

	/**
	 * 查询家长指定孩子的黑名单列表
	 * @param schoolCode
	 * @param cardNumber
	 * @param studentNumber
	 * @Author: WanMing
	 * @Date: 2019/6/25 14:37
	 */
	@Override
	public List<FamilyBlackUrlVo> findFamilyBlackUrlByStudent(String schoolCode, String cardNumber, String studentNumber) {
		List<FamilyBlackUrl> familyBlackUrls = familyBlackUrlMapper.findFamilyBlackUrlByStudent(schoolCode,cardNumber,studentNumber);
		//数据转换
		List<FamilyBlackUrlVo> familyBlackUrlVos  = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(familyBlackUrls)){
			familyBlackUrls.forEach(blackUrl->{
				FamilyBlackUrlVo familyBlackUrlVo = new FamilyBlackUrlVo();
				BeanUtils.copyProperties(blackUrl, familyBlackUrlVo);
				familyBlackUrlVos.add(familyBlackUrlVo);
			});
		}
		return familyBlackUrlVos;
	}
}
