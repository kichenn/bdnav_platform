package com.bdxh.backend.controller.system;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddPermissionDto;
import com.bdxh.system.dto.AuRolePermissionDto;
import com.bdxh.system.dto.ModifyPermissionDto;
import com.bdxh.system.dto.RolePermissionDto;
import com.bdxh.system.feign.PermissionControllerClient;
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


    @RequestMapping(value="/AddPermissionDto",method = RequestMethod.POST)
    @ApiOperation("添加权限菜单")
    public Object AddPermissionDto(@RequestBody AddPermissionDto addPermissionDto){
        try {
            Wrapper wrapper = permissionControllerClient.AddPermissionDto(addPermissionDto);
            return WrapMapper.ok(wrapper.getResult());
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
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value="/theTreeMenu",method = RequestMethod.GET)
    @ApiOperation("查询全部菜单权限")
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



    @RequestMapping(value="/addorUpdatePermission",method = RequestMethod.POST)
    @ApiOperation("保存并修改权限")
    public Object addorUpdatePermission(   @RequestParam(value = "roleId") Long roleId,@RequestParam(value = "arpdDtos")String arpdDtos){
        try {
            Wrapper wrapper = permissionControllerClient.addorUpdatePermission(roleId,arpdDtos);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


}
