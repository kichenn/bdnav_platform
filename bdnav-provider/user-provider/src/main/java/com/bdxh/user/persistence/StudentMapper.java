package com.bdxh.user.persistence;

import com.bdxh.user.entity.Student;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface StudentMapper extends Mapper<Student> {
}