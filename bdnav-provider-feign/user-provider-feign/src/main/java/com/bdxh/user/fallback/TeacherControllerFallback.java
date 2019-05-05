package com.bdxh.user.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddTeacherDto;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.dto.UpdateTeacherDto;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.feign.TeacherControllerClient;
import com.bdxh.user.vo.TeacherVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-03-13 10:17
 **/
@Component
public class TeacherControllerFallback implements TeacherControllerClient {
    @Override
    public Wrapper addTeacher(AddTeacherDto addTeacherDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper removeTeacher(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper removeTeachers(String schoolCodes, String cardNumbers) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateTeacher(UpdateTeacherDto updateTeacherDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<TeacherVo> queryTeacherInfo(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper queryTeacherListPage(TeacherQueryDto teacherQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<TeacherDept> findTeacherBySchoolDeptId(String schoolCode, Long schoolId, Long deptId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper batchSaveTeacherInfo(List<AddTeacherDto> teacherList) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper queryTeacherCardNumberBySchoolCode(String schoolCode)  {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<Teacher>> findTeacherInfoByDeptOrg(String schoolCode, String parentIds) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<Teacher>> findAllTeacher() {
        return WrapMapper.error();
    }
}