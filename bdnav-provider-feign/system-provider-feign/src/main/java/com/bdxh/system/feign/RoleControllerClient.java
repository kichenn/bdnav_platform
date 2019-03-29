package com.bdxh.system.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddRoleDto;
import com.bdxh.system.dto.UpdateRoleDto;
import com.bdxh.system.fallback.RoleControllerClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 系统角色feign客户端
 * @author: xuyuan
 * @create: 2019-02-28 12:29
 **/
@Service
@FeignClient(value = "system-provider-cluster",fallback= RoleControllerClientFallback.class)
public interface RoleControllerClient {

    /**
     * 根据用户id查询角色列表
     * @param userId
     * @return
     */
    @RequestMapping(value = "/role/queryRoleListByUserId",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<String>> queryRoleListByUserId(@RequestParam("userId") Long userId);

    /**
     *  分页查询角色信息
     * @return
     */
    @RequestMapping(value = "/role/findPageRoleListAll")
    @ResponseBody
    Wrapper findPageRoleListAll(@RequestParam(name = "pageNum")Integer pageNum,
                                              @RequestParam(name = "pageSize")Integer pageSize);

    /**
     *  增加角色管理
     * @return
     */
    @RequestMapping(value = "/role/addRole")
    @ResponseBody
    Wrapper addRole(@RequestBody AddRoleDto addRoleDto);


    /**
     *  修改角色管理信息
     * @return
     */
    @RequestMapping(value = "/role/updateRole")
    @ResponseBody
    Wrapper updateRole(@RequestBody UpdateRoleDto updateRoleDto);


    /**
     *  删除角色管理信息
     * @return
     */
    @RequestMapping(value = "/role/delRole")
    @ResponseBody
    Wrapper delRole(@RequestParam(name = "roleId") Long roleId);


    /**
     *  批量删除管理信息
     * @return
     */
    @RequestMapping(value = "/role/delBatchRole")
    @ResponseBody
    Wrapper delBatchRole(@RequestParam(name = "ids") String ids);



}
