package com.bdxh.system.persistence;

import com.bdxh.system.entity.Dept;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface DeptMapper extends Mapper<Dept> {
    /**
     * 根据条件字典数据
     * @param param
     * @return
     */
    List<Dept> getByCondition(Map<String,Object> param);
}