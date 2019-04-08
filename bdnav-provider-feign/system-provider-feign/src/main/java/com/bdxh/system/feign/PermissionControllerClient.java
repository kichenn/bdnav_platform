package com.bdxh.system.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.*;
import com.bdxh.system.entity.Permission;
import com.bdxh.system.fallback.PermissionControllerClientFallback;
import com.bdxh.system.vo.PermissionTreeVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 系统权限feign客户端
 * @author: xuyuan
 * @create: 2019-02-28 12:29
 **/
@Service
@FeignClient(value = "system-provider-cluster", fallback = PermissionControllerClientFallback.class)
public interface PermissionControllerClient {

    /**
     * 根据角色id查询权限列表（菜单）
     *
     * @return
     */
    @RequestMapping(value = "/permission/permissionMenus", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<List<String>> permissionMenus(Long roleId);

    /**
     * 根据用户id查询权限列表(菜单 and  按钮)
     *
     * @return
     */
    @RequestMapping(value = "/permission/permissionMenusByUserId", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<String>> permissionMenusByUserId(@RequestParam("userId") Long userId);

    /**
     * 角色id查询用户菜单or按钮权限
     *
     * @return
     */
    @RequestMapping(value = "/permission/findPermissionByRoleId", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<PermissionTreeVo>> findPermissionByRoleId(@RequestParam("roleId") Long roleId, @RequestParam("type") Byte type);

    /**
     * 添加权限信息
     *
     * @return
     */
    @RequestMapping(value = "/permission/addPermission")
    @ResponseBody
    Wrapper addPermission(@RequestBody AddPermissionDto addPermissionDto);

    /**
     * 修改权限信息
     *
     * @return
     */
    @RequestMapping(value = "/permission/modifyPermission")
    @ResponseBody
    Wrapper modifyPermission(@RequestBody ModifyPermissionDto modifyPermissionDto);


    /**
     * 查询全部菜单
     *
     * @return
     */
    @RequestMapping(value = "/permission/theTreeMenu", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<PermissionTreeVo>> theTreeMenu(
            @RequestParam("roleId") Long roleId);


    /**
     * 更改或保存权限
     *
     * @return
     */
    @RequestMapping(value = "/permission/addOrUpdatePermission", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addOrUpdatePermission(@RequestBody BaPermissionsDto baPermissionsDto);


    /**
     * 全部菜单权限
     *
     * @return
     */
    @RequestMapping(value = "/permission/thePermissionMenu")
    @ResponseBody
    Wrapper<List<PermissionTreeVo>> thePermissionMenu();

    /**
     * 删除单个权限
     */
    @RequestMapping(value = "/permission/delPermissionById")
    @ResponseBody
    Wrapper delPermissionById(@RequestParam("id") Long id);

    /**
     * 根据id查询菜单详情
     */
    @RequestMapping(value = "/permission/findPermissionById")
    @ResponseBody
    Wrapper<Permission> findPermissionById(@RequestParam("id") Long id);

    /**
     * 父id查询部门信息
     *
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/permission/findPermissionByParentId")
    @ResponseBody
    Wrapper<Permission> findPermissionByParentId(@RequestParam("parentId") Long parentId);

    /**
     * 查询当前用户权限
     *
     * @return
     */
    @RequestMapping(value = "/permission/userPermissionMenu", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<PermissionTreeVo>> userPermissionMenu(@RequestParam("userId") Long userId);


}
