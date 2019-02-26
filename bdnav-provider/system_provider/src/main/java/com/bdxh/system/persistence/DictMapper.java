package com.bdxh.system.persistence;

import com.bdxh.system.entity.Dict;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface DictMapper extends Mapper<Dict> {
}