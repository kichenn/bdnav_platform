package com.bdxh.appmarket.persistence;

import com.bdxh.appmarket.entity.AppCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface AppCategoryMapper extends Mapper<AppCategory> {

    /**
     * 根据分类名称精确查找分类
     * @param categoryName
     * @return
     */
    AppCategory getByCategoryName(@Param("categoryName") String categoryName);

    /**
     * 根据条件查询应用分类
     * @param param
     * @return
     */
    List<AppCategory> getByCondition(Map<String,Object> param);

}