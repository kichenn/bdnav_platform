package com.bdxh.client.controller.appmarket;

import com.bdxh.appmarket.dto.AddImageDto;
import com.bdxh.appmarket.dto.ImageQueryDto;
import com.bdxh.appmarket.dto.UpdateImageDto;
import com.bdxh.appmarket.feign.AppImageControllerClient;
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
 * @description: 应用图片控制器
 * @author: xuyuan
 * @create: 2019-04-12 14:14
 **/
@RestController
@RequestMapping("/appImageWeb")
@Validated
@Slf4j
@Api(value = "应用图片管理", tags = "应用图片管理")
public class AppImageWebController {

    @Autowired
    private AppImageControllerClient appImageControllerClient;

    @ApiOperation("增加应用图片")
    @RequestMapping(value = "/addImage",method = RequestMethod.POST)
    public Object addImage(@Valid @RequestBody AddImageDto addImageDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            SchoolUser user = SecurityUtils.getCurrentUser();
            addImageDto.setOperator(user.getId());
            addImageDto.setOperatorName(user.getUserName());
            addImageDto.setSchoolId(user.getSchoolId());
            addImageDto.setSchoolCode(user.getSchoolCode());
            Wrapper wrapper = appImageControllerClient.addImage(addImageDto);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id删除应用图片")
    @RequestMapping(value = "/delImage",method = RequestMethod.POST)
    public Object delImage(@RequestParam(name = "id") @NotNull(message = "应用图片id不能为空") Long id){
        try {
            Wrapper wrapper = appImageControllerClient.delImage(id);
            return wrapper;
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
            SchoolUser user = SecurityUtils.getCurrentUser();
            updateImageDto.setOperator(user.getId());
            updateImageDto.setOperatorName(user.getUserName());
            updateImageDto.setSchoolId(user.getSchoolId());
            updateImageDto.setSchoolCode(user.getSchoolCode());
            Wrapper wrapper = appImageControllerClient.updateImage(updateImageDto);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用图片")
    @RequestMapping(value = "/queryImage",method = RequestMethod.GET)
    public Object queryImage(@RequestParam(name = "id") @NotNull(message = "应用图片id不能为空") Long id){
        try {
            Wrapper wrapper = appImageControllerClient.queryImage(id);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用图片列表")
    @RequestMapping(value = "/queryImageList",method = RequestMethod.POST)
    public Object queryImageList(@Valid @RequestBody ImageQueryDto imageQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Wrapper wrapper = appImageControllerClient.queryImageList(imageQueryDto);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("分页查询应用图片列表")
    @RequestMapping(value = "/queryImageListPage",method = RequestMethod.POST)
    public Object queryImageListPage(@Valid @RequestBody ImageQueryDto imageQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Wrapper wrapper = appImageControllerClient.queryImageListPage(imageQueryDto);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
