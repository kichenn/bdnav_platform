package com.bdxh.user.persistence;

import com.bdxh.user.dto.StudentDto;
import com.bdxh.user.entity.Student;
import com.bdxh.user.vo.StudentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface StudentMapper extends Mapper<Student> {
}