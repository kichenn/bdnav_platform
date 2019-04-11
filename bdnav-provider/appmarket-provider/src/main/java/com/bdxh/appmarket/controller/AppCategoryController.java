package com.bdxh.appmarket.controller;

import com.bdxh.appmarket.dto.AddCategoryDto;
import com.bdxh.appmarket.dto.CategoryQueryDto;
import com.bdxh.appmarket.dto.UpdateCategoryDto;
import com.bdxh.appmarket.entity.AppCategory;
import com.bdxh.appmarket.service.AppCategoryService;
import com.bdxh.appmarket.service.AppService;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 应用分类控制器
 * @author: xuyuan
 * @create: 2019-03-05 09:36
 **/
@RestController
@RequestMapping("/appCategory")
@Slf4j
@Validated
@Api(value = "应用分类接口文档", tags = "应用分类接口文档")
public class AppCategoryController {

    @Autowired
    private AppCategoryService appCategoryService;

    @Autowired
    private AppService appService;

    @ApiOperation("增加应用分类")
    @RequestMapping(value = "/addCategory",method = RequestMethod.POST)
    public Object addCategory(@Valid @RequestBody AddCategoryDto addCategoryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            AppCategory appCategoryData = appCategoryService.getByCategoryName(addCategoryDto.getName());
            Preconditions.checkNotNull(appCategoryData,"应用分类已经存在");
            AppCategory appCategory = BeanMapUtils.map(addCategoryDto, AppCategory.class);
            appCategoryService.save(appCategory);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id删除应用分类")
    @RequestMapping(value = "/delCategory",method = RequestMethod.POST)
    public Object delCategory(@RequestParam(name = "id") @NotNull(message = "分类id不能为空") Long id){
        try {
            Integer isCategoryAppExist = appService.isCategoryAppExist(id);
            Preconditions.checkArgument(isCategoryAppExist==null,"分类下已包含应用");
            appCategoryService.deleteByKey(id);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id更新应用分类")
    @RequestMapping(value = "/updateCategory",method = RequestMethod.POST)
    public Object updateCategory(@Valid @RequestBody UpdateCategoryDto updateCategoryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            AppCategory appCategory = BeanMapUtils.map(updateCategoryDto, AppCategory.class);
            appCategoryService.update(appCategory);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用分类")
    @RequestMapping(value = "/queryCategory",method = RequestMethod.GET)
    public Object queryCategory(@RequestParam(name = "id") @NotNull(message = "分类id不能为空") Long id){
        try {
            AppCategory appCategory = appCategoryService.selectByKey(id);
            return WrapMapper.ok(appCategory);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用分类列表")
    @RequestMapping(value = "/queryCategoryList",method = RequestMethod.GET)
    public Object queryCategoryList(@Valid @RequestBody CategoryQueryDto categoryQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(categoryQueryDto);
            List<AppCategory> pageInfo = appCategoryService.getAppCategoryList(param);
            return WrapMapper.ok(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("分页查询应用分类列表")
    @RequestMapping(value = "/queryCategoryListPage",method = RequestMethod.GET)
    public Object queryCategoryListPage(@Valid @RequestBody CategoryQueryDto categoryQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(categoryQueryDto);
            PageInfo<AppCategory> pageInfo = appCategoryService.getAppCategoryListPage(param, categoryQueryDto.getPageNum(), categoryQueryDto.getPageSize());
            return WrapMapper.ok(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
