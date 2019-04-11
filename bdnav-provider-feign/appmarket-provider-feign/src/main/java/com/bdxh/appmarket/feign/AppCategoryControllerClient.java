package com.bdxh.appmarket.feign;

import com.bdxh.appmarket.dto.AddCategoryDto;
import com.bdxh.appmarket.dto.CategoryQueryDto;
import com.bdxh.appmarket.dto.UpdateCategoryDto;
import com.bdxh.appmarket.entity.AppCategory;
import com.bdxh.appmarket.fallback.AppCategoryControllerClientFallback;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description: 应用分类feign客户端
 * @author: xuyuan
 * @create: 2019-04-11 15:49
 **/
@Service
@FeignClient(value = "appmarket-provider-cluster",fallback = AppCategoryControllerClientFallback.class)
public interface AppCategoryControllerClient {


    @RequestMapping("/appCategory/addCategory")
    Wrapper addCategory(@RequestBody AddCategoryDto addCategoryDto);

    @RequestMapping("/appCategory/delCategory")
    Wrapper delCategory(@RequestParam(name = "id") Long id);

    @RequestMapping("/appCategory/updateCategory")
    Wrapper updateCategory(@RequestBody UpdateCategoryDto updateCategoryDto);

    @RequestMapping("/appCategory/queryCategory")
    Wrapper<AppCategory> queryCategory(@RequestParam(name = "id") Long id);

    @RequestMapping("/appCategory/queryCategoryList")
    Wrapper<List<AppCategory>> queryCategoryList(@RequestBody CategoryQueryDto categoryQueryDto);

    @RequestMapping("/appCategory/queryCategoryListPage")
    Wrapper<PageInfo<AppCategory>> queryCategoryListPage(@RequestBody CategoryQueryDto categoryQueryDto);

}
