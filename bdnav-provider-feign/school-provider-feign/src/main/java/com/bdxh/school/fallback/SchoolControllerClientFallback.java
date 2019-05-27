package com.bdxh.school.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.ModifySchoolDto;
import com.bdxh.school.dto.SchoolDto;
import com.bdxh.school.dto.SchoolExcelDto;
import com.bdxh.school.dto.SchoolQueryDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.vo.BaseEchartsVo;
import com.bdxh.school.vo.SchoolInfoVo;
import com.bdxh.school.vo.SchoolShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 学校降级服务
 * @Author: Kang
 * @Date: 2019/3/12 10:56
 */
@Component
public class SchoolControllerClientFallback implements SchoolControllerClient {

    @Override
    public Wrapper addSchool(SchoolDto schoolDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifySchoolInfo(ModifySchoolDto schoolDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delSchool(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper batchDelSchool(List<Long> ids) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<SchoolInfoVo> findSchoolById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<SchoolShowVo>> findSchoolsInConditionPaging(SchoolQueryDto schoolQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<SchoolShowVo>> findSchoolsInCondition(SchoolQueryDto schoolQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<SchoolShowVo>> findSchools() {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<School>> findSchoolInIds(List<Long> ids) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<School> findSchoolBySchoolCode(String schoolCode) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<BaseEchartsVo>> querySchoolNumByArea() {
        return WrapMapper.error();
    }
}
