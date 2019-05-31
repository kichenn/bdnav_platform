package com.bdxh.school.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.school.dto.SchoolServicePermitQueryDto;
import com.bdxh.school.entity.SchoolServicePermit;
import com.bdxh.school.persistence.SchoolServicePermitMapper;
import com.bdxh.school.service.SchoolServicePermitService;
import com.bdxh.school.vo.SchoolServicePermitShowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
* @Description: 学校服务许可业务层实现
* @Author wanMing
* @Date 2019-05-28 10:24:19
*/
@Service
@Slf4j
public class SchoolServicePermitServiceImpl extends BaseService<SchoolServicePermit> implements SchoolServicePermitService {

	@Autowired
	private SchoolServicePermitMapper schoolServicePermitMapper;

	/**
	 *	查询数据总记录数
	 * @Author: WanMing
	 * @Date: 2019/5/28 10:59
	 */
	@Override
	public Integer getSchoolServicePermitAllCount(){
		return schoolServicePermitMapper.getSchoolServicePermitAllCount();
	}

	/**
	 * 	根据id批量删除服务许可记录
	 * @Author: WanMing
	 * @Date: 2019/5/28 11:00
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelSchoolServicePermitInIds(List<Long> ids){
		return schoolServicePermitMapper.delSchoolServicePermitInIds(ids) > 0;
	}



	/**
	 * 分页+条件查询 学校的服务许可权限
	 * @Author: WanMing
	 * @Date: 2019/5/28 16:21
	 */
	@Override
	public PageInfo<SchoolServicePermitShowVo> findSchoolServicePermitInConditionPage(SchoolServicePermitQueryDto schoolServicePermitQueryDto) {
		Page page = PageHelper.startPage(schoolServicePermitQueryDto.getPageNum(), schoolServicePermitQueryDto.getPageSize());
		//对象拷贝
		SchoolServicePermit schoolServicePermit = new SchoolServicePermit();
		BeanUtils.copyProperties(schoolServicePermitQueryDto, schoolServicePermit);
		//类型设置
		schoolServicePermit.setStatus(schoolServicePermitQueryDto.getStatus()!=null?schoolServicePermitQueryDto.getStatus().getKey():null);
		List<SchoolServicePermit>  SchoolServicePermits = schoolServicePermitMapper.findSchoolServicePermitInConditionPage(schoolServicePermit);
		List<SchoolServicePermitShowVo> permitShowVos = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(SchoolServicePermits)){
			SchoolServicePermits.stream().forEach(item->{
				SchoolServicePermitShowVo permitShowVo = new SchoolServicePermitShowVo();
				BeanUtils.copyProperties(item, permitShowVo);
				permitShowVos.add(permitShowVo);
			});
		}
		PageInfo<SchoolServicePermitShowVo> pageInfo = new PageInfo<>(permitShowVos);
		pageInfo.setTotal(page.getTotal());
		return pageInfo;
	}

	/**
	 * 根据id查询学校的服务许可权限
	 * @Author: WanMing
	 * @Date: 2019/5/28 20:21
	 */
	@Override
	public SchoolServicePermit findSchoolServicePermitById(Long id) {
		SchoolServicePermit schoolServicePermit = schoolServicePermitMapper.selectByPrimaryKey(id);
		return schoolServicePermit;
	}

	/**
	 * 根据学校编码查询学校的服务许可权限记录数
	 * @Author: WanMing
	 * @Date: 2019/5/30 10:17
	 */
	@Override
	public SchoolServicePermit querySchoolServicePermitCountBySchoolCode(String schoolCode) {
		return schoolServicePermitMapper.querySchoolServicePermitCountBySchoolCode(schoolCode);
	}
}
