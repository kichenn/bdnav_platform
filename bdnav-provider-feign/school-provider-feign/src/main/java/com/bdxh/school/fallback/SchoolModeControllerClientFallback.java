package com.bdxh.school.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolModeDto;
import com.bdxh.school.dto.ModifySchoolModeDto;
import com.bdxh.school.dto.QuerySchoolMode;
import com.bdxh.school.entity.SchoolMode;
import com.bdxh.school.feign.SchoolModeControllerClient;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public Wrapper getModesById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<QuerySchoolMode>> findModesInConditionPage(QuerySchoolMode querySchoolMode) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<SchoolMode>> getModesAll() {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<SchoolMode>> getListByPlatform(Long schoolId,String platform) {
        return WrapMapper.error();
    }
}
