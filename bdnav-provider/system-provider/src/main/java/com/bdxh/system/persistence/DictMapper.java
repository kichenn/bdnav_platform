package com.bdxh.system.persistence;

import com.bdxh.system.entity.Dict;
import org.apache.ibatis.annotations.Param;
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

    //根据字典名称查询当前用户
    Dict getByDictName(@Param("name") String name);
}