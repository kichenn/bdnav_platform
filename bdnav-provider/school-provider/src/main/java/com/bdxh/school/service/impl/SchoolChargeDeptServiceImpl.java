package com.bdxh.school.service.impl;

import com.bdxh.school.dto.QuerySchoolChargeDeptDto;
import com.bdxh.school.persistence.SchoolPosDeviceChargeMapper;
import com.bdxh.school.service.SchoolChargeDeptService;
import com.bdxh.school.vo.SchoolChargeDeptVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.school.entity.SchoolChargeDept;
import com.bdxh.school.persistence.SchoolChargeDeptMapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @Description: 学校收费部门的业务层实现
* @Author WanMing
* @Date 2019-07-10 18:12:31
*/
@Service
@Slf4j
public class SchoolChargeDeptServiceImpl extends BaseService<SchoolChargeDept> implements SchoolChargeDeptService {

	@Autowired
	private SchoolChargeDeptMapper schoolChargeDeptMapper;

	@Autowired
	private SchoolPosDeviceChargeMapper schoolPosDeviceChargeMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getSchoolChargeDeptAllCount(){
		return schoolChargeDeptMapper.getSchoolChargeDeptAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelSchoolChargeDeptInIds(List<Long> ids){
		return schoolChargeDeptMapper.delSchoolChargeDeptInIds(ids) > 0;
	}

	/**
	 * 分页+条件查询
	 *
	 * @param querySchoolChargeDeptDto
	 * @Author: WanMing
	 * @Date: 2019/7/10 19:04
	 */
	@Override
	public PageInfo<SchoolChargeDeptVo> findSchoolChargeDeptByCondition(QuerySchoolChargeDeptDto querySchoolChargeDeptDto) {
		PageHelper.startPage(querySchoolChargeDeptDto.getPageNum(),querySchoolChargeDeptDto.getPageSize());
		List<SchoolChargeDeptVo> schoolChargeDeptVos =  schoolChargeDeptMapper.findSchoolChargeDeptByCondition(querySchoolChargeDeptDto);
		return new PageInfo<>(schoolChargeDeptVos);
	}

    /**
     * 根据schoolCode查询学校的收费部门
     *
     * @param schoolCode
     * @Author: WanMing
     * @Date: 2019/7/11 09:44
     */
    @Override
    public List<SchoolChargeDeptVo> findSchoolChargeDeptBySchool(String schoolCode,Byte chargeDeptType) {
        return schoolChargeDeptMapper.findSchoolChargeDeptBySchool(schoolCode,chargeDeptType);
    }

	/**
	 * 分页+查询学校消费部门信息和POS机的数量
	 *
	 * @param querySchoolChargeDeptDto
	 * @Author: WanMing
	 * @Date: 2019/7/11 19:04
	 */
	@Override
	public PageInfo<SchoolChargeDeptVo> querySchoolChargeDeptAndPosNum(QuerySchoolChargeDeptDto querySchoolChargeDeptDto) {
		PageHelper.startPage(querySchoolChargeDeptDto.getPageNum(),querySchoolChargeDeptDto.getPageSize());
		List<SchoolChargeDeptVo> schoolChargeDeptVos =  schoolChargeDeptMapper.querySchoolChargeDeptAndPosNum(querySchoolChargeDeptDto);
		return new PageInfo<>(schoolChargeDeptVos);
	}
}
