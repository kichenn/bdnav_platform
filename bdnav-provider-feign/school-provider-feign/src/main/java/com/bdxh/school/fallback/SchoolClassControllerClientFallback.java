package com.bdxh.school.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.*;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.feign.SchoolClassControllerClient;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.vo.SchoolClassTreeVo;
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
public class SchoolClassControllerClientFallback implements SchoolClassControllerClient {

    @Override
    public Wrapper<List<SchoolClassTreeVo>> findSchoolClassTreeBySchoolId(Long schoolId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<SchoolClass> findSchoolClassById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<SchoolClass>> findSchoolClassAll() {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addSchoolClass(SchoolClassDto schoolClassDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifySchoolClass(SchoolClassModifyDto schoolClassDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delSchoolClassById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper batchDelSchoolClassInIds(List<Long> ids) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delSchoolClassBySchoolId(Long schoolId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<SchoolClass> findSchoolClassBySchoolClass(SchoolClass schoolClass) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<SchoolClass>> findSchoolClassByParentId(Long id) {
        return WrapMapper.error();
    }

    @Override
    public  Wrapper<List<SchoolClass>> queryClassUrlBySchoolCode(String schoolCode) {
        return WrapMapper.error();
    }
}
