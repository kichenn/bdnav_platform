package com.bdxh.school.persistence;

import java.util.List;

import com.bdxh.school.vo.SchoolDeviceShowVo;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.bdxh.school.entity.SchoolDevice;


/**
 * @Description: Mapper
 * @Author Kang
 * @Date 2019-03-27 12:09:22
 */
@Repository
public interface SchoolDeviceMapper extends Mapper<SchoolDevice> {
    /**
     * 查询总条数
     */
    Integer getSchoolDeviceAllCount();

    /**
     * 批量删除方法
     */
    Integer delSchoolDeviceInIds(@Param("ids") List<Long> ids);

    /**
     * 门禁信息根据条件分页查询
     *
     * @return
     */
    List<SchoolDeviceShowVo> findSchoolDeviceInConditionPage(@Param("device") SchoolDevice schoolDevice);

    /**
     * 设备类型，设备编码查询设备信息
     */
    SchoolDevice findSchoolDeviceByIdOnModel(@Param("deviceId") String deviceId, @Param("deviceModel") String deviceModel);
}
