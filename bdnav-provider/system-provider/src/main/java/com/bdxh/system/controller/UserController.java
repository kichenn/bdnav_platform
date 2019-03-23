package com.bdxh.system.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.dto.*;
import com.bdxh.system.entity.User;
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

import javax.validation.Valid;
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

    /**
     * 增加用户
     *
     * @param addUserDto
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "增加系统用户")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public Object addUser(@Valid @RequestBody AddUserDto addUserDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            User userData = userService.getByUserName(addUserDto.getUserName());
            Preconditions.checkArgument(userData == null, "用户名已经存在");
            User user = BeanMapUtils.map(addUserDto, User.class);
            userService.save(user);

            return WrapMapper.ok();
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
    public Object updateUser(@Valid @RequestBody UpdateUserDto updateUserDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            User user = BeanMapUtils.map(updateUserDto, User.class);
            userService.update(user);
            return WrapMapper.ok();
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
    @RequestMapping(value = "/delUser", method = RequestMethod.POST)
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
    public Object queryListPage(@RequestBody UserQueryDto userQueryDto) {
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(userQueryDto);
            PageInfo<User> Users = userService.findListPage(param,userQueryDto.getPageNum(),userQueryDto.getPageSize());
            return WrapMapper.ok(Users);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("用户名称查询用户信息")
    @RequestMapping(value = "/queryUserByUserName", method = RequestMethod.GET)
    @ResponseBody
    public Object queryUserByUserName(@RequestParam("userName") String userName) {
        return WrapMapper.ok(userService.getByUserName(userName));
    }


}
