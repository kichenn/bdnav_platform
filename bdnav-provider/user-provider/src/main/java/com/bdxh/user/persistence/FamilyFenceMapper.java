package com.bdxh.user.persistence;

import com.bdxh.user.entity.FamilyFence;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface FamilyFenceMapper extends Mapper<FamilyFence> {
}