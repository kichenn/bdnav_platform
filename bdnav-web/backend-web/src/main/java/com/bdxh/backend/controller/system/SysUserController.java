package com.bdxh.backend.controller.system;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddUserDto;
import com.bdxh.system.dto.UpdateUserDto;
import com.bdxh.system.dto.UserQueryDto;
import com.bdxh.system.entity.User;
import com.bdxh.system.feign.UserControllerClient;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 系统用户交互控制层
 */
@RestController
@RequestMapping("/sysUser")
@Validated
@Slf4j
@Api(value = "系统用户交互API", tags = "系统用户交互API")
public class SysUserController {

    @Autowired
    private UserControllerClient userControllerClient;


    @RequestMapping(value = "/findSchoolsInConditionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "用户信息列表数据[分页筛选]",response = PageInfo.class)
    public Object findSchoolsInConditionPaging(@Validated @RequestBody UserQueryDto userQueryDto) {
        try {
        Wrapper wrapper = userControllerClient.queryListPage(userQueryDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户信息",response = Boolean.class)
    public Object addUser(@Validated @RequestBody AddUserDto addUserDto) {
        try {
            Wrapper wrapper = userControllerClient.addUser(addUserDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户信息",response = Boolean.class)
    public Object updateUser(@Validated @RequestBody UpdateUserDto updateUserDto) {
        try {
            Wrapper wrapper = userControllerClient.updateUser(updateUserDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value = "/delUser", method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除用户信息",response = Boolean.class)
    public Object delUser(@RequestParam(name = "id") Long id) {
        try {
            User user = SecurityUtils.getCurrentUser();
            if (user.getId().equals(id)){
                return WrapMapper.error("当前用为正在登陆用户,不能删除");
            }else{
                Wrapper wrapper = userControllerClient.delUser(id);
                return wrapper;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value = "/delBatchUser", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除用户信息",response = Boolean.class)
    public Object delBatchUser(@RequestParam(name = "ids") String ids) {
        try {
            Wrapper wrapper = userControllerClient.delBatchUser(ids);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }





    @RequestMapping(value = "/findUserRoleByUserId", method = RequestMethod.GET)
    @ApiOperation(value = "根据用户id查询所有权限",response = Long.class)
    public Object findUserRoleByUserId(@RequestParam(value = "userId") Long userId) {
        try {

            Wrapper wrapper = userControllerClient.findUserRoleByUserId(userId);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value = "/enableAndDisable", method = RequestMethod.GET)
    @ApiOperation(value = "用户的启用与禁止",response = Boolean.class)
    public Object enableAndDisable(@RequestParam(value = "userId") Long userId,@RequestParam(name = "status") Byte status) {
        try {
            Wrapper wrapper = userControllerClient.enableAndDisable(userId,status);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


}
