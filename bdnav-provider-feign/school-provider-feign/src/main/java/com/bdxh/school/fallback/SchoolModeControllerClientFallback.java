package com.bdxh.school.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolModeDto;
import com.bdxh.school.dto.ModifySchoolModeDto;
import com.bdxh.school.feign.SchoolModeControllerClient;
import org.springframework.stereotype.Component;

@Component
public class SchoolModeControllerClientFallback implements SchoolModeControllerClient {

    @Override
    public Wrapper addSchoolModes(AddSchoolModeDto addSchoolModeDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifySchoolModes(ModifySchoolModeDto modifySchoolModeDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delSchoolModesById(Long id) {
        return WrapMapper.error();
    }
}
