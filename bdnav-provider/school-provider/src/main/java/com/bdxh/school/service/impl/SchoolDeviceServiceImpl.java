package com.bdxh.school.service.impl;

import com.bdxh.school.dto.SchoolDeviceAndChargeDeptQueryDto;
import com.bdxh.school.dto.SchoolDeviceQueryDto;
import com.bdxh.school.dto.SchoolPosDeviceQueryDto;
import com.bdxh.school.entity.GroupPermission;
import com.bdxh.school.enums.DeviceStatusEnum;
import com.bdxh.school.service.SchoolDeviceService;
import com.bdxh.school.vo.SchoolDeviceShowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.school.entity.SchoolDevice;
import com.bdxh.school.persistence.SchoolDeviceMapper;

import java.util.List;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-03-27 12:26:55
 */
@Service
@Slf4j
public class SchoolDeviceServiceImpl extends BaseService<SchoolDevice> implements SchoolDeviceService {

    @Autowired
    private SchoolDeviceMapper schoolDeviceMapper;

    /*
     *查询总条数
     */
    @Override
    public Integer getSchoolDeviceAllCount() {
        return schoolDeviceMapper.getSchoolDeviceAllCount();
    }

    /*
     *批量删除方法
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelSchoolDeviceInIds(List<Long> ids) {
        return schoolDeviceMapper.delSchoolDeviceInIds(ids) > 0;
    }

    /**
     * 门禁信息根据条件分页查询
     */
    @Override
    public PageInfo<SchoolDeviceShowVo> findSchoolDeviceInConditionPage(SchoolDeviceQueryDto schoolDeviceQueryDto) {
        Page page = PageHelper.startPage(schoolDeviceQueryDto.getPageNum(), schoolDeviceQueryDto.getPageSize());
        SchoolDevice schoolDevice = new SchoolDevice();
        BeanUtils.copyProperties(schoolDeviceQueryDto, schoolDevice);
        if (schoolDeviceQueryDto.getDeviceStatusEnum() != null) {
            //设置类型
            schoolDevice.setDeviceStatus(schoolDeviceQueryDto.getDeviceStatusEnum().getKey());
        }
        PageInfo<SchoolDeviceShowVo> pageInfo = new PageInfo(schoolDeviceMapper.findSchoolDeviceInConditionPage(schoolDevice));
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    /**
     * 设备类型，设备编码查询设备信息
     */
    @Override
    public SchoolDevice findSchoolDeviceByIdOnModel(String deviceId, String deviceModel) {
        return schoolDeviceMapper.findSchoolDeviceByIdOnModel(deviceId, deviceModel);
    }

    /**
     * 查询单个学校下的设备的列表
     *
     * @param schoolPosDeviceQueryDto
     */
    @Override
    public PageInfo<SchoolDevice> findSchoolPosDeviceBySchool(SchoolPosDeviceQueryDto schoolPosDeviceQueryDto) {
        PageHelper.startPage(schoolPosDeviceQueryDto.getPageNum(),schoolPosDeviceQueryDto.getPageSize());
        List<SchoolDevice> schoolDevices = schoolDeviceMapper.findSchoolPosDeviceBySchool(schoolPosDeviceQueryDto);
        return new PageInfo<>(schoolDevices);
    }

    /**
     * 根据收费部门id查询下面的消费机列表
     *
     * @param deptQueryDto
     * @return
     */
    @Override
    public PageInfo<SchoolDevice> querySchoolPosDeviceByChargeDept(SchoolDeviceAndChargeDeptQueryDto deptQueryDto) {
        PageHelper.startPage(deptQueryDto.getPageNum(),deptQueryDto.getPageSize());
        List<SchoolDevice> schoolDevices = schoolDeviceMapper.querySchoolPosDeviceByChargeDept(deptQueryDto);

        return new PageInfo<SchoolDevice>(schoolDevices);
    }
}
