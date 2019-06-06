package com.bdxh.backend.controller.appmarket;

import com.bdxh.appmarket.dto.*;
import com.bdxh.appmarket.feign.SystemAppControllerClient;
import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.helper.qcloud.files.constant.QcloudConstants;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/SystemWebApp")
@Validated
@Slf4j
@Api(value = "系统管控应用控制器", tags = "系统管控应用控制器")
public class SystemAppWebController {


    @Autowired
    private SystemAppControllerClient systemAppControllerClient;

    @ApiOperation("添加管控应用信息")
    @RequestMapping(value = "/addAppCategory",method = RequestMethod.POST)
    public Object addAppCategory(@Valid @RequestBody AddSystemAppDto addSystemAppDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            User user = SecurityUtils.getCurrentUser();
            addSystemAppDto.setOperator(user.getId());
            addSystemAppDto.setOperatorName(user.getUserName());
            Wrapper wrapper = systemAppControllerClient.addAppCategory(addSystemAppDto);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id删除应用")
    @RequestMapping(value = "/delSystemAppById",method = RequestMethod.GET)
    public Object delSystemAppById(@RequestParam(name = "id") Long id){
        try {
            Wrapper wrapper = systemAppControllerClient.delSystemAppById(id);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @ApiOperation("修改管控应用信息")
    @RequestMapping(value = "/modifyAppCategory",method = RequestMethod.POST)
    public Object modifyAppCategory(@Valid @RequestBody ModifySystemAppDto modifySystemAppDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            User user = SecurityUtils.getCurrentUser();
            modifySystemAppDto.setOperator(user.getId());
            modifySystemAppDto.setOperatorName(user.getUserName());
            Wrapper wrapper = systemAppControllerClient.modifyAppCategory(modifySystemAppDto);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }



    @ApiOperation("带条件查询管控应用信息")
    @RequestMapping(value = "/findAppControl",method = RequestMethod.POST)
    public Object findAppControl(@Valid @RequestBody QuerySystemAppDto querySystemAppDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Wrapper wrapper = systemAppControllerClient.findAppControl(querySystemAppDto);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 上传APP应用
     */
    @ApiOperation("上传APP应用")
    @RequestMapping(value = "/androidMarket",method = RequestMethod.POST)
    public Object androidMarket(MultipartFile multipartFile){
        Map<String, String> result = null;
        try {
            result = FileOperationUtils.saveBatchFile(multipartFile, QcloudConstants.APP_BUCKET_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WrapMapper.ok(result);
    }


}
