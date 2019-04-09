package com.bdxh.system.controller;

import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.dto.*;
import com.bdxh.system.entity.User;
import com.bdxh.system.entity.UserRole;
import com.bdxh.system.service.UserRoleService;
import com.bdxh.system.service.UserService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user")
@Validated
@Slf4j
@Api(value = "系统用户管理", tags = "系统用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 增加用户
     *
     * @param addUserDto
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "增加系统用户")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public Object addUser(@Validated @RequestBody AddUserDto addUserDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            User userData = userService.getByUserName(addUserDto.getUserName());
            Preconditions.checkArgument(userData == null, "用户名已经存在");
            Boolean result = userService.addUsers(addUserDto);
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 修改用户信息
     *
     * @param bindingResult
     * @return
     */
    @ApiOperation("修改用户信息")
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Object updateUser(@Validated @RequestBody UpdateUserDto updateUserDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Boolean result;
            User userData = userService.getByUserName(updateUserDto.getUserName());
           if (userData!=null){
               if(userData.getUserName().equals(updateUserDto.getUserName())&&!userData.getId().equals(updateUserDto.getId())){
                   return WrapMapper.error("用户名已经存在,请更换用户名称");
               }else{
                   result = userService.updateUsers(updateUserDto);
               }
           }else{
                result = userService.updateUsers(updateUserDto);
           }
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据id删除用户信息
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id删除用户信息")
    @RequestMapping(value = "/delUser", method = RequestMethod.GET)
    public Object delUser(@RequestParam(name = "id") Long id) {
        try {
            userService.delUser(id);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除用户信息
     *
     * @param ids
     * @return
     */
    @ApiOperation("删除用户信息")
    @RequestMapping(value = "/delBatchUser", method = RequestMethod.POST)
    public Object delBatchUser(@RequestParam(name = "ids") String ids) {
        try {
            String[] idsArr = StringUtils.split(ids, ",");
            List<Long> idsLongArr = new ArrayList<>(15);
            if (idsArr != null && idsArr.length > 0) {
                for (int i = 0; i < idsArr.length; i++) {
                    String id = idsArr[i];
                    if (StringUtils.isNotEmpty(id)) {
                        idsLongArr.add(Long.valueOf(id));
                    }
                }
            }
            userService.delBatchUser(idsLongArr);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 根据id查询用户对象
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id查询用户对象")
    @RequestMapping(value = "/queryUserById", method = RequestMethod.GET)
    public Object queryUser(@RequestParam(name = "id") Long id) {
        try {
            User user = userService.selectByKey(id);
            return WrapMapper.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }



    /**
     * 根据条件分页查找用户列表
     *
     * @return
     */
    @ApiOperation("根据条件分页查找用户")
    @RequestMapping(value = "/queryListPage", method = RequestMethod.POST)
    public Object queryListPage(@RequestBody UserQueryDto userQueryDto,BindingResult bindingResult) {
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(userQueryDto);
            PageInfo<UserQueryDto> Users = userService.findListPage(param,userQueryDto.getPageNum(),userQueryDto.getPageSize());
            return WrapMapper.ok(Users);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("用户名称查询用户信息")
    @RequestMapping(value = "/queryUserByUserName", method = RequestMethod.GET)
    public Object queryUserByUserName(@RequestParam("userName") String userName) {
        return WrapMapper.ok(userService.getByUserName(userName));
    }


    @RequestMapping(value = "/finaUserRoleByUserId", method = RequestMethod.GET)
    @ApiOperation(value = "根据用户id查询角色值", response = Boolean.class)
    public Object findUserRoleByUserId(@RequestParam(value = "userId") Long userId) {
        List<UserRole> result= userRoleService.findUserRoleOnly(userId);
        List<Long> roles = new ArrayList<>();
        result.stream().forEach(e -> {
            roles.add(e.getRoleId());
        });
        return WrapMapper.ok(roles);
    }


    @RequestMapping(value = "/enableAndDisable", method = RequestMethod.GET)
    @ApiOperation(value = "用户的启用与禁止", response = Boolean.class)
    public Object enableAndDisable(@RequestParam(value = "userId") Long userId,@RequestParam(name = "status") Byte status) {
        return WrapMapper.ok(userService.enableAndDisable(userId,status));
    }

    @RequestMapping(value = "/findUserRelationship", method = RequestMethod.GET)
    @ApiOperation(value = "查询当前用户所有权限菜单", response = Boolean.class)
    public Object findUserRelationship(@RequestParam(value = "userId") Long userId) {
        List<UserRole> result= userRoleService.findUserRoleByUserId(userId);
        return WrapMapper.ok(result);
    }




    @RequestMapping(value = "/test111", method = RequestMethod.GET)
    @ApiOperation(value = "测试专用", response = Boolean.class)
    public Object test111(@RequestParam(value = "userName") String userName) {
        User userData = userService.getByUserName(userName);
        Preconditions.checkArgument(userData == null, "用户名已经存在");
/*        List<UserRole> result= userRoleService.findUserRoleByUserId(userId);*/
        return WrapMapper.ok();
    }


}
