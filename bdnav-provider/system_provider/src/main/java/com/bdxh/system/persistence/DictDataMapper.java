package com.bdxh.system.persistence;

import com.bdxh.system.entity.DictData;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface DictDataMapper extends Mapper<DictData> {
}