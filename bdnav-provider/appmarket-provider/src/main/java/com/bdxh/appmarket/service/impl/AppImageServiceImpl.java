package com.bdxh.appmarket.service.impl;

import com.bdxh.appmarket.entity.AppImage;
import com.bdxh.appmarket.persistence.AppImageMapper;
import com.bdxh.appmarket.service.AppImageService;
import com.bdxh.common.support.BaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description: 应用图片service实现
 * @author: xuyuan
 * @create: 2019-04-11 14:28
 **/
@Service
public class AppImageServiceImpl extends BaseService<AppImage> implements AppImageService {

    @Autowired
    private AppImageMapper appImageMapper;

    @Override
    public List<AppImage> getAppImageList(Map<String, Object> param) {
        List<AppImage> appImages = appImageMapper.getByCondition(param);
        return appImages;
    }

    @Override
    public PageInfo<AppImage> getAppImageListPage(Map<String, Object> param, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<AppImage> appImages = appImageMapper.getByCondition(param);
        PageInfo<AppImage> pageInfo = new PageInfo<>(appImages);
        return pageInfo;
    }

}
