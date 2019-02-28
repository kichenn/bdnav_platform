package com.bdxh.system.persistence;

import com.bdxh.system.entity.Dict;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface DictMapper extends Mapper<Dict> {
    /**
     * 根据条件查询字典
     * @param param
     * @return
     */
    List<Dict> getByCondition(Map<String,Object> param);
}