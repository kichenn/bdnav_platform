package com.bdxh.user.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.feign.TeacherDeptControllerClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-10 17:23
 **/
@Component
public class TeacherDeptControllerFallback implements TeacherDeptControllerClient {
    @Override
    public Wrapper deleteTeacherDeptInfo(String schoolCode, Integer deptId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<TeacherDept>> findAllTeacherDeptInfo(TeacherDept teacherDept) {
        return WrapMapper.error();
    }
}