package com.bdxh.system.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddPermissionDto;
import com.bdxh.system.dto.AuRolePermissionDto;
import com.bdxh.system.dto.ModifyPermissionDto;
import com.bdxh.system.dto.RolePermissionDto;
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
@FeignClient(value = "system-provider-cluster",fallback= PermissionControllerClientFallback.class)
public interface PermissionControllerClient {

    /**
     * 根据用户id查询权限列表
     * @param userId
     * @return
     */
    @RequestMapping(value = "/permission/permissionMenus",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<String>> permissionMenus(Long userId);


    /**
     * 角色id查询用户菜单or按钮权限
     * @return
     */
    @RequestMapping(value = "/permission/findPermissionByRoleId",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<PermissionTreeVo>> findPermissionByRoleId(@RequestParam("roleId") Long roleId, @RequestParam("type") Byte type);

    /**
     * 添加权限信息
     * @return
     */
    @RequestMapping(value = "/permission/AddPermissionDto")
    @ResponseBody
    Wrapper AddPermissionDto(@RequestBody AddPermissionDto dto);

    /**
     * 修改权限信息
     * @return
     */
    @RequestMapping(value = "/permission/modifyPermission")
    @ResponseBody
    Wrapper modifyPermission(@RequestBody ModifyPermissionDto dto);


    /**
     * 查询全部菜单
     * @return
     */
    @RequestMapping(value = "/permission/theTreeMenu",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<PermissionTreeVo>> theTreeMenu(
            @RequestParam("roleId") Long roleId,
            @RequestParam(value = "selected") Integer selected);


    /**
     * 更改或保存权限
     * @return
     */
    @RequestMapping(value = "/permission/addorUpdatePermission",method = RequestMethod.POST)
    @ResponseBody
    Wrapper addorUpdatePermission(
            @RequestParam(value = "roleId") Long roleId,@RequestParam(value = "arpdDtos")String arpdDtos);

}
