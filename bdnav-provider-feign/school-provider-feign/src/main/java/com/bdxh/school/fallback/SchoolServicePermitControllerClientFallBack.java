package com.bdxh.school.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolServicePermitDto;
import com.bdxh.school.dto.ModifySchoolServicePermitDto;
import com.bdxh.school.dto.SchoolServicePermitQueryDto;
import com.bdxh.school.entity.SchoolServicePermit;
import com.bdxh.school.feign.SchoolServicePermitControllerClient;
import com.bdxh.school.vo.SchoolServicePermitShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

/** 学校服务许可权限Feign降级服务
 * @author WanMing
 * @date 2019/5/28 19:44
 */
@Component
public class SchoolServicePermitControllerClientFallBack implements SchoolServicePermitControllerClient {


    @Override
    public Wrapper addSchoolServicePermit(AddSchoolServicePermitDto addSchoolServicePermitDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifySchoolServicePermit(ModifySchoolServicePermitDto modifySchoolServicePermitDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<SchoolServicePermitShowVo>> findSchoolServicePermitInConditionPage(SchoolServicePermitQueryDto schoolServicePermitQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<SchoolServicePermit> findSchoolServicePermitById(Long id) {
        return WrapMapper.error();
    }
}
