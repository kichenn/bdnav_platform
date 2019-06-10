package com.bdxh.backend.controller.system;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.*;
import com.bdxh.system.entity.Permission;

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
    @ApiOperation(value="角色id查询用户菜单or按钮权限",response = PermissionTreeVo.class)
    public Object findPermissionByRoleId(@RequestParam(name = "roleId")Long roleId,
                                         @RequestParam(name = "type",defaultValue = "1") Byte type){
        try {
            Wrapper wrapper = permissionControllerClient.findPermissionByRoleId(roleId,type);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value="/permissionMenus",method = RequestMethod.POST)
    @ApiOperation(value="根据用户id查询权限列表",response = String.class)
    public Object permissionMenus(@RequestParam(name = "roleId")Long roleId){
        try {
            Wrapper wrapper = permissionControllerClient.permissionMenus(roleId);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value="/addPermission",method = RequestMethod.POST)
    @ApiOperation(value="添加权限菜单",response=Boolean.class)
    public Object addPermission(@Validated @RequestBody AddPermissionDto addPermissionDto){
        try {
            Wrapper wrapper = permissionControllerClient.addPermission(addPermissionDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value="/modifyPermission",method = RequestMethod.POST)
    @ApiOperation(value="修改权限菜单",response=Boolean.class)
    public Object modifyPermission(@Validated @RequestBody ModifyPermissionDto modifyPermissionDto){
        try {
            Wrapper wrapper = permissionControllerClient.modifyPermission(modifyPermissionDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value="/theTreeMenu",method = RequestMethod.GET)
    @ApiOperation(value="查询特定情况下菜单",response = PermissionTreeVo.class)
    public Object theTreeMenu( @RequestParam(value = "roleId") Long roleId){
        try {
            Wrapper wrapper = permissionControllerClient.theTreeMenu(roleId);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value="/thePermissionMenu",method = RequestMethod.GET)
    @ApiOperation(value="查询全部菜单权限",response = PermissionTreeVo.class)
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
    @ApiOperation(value="保存并修改权限",response = Boolean.class)
    public Object addOrUpdatePermission(@Validated @RequestBody BaPermissionsDto baPermissionsDto){
        try {
            Wrapper wrapper = permissionControllerClient.addOrUpdatePermission(baPermissionsDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value="/delPermissionById",method = RequestMethod.GET)
    @ApiOperation(value="删除单个菜单权限",response = Boolean.class)
    public Object delPermissionById(@RequestParam("id") Long id){
        try {
            Permission permission=permissionControllerClient.findPermissionByParentId(id).getResult();
         if (permission!=null){
             return WrapMapper.error("该节点下存在子节点不能删除");
         }
             Wrapper wrapper = permissionControllerClient.delPermissionById(id);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value="/findPermissionById",method = RequestMethod.GET)
    @ApiOperation(value="根据id查询菜单详情",response = Permission.class)
    public Object findPermissionById(@RequestParam("id") Long id){
        try {
            Wrapper wrapper = permissionControllerClient.findPermissionById(id);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value="/userPermissionMenu",method = RequestMethod.GET)
    @ApiOperation(value="当前用户所有菜单列表",response = PermissionTreeVo.class)
    public Object userPermissionMenu(@RequestParam("userId") Long userId){
        try {
            Wrapper<List<PermissionTreeVo>> wrapper = permissionControllerClient.userPermissionMenu(userId);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


}
