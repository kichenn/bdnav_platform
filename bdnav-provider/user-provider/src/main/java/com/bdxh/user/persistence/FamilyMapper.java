package com.bdxh.user.persistence;

import com.bdxh.user.entity.Family;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface FamilyMapper extends Mapper<Family> {
}