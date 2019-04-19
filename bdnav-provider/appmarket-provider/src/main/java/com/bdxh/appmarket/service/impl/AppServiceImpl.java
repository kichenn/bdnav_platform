package com.bdxh.appmarket.service.impl;

import com.bdxh.appmarket.entity.App;
import com.bdxh.appmarket.entity.AppImage;
import com.bdxh.appmarket.persistence.AppImageMapper;
import com.bdxh.appmarket.persistence.AppMapper;
import com.bdxh.appmarket.service.AppService;
import com.bdxh.common.support.BaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description: 应用管理service实现
 * @author: xuyuan
 * @create: 2019-03-04 14:55
 **/
@Service
@Slf4j
public class AppServiceImpl extends BaseService<App> implements AppService {

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private AppImageMapper appImageMapper;

    public Integer isCategoryAppExist(Long categoryId){
        Integer isCategoryAppExist = appMapper.isCategoryAppExist(categoryId);
        return isCategoryAppExist;
    }

    @Override
    public Integer isAppExist(String appPackage) {
        Integer isAppExist = appMapper.isAppExist(appPackage);
        return isAppExist;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveApp(App app, List<AppImage> appImages) {
        appMapper.insertSelective(app);
        if (appImages!=null&&appImages.size()>0){
            for (int i=0;i<appImages.size();i++){
                AppImage appImage = appImages.get(i);
                appImage.setAppId(app.getId());
                appImage.setOperator(app.getOperator());
                appImage.setOperatorName(app.getOperatorName());
            }
            appImageMapper.batchSaveImage(appImages);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delApp(Long id) {
        appMapper.deleteByPrimaryKey(id);
        appImageMapper.deleteByAppId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateApp(App app, List<AppImage> appImages) {
        appMapper.updateByPrimaryKeySelective(app);
        appImageMapper.deleteByAppId(app.getId());
        if (appImages!=null&&appImages.size()>0){
            for (int i=0;i<appImages.size();i++){
                AppImage appImage = appImages.get(i);
                appImage.setAppId(app.getId());
                appImage.setOperator(app.getOperator());
                appImage.setOperatorName(app.getOperatorName());
            }
            appImageMapper.batchSaveImage(appImages);
        }
    }

    @Override
    public List<App> getAppList(Map<String, Object> param) {
        List<App> apps = appMapper.getByCondition(param);
        return apps;
    }

    @Override
    public PageInfo<App> getAppListPage(Map<String, Object> param, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<App> apps = appMapper.getByCondition(param);
        PageInfo<App> appPageInfo = new PageInfo<>(apps);
        return appPageInfo;
    }

    @Override
    public PageInfo<App> getApplicationOfCollection(Long schoolId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<App> apps = appMapper.getApplicationOfCollection(schoolId);
        return new PageInfo(apps);
    }

}
