package com.bdxh.appmarket.service;

import com.bdxh.appmarket.entity.AppImage;
import com.bdxh.common.support.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @description: 应用图片service
 * @author: xuyuan
 * @create: 2019-04-11 14:25
 **/
public interface AppImageService extends IService<AppImage> {

    /**
     * 根据条件查询应用图片列表
     * @param param
     * @return
     */
    List<AppImage> getAppImageList(Map<String,Object> param);

    /**
     * 根据条件分页查询应用图片列表
     * @param param
     * @return
     */
    PageInfo<AppImage> getAppImageListPage(Map<String, Object> param, int pageNum, int pageSize);

}
