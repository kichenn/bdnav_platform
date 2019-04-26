package com.bdxh.appmarket.persistence;

import com.bdxh.appmarket.entity.App;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface AppMapper extends Mapper<App> {

    /**
     * 判断分类下是否存在app
     * @param categoryId
     * @return
     */
    Integer isCategoryAppExist(@Param("categoryId") Long categoryId);

    /**
     * 判断app包名是否存在
     * @param appPackage
     * @return
     */
    Integer isAppExist(@Param("appPackage") String appPackage);

    /**
     * 根据条件查询app列表
     * @param param
     * @return
     */
    List<App> getByCondition(Map<String,Object> param);

    //显示全部应用or学校特定应用
    List<App> getApplicationOfCollection(@Param("schoolId") Long schoolId,@Param("appName") String appName,@Param("platform") Byte platform);



}