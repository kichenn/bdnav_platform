package com.bdxh.user.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddTeacherDto;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.dto.UpdateTeacherDto;
import com.bdxh.user.feign.TeacherControllerClient;
import com.bdxh.user.vo.TeacherVo;
import org.springframework.stereotype.Component;

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
}