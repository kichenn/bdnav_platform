package com.bdxh.school.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolFenceDto;
import com.bdxh.school.dto.ModifySchoolFenceDto;
import com.bdxh.school.dto.SchoolFenceQueryDto;
import com.bdxh.school.entity.SchoolFence;
import com.bdxh.school.feign.SchoolFenceControllerClient;
import com.bdxh.school.vo.SchoolFenceShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 学校围栏降级
 * @Author: Kang
 * @Date: 2019/4/11 15:34
 */
@Component
public class SchoolFenceControllerClientFallback implements SchoolFenceControllerClient {
    @Override
    public Wrapper addFence(AddSchoolFenceDto addSchoolFenceDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyFence(ModifySchoolFenceDto modifySchoolFenceDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delFenceById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<SchoolFence> findFenceById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<SchoolFenceShowVo>> findFenceInConditionPaging(SchoolFenceQueryDto schoolFenceQueryDto) {
        return WrapMapper.error();
    }
}
