package com.bdxh.school.service;

import com.bdxh.common.support.IService;
import com.bdxh.school.dto.SchoolDeviceAndChargeDeptQueryDto;
import com.bdxh.school.dto.SchoolDeviceQueryDto;
import com.bdxh.school.dto.SchoolPosDeviceQueryDto;
import com.bdxh.school.entity.SchoolDevice;
import com.bdxh.school.vo.ChargeDeptAndDeviceVo;
import com.bdxh.school.vo.SchoolDeviceShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 业务层接口
 * @Author Kang
 * @Date 2019-03-27 12:21:12
 */
@Service
public interface SchoolDeviceService extends IService<SchoolDevice> {

    /**
     * 查询所有数量
     */
    Integer getSchoolDeviceAllCount();

    /**
     * 批量删除方法
     */
    Boolean batchDelSchoolDeviceInIds(List<Long> id);

    /**
     * 门禁信息根据条件分页查询
     */
    PageInfo<SchoolDeviceShowVo> findSchoolDeviceInConditionPage(SchoolDeviceQueryDto schoolDeviceQueryDto);

    /**
     * 设备类型，设备编码查询设备信息
     */
    SchoolDevice findSchoolDeviceByIdOnModel(String deviceId, String deviceModel);

    /**
     * 查询单个学校下的pos机列表
     */
    PageInfo<SchoolDevice> findSchoolPosDeviceBySchool(SchoolPosDeviceQueryDto devicePosQueryDto);

    /**
     *  根据收费部门id查询下面的消费机列表
     * @param deptQueryDto
     * @return
     */
    PageInfo<SchoolDevice> querySchoolPosDeviceByChargeDept(SchoolDeviceAndChargeDeptQueryDto deptQueryDto);

    /**
     * 查询收费部门和pos机的关系列表
     * @param schoolCode
     * @return
     */
    List<ChargeDeptAndDeviceVo> findChargeDeptAndDeviceRelation(String schoolCode,Byte chargeDeptType);
}
