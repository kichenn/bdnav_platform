package com.bdxh.user.persistence;

import com.bdxh.user.entity.Teacher;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface TeacherMapper extends Mapper<Teacher> {
}