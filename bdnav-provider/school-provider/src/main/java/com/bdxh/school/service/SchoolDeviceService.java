package com.bdxh.school.service;

import com.bdxh.common.support.IService;
import com.bdxh.school.dto.SchoolDeviceQueryDto;
import com.bdxh.school.entity.SchoolDevice;
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
}
