package com.bdxh.appmarket.service;

import com.bdxh.appmarket.entity.App;
import com.bdxh.appmarket.entity.AppImage;
import com.bdxh.common.support.IService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description: 应用管理service
 * @author: xuyuan
 * @create: 2019-03-04 14:55
 **/
public interface AppService extends IService<App> {

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
     * 新增应用
     * @param app
     * @param appImages
     */
    void saveApp(App app, List<AppImage> appImages);

    /**
     * 根据id删除应用
     * @param id
     */
    void delApp(Long id);

    /**
     * 根据id更新应用
     * @param app
     * @param appImages
     */
    void updateApp(App app, List<AppImage> appImages);

    /**
     * 根据条件查询app列表
     * @param param
     * @return
     */
    List<App> getAppList(Map<String,Object> param);

    /**
     * 根据条件分页查询app列表
     * @param param
     * @return
     */
    PageInfo<App> getAppListPage(Map<String,Object> param, int pageNum, int pageSize);

}
