package com.bdxh.system.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.dto.*;
import com.bdxh.system.entity.User;
import com.bdxh.system.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user")
@Validated
@Slf4j
@Api(value = "系统用户相关API", tags = "系统用户管理")
public class UserController {

      @Autowired
      private UserService userService;


    /**
     * 增加用户
     * @param userDto
     * @param bindingResult
     * @return
     */
      @RequestMapping(value = "/addUser",method = RequestMethod.POST)
      public Object addUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){
          //检验参数
          if(bindingResult.hasErrors()){
              String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
              return WrapMapper.error(errors);
          }
          try {
              User user = BeanMapUtils.map(userDto, User.class);
              userService.save(user);
              return WrapMapper.ok();
          } catch (Exception e) {
              e.printStackTrace();
              return WrapMapper.error(e.getMessage());
          }
      }


    /**
     * 修改用户信息
     * @param bindingResult
     * @return
     */
    @ApiOperation("修改角色信息")
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public Object updateUser(@Valid @RequestBody UserDto userDto,BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            User user = BeanMapUtils.map(userDto, User.class);
            userService.update(user);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 根据id查询用户对象
     * @param id
     * @return
     */
    @ApiOperation("根据id查询用户对象")
    @RequestMapping(value = "/queryUserById",method = RequestMethod.GET)
    public Object queryUser(@RequestParam(name = "id") @NotNull(message = "角色id不能为空") Long id){
        try {
            User user = userService.selectByKey(id);
            return WrapMapper.ok(user);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据条件查询列表
     * @return
     */
    @ApiOperation("根据条件查询列表")
    @RequestMapping(value = "/queryList",method = RequestMethod.GET)
    public Object queryList(@Valid @RequestBody UserQueryDto userQueryDto){
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(userQueryDto);
            List<User> Users = userService.findList(param);
            return WrapMapper.ok(Users);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据条件分页查找
     * @param userQueryDto
     * @return
     */
    @ApiOperation("根据条件分页查找")
    @RequestMapping(value = "/queryListPage",method = RequestMethod.GET)
    public Object queryListPage(@Valid @RequestBody UserQueryDto userQueryDto){
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(userQueryDto);
            PageInfo<User> Users = userService.findListPage(param, userQueryDto.getPageNum(),userQueryDto.getPageSize());
            return WrapMapper.ok(Users);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }




}
