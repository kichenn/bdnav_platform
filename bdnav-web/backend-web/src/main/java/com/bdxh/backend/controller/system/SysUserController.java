package com.bdxh.backend.controller.system;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddUserDto;
import com.bdxh.system.dto.UpdateUserDto;
import com.bdxh.system.dto.UserQueryDto;
import com.bdxh.system.feign.UserControllerClient;
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
    @ApiOperation(value = "用户信息列表数据[分页筛选]")
    public Object findSchoolsInConditionPaging(@RequestBody UserQueryDto userQueryDto) {
        try {
        Wrapper wrapper = userControllerClient.queryListPage(userQueryDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户信息")
    public Object addUser(@RequestBody AddUserDto addUserDto) {
        try {
            Wrapper wrapper = userControllerClient.addUser(addUserDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户信息")
    public Object updateUser(@RequestBody UpdateUserDto updateUserDto) {
        try {
            Wrapper wrapper = userControllerClient.updateUser(updateUserDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value = "/delUser", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除用户信息")
    public Object delUser(@RequestParam(name = "id") Long id) {
        try {
            Wrapper wrapper = userControllerClient.delUser(id);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value = "/delBatchUser", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除用户信息")
    public Object delBatchUser(@RequestParam(name = "ids") String ids) {
        try {
            Wrapper wrapper = userControllerClient.delBatchUser(ids);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
