package com.bdxh.school.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.*;
import com.bdxh.school.entity.SchoolDept;
import com.bdxh.school.entity.SchoolDevice;
import com.bdxh.school.feign.SchoolDeviceControllerClient;
import com.bdxh.school.vo.SchoolDeptTreeVo;
import com.bdxh.school.vo.SchoolDeviceShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 学校门禁信息feign降级服务
 * @Author: Kang
 * @Date: 2019/3/27 16:43
 */
@Component
public class SchoolDeviceControllerClientFallback implements SchoolDeviceControllerClient {
    @Override
    public Wrapper addSchoolDevice(AddSchoolDeviceDto addSchoolDeviceDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifySchoolDevice(ModifySchoolDeviceDto modifySchoolDeviceDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delSchoolDeviceById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delBatchSchoolDeviceInIds(List<Long> ids) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<SchoolDevice> findSchoolDeviceById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<SchoolDeviceShowVo>> findSchoolDeviceInConditionPage(SchoolDeviceQueryDto schoolDeviceQueryDto) {
        return WrapMapper.error();
    }
}
