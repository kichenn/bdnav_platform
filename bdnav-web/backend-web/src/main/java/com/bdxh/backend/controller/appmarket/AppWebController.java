package com.bdxh.backend.controller.appmarket;

import com.bdxh.appmarket.dto.AddAppDto;
import com.bdxh.appmarket.dto.AppQueryDto;
import com.bdxh.appmarket.dto.QueryAppDto;
import com.bdxh.appmarket.dto.UpdateAppDto;
import com.bdxh.appmarket.feign.AppControllerClient;
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
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 应用市场管理控制器
 * @author: xuyuan
 * @create: 2019-04-12 14:07
 **/
@RestController
@RequestMapping("/appWeb")
@Validated
@Slf4j
@Api(value = "应用市场管理", tags = "应用市场管理")
public class AppWebController {

    @Autowired
    private AppControllerClient appControllerClient;

    @ApiOperation("增加应用")
    @RequestMapping(value = "/addApp",method = RequestMethod.POST)
    public Object addCategory(@Valid @RequestBody AddAppDto addAppDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            User user = SecurityUtils.getCurrentUser();
            addAppDto.setOperator(user.getId());
            addAppDto.setOperatorName(user.getUserName());
            Wrapper wrapper = appControllerClient.addApp(addAppDto);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id删除应用")
    @RequestMapping(value = "/delApp",method = RequestMethod.POST)
    public Object delApp(@RequestParam(name = "id") @NotNull(message = "应用id不能为空") Long id){
        try {
            Wrapper wrapper = appControllerClient.delApp(id);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id更新应用")
    @RequestMapping(value = "/updateApp",method = RequestMethod.POST)
    public Object updateApp(@Valid @RequestBody UpdateAppDto updateAppDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            User user = SecurityUtils.getCurrentUser();
            updateAppDto.setOperator(user.getId());
            updateAppDto.setOperatorName(user.getUserName());
            Wrapper wrapper = appControllerClient.updateApp(updateAppDto);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用")
    @RequestMapping(value = "/queryApp",method = RequestMethod.GET)
    public Object queryApp(@RequestParam(name = "id") @NotNull(message = "应用id不能为空") Long id){
        try {
            Wrapper wrapper = appControllerClient.queryApp(id);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用和图片")
    @RequestMapping(value = "/queryAppAndImages",method = RequestMethod.GET)
    public Object queryAppAndImages(@RequestParam(name = "id") @NotNull(message = "应用id不能为空") Long id){
        try {
            Wrapper wrapper = appControllerClient.queryAppAndImages(id);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用列表")
    @RequestMapping(value = "/queryAppList",method = RequestMethod.POST)
    public Object queryAppList(@Valid @RequestBody AppQueryDto appQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Wrapper wrapper = appControllerClient.queryAppList(appQueryDto);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("分页查询应用列表")
    @RequestMapping(value = "/queryAppListPage",method = RequestMethod.POST)
    public Object queryAppListPage(@Valid @RequestBody AppQueryDto appQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Wrapper wrapper = appControllerClient.queryAppListPage(appQueryDto);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("分页查询特定应用列表")
    @RequestMapping(value = "/getApplicationOfCollection",method = RequestMethod.POST)
    public Object getApplicationOfCollection(@RequestBody QueryAppDto queryAppDto){
        try {
            Wrapper wrapper = appControllerClient.getApplicationOfCollection(queryAppDto);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据ids查询应用列表")
    @RequestMapping(value = "/getAppListByids",method = RequestMethod.GET)
    public Object getAppListByids(@RequestParam(name = "ids")String ids){
        try {
            Wrapper wrapper = appControllerClient.getAppListByids(ids);
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


    /**
     * 删除APP应用
     */
    @ApiOperation("删除APP应用")
    @RequestMapping(value = "/delAndroidMarket",method = RequestMethod.GET)
    public Object delAndroidMarket(@RequestParam("fileName") String fileName){
        FileOperationUtils.deleteFile(fileName, QcloudConstants.APP_BUCKET_NAME);
        return WrapMapper.ok(true);
    }


}
