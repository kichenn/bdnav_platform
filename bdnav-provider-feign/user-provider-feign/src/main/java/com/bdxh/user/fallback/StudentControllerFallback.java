/**
 * Copyright (C), 2019-2019
 * FileName: StudentControllerFallback
 * Author:   binzh
 * Date:     2019/3/11 14:17
 * Description: 学生信息降级服务
 * History:
 */
package com.bdxh.user.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddStudentDto;
import com.bdxh.user.dto.StudentQueryDto;
import com.bdxh.user.dto.UpdateStudentDto;
import com.bdxh.user.entity.Student;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.vo.StudentVo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


/**
 * @Author： binzh
 * @Description： //学生信息降级服务
 * @Date： 14:18 2019/3/11
 **/
@Component
public class StudentControllerFallback implements StudentControllerClient {

    @Override
    public Wrapper queryStudentListPage(StudentQueryDto studentQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper removeStudents(String schoolCodes, String cardNumbers) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateStudent(UpdateStudentDto updateStudentDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<StudentVo> queryStudentInfo(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addStudent(AddStudentDto addStudentDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper removeStudent(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<Student> findStudentBySchoolClassId(String schoolCode, Long schoolId, Long classId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper batchSaveStudentInfo(List<AddStudentDto> studentList) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper queryCardNumberBySchoolCode(String schoolCode) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<Student>> findStudentInfoByClassOrg(String schoolCode, String parentIds, Byte type) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<Student>> findAllStudent() {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<Student>> findStudentInfo(StudentQueryDto studentQueryDto) {
        return WrapMapper.error();
    }


}
