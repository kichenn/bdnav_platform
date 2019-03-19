package com.bdxh.appmarket.service;

import com.bdxh.appmarket.entity.AppCategory;
import com.bdxh.common.support.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @description: 应用分类service
 * @author: xuyuan
 * @create: 2019-03-04 14:54
 **/
public interface AppCategoryService extends IService<AppCategory> {

    /**
     * 根据分类名称精确查找分类
     * @param categoryName
     * @return
     */
    AppCategory getByCategoryName(String categoryName);

    /**
     * 根据条件查询应用分类
     * @param param
     * @return
     */
    List<AppCategory> getAppCategoryList(Map<String,Object> param);

    /**
     * 根据条件分页查询应用分类
     * @param param
     * @return
     */
    PageInfo<AppCategory> getAppCategoryListPage(Map<String,Object> param, int pageNum, int pageSize);

}
