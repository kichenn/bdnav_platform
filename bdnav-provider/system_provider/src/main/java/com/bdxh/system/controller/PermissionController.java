package com.bdxh.system.controller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @Description:
 * @Author: Kang
 * @Date: 2019/2/28 19:49
 */
@RestController
@RequestMapping("/permission")
@Validated
@Slf4j
@Api(value = "权限操作菜单管理", tags = "权限操作菜单管理")
public class PermissionController {

    /**
     * @Description: 角色id查询用户菜单or按钮权限
     * @Author: Kang
     * @Date: 2019/2/28 19:55
     */
    @RequestMapping(value = "/findPermissionByRoleId", method = RequestMethod.GET)
    @ApiOperation(value = "角色id查询用户权限", response = Boolean.class)
    @ResponseBody
    public Object findPermissionByRoleId() {
        return WrapMapper.ok();
    }

    /**
     * @Description: 增加用户权限
     * @Author: Kang
     * @Date: 2019/2/28 19:56
     */
    @RequestMapping(value = "/addPermission", method = RequestMethod.POST)
    @ApiOperation(value = "新增用户权限", response = Boolean.class)
    @ResponseBody
    public Object addPermission() {
        return WrapMapper.ok();
    }

    /**
    * @Description:   修改用户权限
    * @Author: Kang
    * @Date: 2019/2/28 19:58
    */
    @RequestMapping(value = "/modifyPermission", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户权限", response = Boolean.class)
    @ResponseBody
    public Object modifyPermission() {
        return WrapMapper.ok();
    }

    /**
     * @Description: 删除用户权限
     * @Author: Kang
     * @Date: 2019/2/28 19:57
     */
    @RequestMapping(value = "/delPermissionById", method = RequestMethod.POST)
    @ApiOperation(value = "删除用户权限", response = Boolean.class)
    @ResponseBody
    public Object delPermission() {
        return WrapMapper.ok();
    }
}
