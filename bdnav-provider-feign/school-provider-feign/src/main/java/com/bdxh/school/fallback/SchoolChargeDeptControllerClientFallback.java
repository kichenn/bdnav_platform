package com.bdxh.school.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.*;
import com.bdxh.school.feign.SchoolChargeDeptControllerClient;
import com.bdxh.school.vo.BaseEchartsVo;
import com.bdxh.school.vo.SchoolChargeDeptVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消费部门的降级处理
 * @author WanMing
 * @date 2019/7/12 11:17
 */
@Component
public class SchoolChargeDeptControllerClientFallback implements SchoolChargeDeptControllerClient {
    @Override
    public Wrapper addSchoolChargeDept(AddSchoolChargeDeptDto addSchoolChargeDeptDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delSchoolChargeDept(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifySchoolChargeDept(ModifySchoolChargeDeptDto modifySchoolChargeDeptDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<SchoolChargeDeptVo>> findSchoolChargeDeptByCondition(QuerySchoolChargeDeptDto querySchoolChargeDeptDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<SchoolChargeDeptVo>> findSchoolChargeDeptBySchool(String schoolCode,Byte chargeDeptType) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addSchoolPosDeviceCharge(AddSchoolPosDeviceChargeDto addSchoolPosDeviceChargeDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper changeSchoolPosDevice(ModifySchoolPosDeviceDto modifySchoolPosDeviceDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delSchoolPosDeviceCharge(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<SchoolChargeDeptVo>> querySchoolChargeDeptAndPosNum(QuerySchoolChargeDeptDto querySchoolChargeDeptDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<BaseEchartsVo>> queryChargeDeptNumAndPosNum(String schoolCode) {
        return WrapMapper.error();
    }
}
