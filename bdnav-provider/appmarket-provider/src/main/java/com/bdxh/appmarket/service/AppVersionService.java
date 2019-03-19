package com.bdxh.appmarket.service;

import com.bdxh.appmarket.entity.AppVersion;
import com.bdxh.common.support.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @description: 应用版本service
 * @author: xuyuan
 * @create: 2019-03-04 14:57
 **/
public interface AppVersionService extends IService<AppVersion> {

    /**
     * 查询appVersion是否存在
     * @param appId
     * @param appVersion
     * @return
     */
    Integer isAppVersionExist(Long appId,String appVersion);

    /**
     * 根据条件查询应用版本列表
     * @param param
     * @return
     */
    List<AppVersion> queryAppVersionList(Map<String,Object> param);

    /**
     * 根据条件分页查询应用版本列表
     * @param param
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<AppVersion> queryAppVersionListPage(Map<String,Object> param, int pageNum, int pageSize);

}
