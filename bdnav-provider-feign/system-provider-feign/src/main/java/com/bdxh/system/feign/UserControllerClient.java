package com.bdxh.system.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.*;
import com.bdxh.system.entity.User;
import com.bdxh.system.entity.UserRole;
import com.bdxh.system.fallback.UserControllerClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 系统用户feign客户端
 * @author: xuyuan
 * @create: 2019-02-28 12:29
 **/
@Service
@FeignClient(value = "system-provider-cluster", fallback = UserControllerClientFallback.class)
public interface UserControllerClient {

    /**
     * 根据用户名查询用户对象
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "/user/queryUserByUserName", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<User> queryUserByUserName(@RequestParam("userName") String userName);

    /**
     * 添加系统用户
     * @return
     */
    @RequestMapping(value = "/user/addUser")
    @ResponseBody
    Wrapper addUser(@RequestBody AddUserDto addUserDto);

    /**
     * 修改系统用户
     * @return
     */
    @RequestMapping(value = "/user/updateUser")
    @ResponseBody
    Wrapper updateUser(@RequestBody UpdateUserDto updateUserDto);


    /**
     *  查询用户列表
     */
    @RequestMapping(value = "/user/queryListPage")
    @ResponseBody
    Wrapper queryListPage(@RequestBody UserQueryDto userQueryDto);

    /**
     * 删除系统用户
     * @return
     */
    @RequestMapping(value = "/user/delUser")
    @ResponseBody
    Wrapper delUser(@RequestParam(name = "id") Long id);



    /**
     * 批量删除系统用户
     * @return
     */
    @RequestMapping(value = "/user/delBatchUser")
    @ResponseBody
    Wrapper delBatchUser(@RequestParam(name = "ids") String ids);
    
    /**
     * 根据用户id查询所有权限
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/finaUserRoleByUserId")
    @ResponseBody
    Wrapper<List<Long>> findUserRoleByUserId(@RequestParam(value = "userId") Long userId);


    /**
     * 用户的启用与关闭
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/enableAndDisable")
    @ResponseBody
    Wrapper enableAndDisable(@RequestParam(value = "userId") Long userId,@RequestParam(name = "status") Byte status);

    /**
     * 查询当前用户所有权限菜单
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/findUserRelationship")
    @ResponseBody
    Wrapper<List<UserRole>> findUserRelationship(@RequestParam(value = "userId") Long userId);




}
