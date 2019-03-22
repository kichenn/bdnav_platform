package com.bdxh.backend.controller.system;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddRoleDto;
import com.bdxh.system.dto.UpdateRoleDto;
import com.bdxh.system.feign.PermissionControllerClient;
import com.bdxh.system.feign.RoleControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统角色交互控制层
 */
@RestController
@RequestMapping("/sysRole")
@Validated
@Slf4j
@Api(value = "系统角色交互API", tags = "系统角色交互API")
public class SysRoleController {

    @Autowired
    private RoleControllerClient roleControllerClient;

    @Autowired
    private PermissionControllerClient permissionControllerClient;

    @RequestMapping(value="/findPageRoleListAll",method = RequestMethod.GET)
    @ApiOperation("分页查询全部角色信息")
    public Object findPageRoleListAll(@RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum,
                                   @RequestParam(name = "pageSize",defaultValue = "10")Integer pageSize){
        try {
            Wrapper wrapper = roleControllerClient.findPageRoleListAll(pageNum,pageSize);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }



    @RequestMapping(value="/addRole",method = RequestMethod.POST)
    @ApiOperation("增加角色信息")
    public Object addRole(@RequestBody AddRoleDto addRoleDto){
        try {
            Wrapper wrapper = roleControllerClient.addRole(addRoleDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }



    @RequestMapping(value="/updateRole",method = RequestMethod.POST)
    @ApiOperation("修改角色信息")
    public Object updateRole(@RequestBody UpdateRoleDto updateRoleDto){
        try {
            Wrapper wrapper = roleControllerClient.updateRole(updateRoleDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value="/updateRSwitchRole",method = RequestMethod.GET)
    @ApiOperation("修改角色启用状态")
    public Object updateRSwitchRole(@RequestParam(name = "roleId") Long roleId,
                     @RequestParam(name = "rswitch")Integer rswitch){
        try {
            Wrapper wrapper = roleControllerClient.updateRSwitchRole(roleId,rswitch);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }



    @RequestMapping(value="/delRole",method = RequestMethod.GET)
    @ApiOperation("删除角色信息")
    public Object delRole(@RequestParam(name = "roleId") Long roleId){
        try {
            Wrapper wrapper = roleControllerClient.delRole(roleId);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }



    @RequestMapping(value="/delBatchRole",method = RequestMethod.POST)
    @ApiOperation("批量删除角色")
    public Object delBatchRole(@RequestParam(name = "ids") String ids){
        try {
            Wrapper wrapper = roleControllerClient.delBatchRole(ids);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


}
