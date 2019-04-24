package com.bdxh.school.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddPolicyDto;
import com.bdxh.school.dto.ModifyPolicyDto;
import com.bdxh.school.dto.QuerySchoolMode;
import com.bdxh.school.dto.QuerySchoolStrategy;
import com.bdxh.school.feign.SchoolStrategyControllerClient;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

@Component
public class SchoolStrategyControllerClientFallback implements SchoolStrategyControllerClient {
    @Override
    public Wrapper addPolicyInCondition(AddPolicyDto addPolicyDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updatePolicyInCondition(ModifyPolicyDto modifyPolicyDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delSchoolStrategyById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<QuerySchoolStrategy>> findPolicyInConditionPage(QuerySchoolStrategy querySchoolStrategy) {
        return WrapMapper.error();
    }
}
