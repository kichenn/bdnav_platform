package com.bdxh.appmarket.service;

import com.bdxh.appmarket.dto.ApplicationVersionDto;
import com.bdxh.appmarket.entity.App;
import com.bdxh.appmarket.entity.AppImage;
import com.bdxh.appmarket.entity.AppVersion;
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
    void saveApp(App app, List<AppImage> appImages, AppVersion appVersion);

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

    /**
     * 分页查询全部应用or学校特定应用
     * @param schoolId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<App> getApplicationOfCollection(Long schoolId,String appName,Byte platform,Integer pageNum, Integer pageSize);

    /**
     * 分页查询所有信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<App> findAppList(Integer pageNum, Integer pageSize);


    /**
     * 根据ids查询所匹配的app列表
     * @return
     */
    List<App> getAppListByids(String ids);

    App versionUpdating(Long id);

    //家长查询应用市场列表
    List<App> familyFindAppInfo(String schoolCode);

    //查询预置应用
    List<App> thePresetList(Byte preset);

    //根据学校编号查询全部应用及对应版本编号
    List<ApplicationVersionDto> findTheApplicationList(@Param("schoolCode")String schoolCode);
}
