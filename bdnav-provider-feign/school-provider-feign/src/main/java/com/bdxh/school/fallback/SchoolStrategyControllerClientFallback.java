package com.bdxh.school.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddPolicyDto;
import com.bdxh.school.dto.ModifyPolicyDto;
import com.bdxh.school.dto.QuerySchoolStrategy;
import com.bdxh.school.entity.SchoolStrategy;
import com.bdxh.school.feign.SchoolStrategyControllerClient;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;


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

    @Override
    public Wrapper getByPriority(String schoolCode, Integer priority) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<SchoolStrategy>> findAllStrategy() {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<SchoolStrategy>> getStrategyList(QuerySchoolStrategy querySchoolStrategy) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updatePolicyPushStatus(Long id, Byte pushState) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<QuerySchoolStrategy> findStrategyById(Long id) {
        return WrapMapper.error();
    }

}
