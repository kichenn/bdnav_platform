package com.bdxh.appmarket.fallback;

import com.bdxh.appmarket.dto.AddCategoryDto;
import com.bdxh.appmarket.dto.CategoryQueryDto;
import com.bdxh.appmarket.dto.UpdateCategoryDto;
import com.bdxh.appmarket.entity.AppCategory;
import com.bdxh.appmarket.feign.AppCategoryControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 应用分类feign降级服务
 * @author: xuyuan
 * @create: 2019-04-11 15:50
 **/
@Component
public class AppCategoryControllerClientFallback implements AppCategoryControllerClient {

    @Override
    public Wrapper addCategory(AddCategoryDto addCategoryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delCategory(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateCategory(UpdateCategoryDto updateCategoryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<AppCategory> queryCategory(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<AppCategory>> queryCategoryList(CategoryQueryDto categoryQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<AppCategory>> queryCategoryListPage(CategoryQueryDto categoryQueryDto) {
        return WrapMapper.error();
    }

}
