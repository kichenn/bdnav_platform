package com.bdxh.appmarket.service.impl;

import com.bdxh.appmarket.entity.AppCategory;
import com.bdxh.appmarket.persistence.AppCategoryMapper;
import com.bdxh.appmarket.service.AppCategoryService;
import com.bdxh.common.support.BaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description: 应用分类service实现
 * @author: xuyuan
 * @create: 2019-03-04 14:54
 **/
@Service
@Slf4j
public class AppCategoryServiceImpl extends BaseService<AppCategory> implements AppCategoryService {

    @Autowired
    private AppCategoryMapper appCategoryMapper;

    @Override
    public AppCategory getByCategoryName(String categoryName) {
        AppCategory appCategory = appCategoryMapper.getByCategoryName(categoryName);
        return appCategory;
    }

    @Override
    public List<AppCategory> getAppCategoryList(Map<String, Object> param) {
        List<AppCategory> appCategories = appCategoryMapper.getByCondition(param);
        return appCategories;
    }

    @Override
    public PageInfo<AppCategory> getAppCategoryListPage(Map<String, Object> param, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<AppCategory> appCategories = appCategoryMapper.getByCondition(param);
        PageInfo<AppCategory> pageInfo = new PageInfo<>(appCategories);
        return pageInfo;
    }

}
