package com.bdxh.backend.controller.system;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.*;
import com.bdxh.system.feign.PermissionControllerClient;
import com.bdxh.system.vo.PermissionTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/sysPermission")
@Validated
@Slf4j
@Api(value = "系统权限交互API", tags = "系统权限交互API")
public class SysPermissionController {

    @Autowired
    private PermissionControllerClient permissionControllerClient;


    @RequestMapping(value="/findPermissionByRoleId",method = RequestMethod.GET)
    @ApiOperation("角色id查询用户菜单or按钮权限")
    public Object findPermissionByRoleId(@RequestParam(name = "roleId",defaultValue = "6")Long roleId,
                                         @RequestParam(name = "type",defaultValue = "1") Byte type){
        try {
            Wrapper wrapper = permissionControllerClient.findPermissionByRoleId(roleId,type);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value="/permissionMenus",method = RequestMethod.GET)
    @ApiOperation("根据用户id查询权限列表")
    public Object permissionMenus(@RequestParam(name = "userId")Long userId){
        try {
            Wrapper wrapper = permissionControllerClient.permissionMenus(userId);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value="/addPermission",method = RequestMethod.POST)
    @ApiOperation("添加权限菜单")
    public Object addPermission(@RequestBody AddPermissionDto addPermissionDto){
        try {
            Wrapper wrapper = permissionControllerClient.addPermission(addPermissionDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value="/modifyPermission",method = RequestMethod.POST)
    @ApiOperation("修改权限菜单")
    public Object modifyPermission(@RequestBody ModifyPermissionDto modifyPermissionDto){
        try {
            Wrapper wrapper = permissionControllerClient.modifyPermission(modifyPermissionDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value="/theTreeMenu",method = RequestMethod.GET)
    @ApiOperation("查询特定情况下菜单")
    public Object theTreeMenu( @RequestParam(value = "roleId") Long roleId,
                               @RequestParam(value = "selected",defaultValue = "2") Integer selected){
        try {
            Wrapper wrapper = permissionControllerClient.theTreeMenu(roleId,selected);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value="/thePermissionMenu",method = RequestMethod.GET)
    @ApiOperation("查询全部菜单权限")
    public Object thePermissionMenu(){
        try {
            Wrapper wrapper = permissionControllerClient.thePermissionMenu();
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value="/addOrUpdatePermission",method = RequestMethod.POST)
    @ApiOperation("保存并修改权限")
    public Object addOrUpdatePermission(@RequestBody BaPermissionsDto baPermissionsDto){
        try {
            Wrapper wrapper = permissionControllerClient.addOrUpdatePermission(baPermissionsDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value="/delPermissionById",method = RequestMethod.GET)
    @ApiOperation("删除单个菜单权限")
    public Object delPermissionById(@RequestParam("id") Long id){
        try {
            Wrapper wrapper = permissionControllerClient.delPermissionById(id);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


}
