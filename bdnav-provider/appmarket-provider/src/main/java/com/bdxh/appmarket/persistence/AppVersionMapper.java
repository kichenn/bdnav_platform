package com.bdxh.appmarket.persistence;

import com.bdxh.appmarket.entity.AppVersion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface AppVersionMapper extends Mapper<AppVersion> {

    /**
     * 根据appId删除
     * @param appId
     */
    void deleteByAppId(@Param("appId") Long appId);

    /**
     * 查询appVersion是否存在
     * @param appId
     * @param appVersion
     * @return
     */
    Integer isAppVersionExist(Long appId, String appVersion);

    /**
     * 根据条件查询应用版本列表
     * @param param
     * @return
     */
    List<AppVersion> getByCondition(Map<String,Object> param);

}