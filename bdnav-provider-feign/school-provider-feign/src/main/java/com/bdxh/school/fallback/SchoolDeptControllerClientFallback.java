package com.bdxh.school.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.SchoolDeptDto;
import com.bdxh.school.dto.SchoolDeptModifyDto;
import com.bdxh.school.entity.SchoolDept;
import com.bdxh.school.feign.SchoolDeptControllerClient;
import com.bdxh.school.vo.SchoolDeptTreeVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 学校降级服务
 * @Author: Kang
 * @Date: 2019/3/12 10:56
 */
@Component
public class SchoolDeptControllerClientFallback implements SchoolDeptControllerClient {

    @Override
    public Wrapper<List<SchoolDeptTreeVo>> findSchoolDeptTreeBySchoolId(Long schoolId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<SchoolDept> findSchoolDeptById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<SchoolDept>> findSchoolDeptAll() {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addSchoolDept(SchoolDeptDto schoolDeptDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifySchoolDept(SchoolDeptModifyDto schoolDeptDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delSchoolDeptById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper batchDelSchoolDeptInIds(List<Long> ids) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delSchoolDeptBySchoolId(Long schoolId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<SchoolDept>> findSchoolDeptByParentId(Long parentId) {
        return WrapMapper.error();
    }
}
