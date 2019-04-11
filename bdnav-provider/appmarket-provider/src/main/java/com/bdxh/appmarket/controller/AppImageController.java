package com.bdxh.appmarket.controller;

import com.bdxh.appmarket.dto.AddImageDto;
import com.bdxh.appmarket.dto.ImageQueryDto;
import com.bdxh.appmarket.dto.UpdateImageDto;
import com.bdxh.appmarket.entity.AppImage;
import com.bdxh.appmarket.service.AppImageService;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.github.pagehelper.PageInfo;
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
 * @description: 应用图片控制器
 * @author: xuyuan
 * @create: 2019-04-11 14:22
 **/
@RestController
@RequestMapping("/appImage")
@Slf4j
@Validated
@Api(value = "应用图片接口文档", tags = "应用图片接口文档")
public class AppImageController {

    @Autowired
    private AppImageService appImageService;

    @ApiOperation("增加应用图片")
    @RequestMapping(value = "/addImage",method = RequestMethod.POST)
    public Object addImage(@Valid @RequestBody AddImageDto addImageDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            AppImage appImage = BeanMapUtils.map(addImageDto, AppImage.class);
            appImageService.save(appImage);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id删除应用图片")
    @RequestMapping(value = "/delImage",method = RequestMethod.POST)
    public Object delImage(@RequestParam(name = "id") @NotNull(message = "应用图片id不能为空") Long id){
        try {
            appImageService.deleteByKey(id);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id更新应用图片")
    @RequestMapping(value = "/updateImage",method = RequestMethod.POST)
    public Object updateImage(@Valid @RequestBody UpdateImageDto updateImageDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            AppImage appImage = BeanMapUtils.map(updateImageDto, AppImage.class);
            appImageService.update(appImage);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用图片")
    @RequestMapping(value = "/queryImage",method = RequestMethod.GET)
    public Object queryImage(@RequestParam(name = "id") @NotNull(message = "应用图片id不能为空") Long id){
        try {
            AppImage appImage = appImageService.selectByKey(id);
            return WrapMapper.ok(appImage);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用图片列表")
    @RequestMapping(value = "/queryImageList",method = RequestMethod.GET)
    public Object queryImageList(@Valid @RequestBody ImageQueryDto imageQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(imageQueryDto);
            List<AppImage> appImages = appImageService.getAppImageList(param);
            return WrapMapper.ok(appImages);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("分页查询应用分类列表")
    @RequestMapping(value = "/queryImageListPage",method = RequestMethod.GET)
    public Object queryImageListPage(@Valid @RequestBody ImageQueryDto imageQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(imageQueryDto);
            PageInfo<AppImage> pageInfo = appImageService.getAppImageListPage(param, imageQueryDto.getPageNum(), imageQueryDto.getPageSize());
            return WrapMapper.ok(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
