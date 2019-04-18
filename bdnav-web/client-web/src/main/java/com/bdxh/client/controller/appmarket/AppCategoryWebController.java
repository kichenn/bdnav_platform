package com.bdxh.client.controller.appmarket;

import com.bdxh.appmarket.dto.AddCategoryDto;
import com.bdxh.appmarket.dto.CategoryQueryDto;
import com.bdxh.appmarket.dto.UpdateCategoryDto;
import com.bdxh.appmarket.feign.AppCategoryControllerClient;
import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.entity.SchoolUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

/**
 * @description: 应用分类控制器
 * @author: xuyuan
 * @create: 2019-04-12 12:18
 **/
@RestController
@RequestMapping("/appCategoryWeb")
@Validated
@Slf4j
@Api(value = "应用分类管理", tags = "应用分类管理")
public class AppCategoryWebController {

    @Autowired
    private AppCategoryControllerClient appCategoryControllerClient;


    @ApiOperation("增加应用分类")
    @RequestMapping(value = "/addCategory",method = RequestMethod.POST)
    public Object addCategory(@Valid @RequestBody AddCategoryDto addCategoryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            SchoolUser user = SecurityUtils.getCurrentUser();
            addCategoryDto.setOperator(user.getId());
            addCategoryDto.setOperatorName(user.getUserName());
            addCategoryDto.setSchoolId(user.getSchoolId());
            addCategoryDto.setSchoolCode(user.getSchoolCode());
            Wrapper wrapper = appCategoryControllerClient.addCategory(addCategoryDto);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id删除应用分类")
    @RequestMapping(value = "/delCategory",method = RequestMethod.POST)
    public Object delCategory(@RequestParam(name = "id") @NotNull(message = "分类id不能为空") Long id){
        try {
            Wrapper wrapper = appCategoryControllerClient.delCategory(id);
            return wrapper;
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
            SchoolUser user = SecurityUtils.getCurrentUser();
            updateCategoryDto.setOperator(user.getId());
            updateCategoryDto.setOperatorName(user.getUserName());
            updateCategoryDto.setSchoolId(user.getSchoolId());
            updateCategoryDto.setSchoolCode(user.getSchoolCode());
            Wrapper wrapper = appCategoryControllerClient.updateCategory(updateCategoryDto);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用分类")
    @RequestMapping(value = "/queryCategory",method = RequestMethod.GET)
    public Object queryCategory(@RequestParam(name = "id") @NotNull(message = "分类id不能为空") Long id){
        try {
            Wrapper wrapper = appCategoryControllerClient.queryCategory(id);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用分类列表")
    @RequestMapping(value = "/queryCategoryList",method = RequestMethod.POST)
    public Object queryCategoryList(@Valid @RequestBody CategoryQueryDto categoryQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Wrapper wrapper = appCategoryControllerClient.queryCategoryList(categoryQueryDto);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("分页查询应用分类列表")
    @RequestMapping(value = "/queryCategoryListPage",method = RequestMethod.POST)
    public Object queryCategoryListPage(@Valid @RequestBody CategoryQueryDto categoryQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Wrapper wrapper = appCategoryControllerClient.queryCategoryListPage(categoryQueryDto);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
