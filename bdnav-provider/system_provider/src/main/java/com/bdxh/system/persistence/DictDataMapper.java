package com.bdxh.system.persistence;

import com.bdxh.system.entity.DictData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface DictDataMapper extends Mapper<DictData> {

    /**
     * 根据条件字典数据
     * @param param
     * @return
     */
    List<DictData> getByCondition(Map<String,Object> param);

   //根据id查找字典列表
    List<DictData> getDictDataById(@Param("id") Long id);

}