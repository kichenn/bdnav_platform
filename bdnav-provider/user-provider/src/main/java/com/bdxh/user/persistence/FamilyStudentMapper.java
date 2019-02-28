package com.bdxh.user.persistence;

import com.bdxh.user.entity.FamilyStudent;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface FamilyStudentMapper extends Mapper<FamilyStudent> {
}