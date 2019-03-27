package com.bdxh.backend.controller.school;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolUserDto;
import com.bdxh.school.dto.ModifyUserDto;
import com.bdxh.school.dto.SchoolUserQueryDto;
import com.bdxh.school.entity.SchoolRole;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolUserControllerClient;
import com.bdxh.system.entity.User;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description: 学校用户交互
 * @Author: Kang
 * @Date: 2019/3/26 17:06
 */
@RestController
@RequestMapping("/schoolUserWebController")
@Validated
@Slf4j
@Api(value = "学校用户交互API", tags = "学校用户交互交互API")
public class SchoolUserWebController {

    @Autowired
    private SchoolUserControllerClient schoolUserControllerClient;


    @RequestMapping(value = "/findSchoolsInConditionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "学校用户信息列表数据[分页筛选]", response = SchoolRole.class)
    public Object findSchoolsInConditionPaging(@RequestBody SchoolUserQueryDto userQueryDto) {
        Wrapper<PageInfo<SchoolUser>> wrapper = schoolUserControllerClient.findSchoolUsersInConditionPage(userQueryDto);
        return WrapMapper.ok(wrapper.getResult());
    }


    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ApiOperation(value = "添加学校用户信息", response = Boolean.class)
    public Object addUser(@RequestBody AddSchoolUserDto addUserDto) {
        SchoolUser schoolUser = new SchoolUser();
        BeanUtils.copyProperties(addUserDto, schoolUser);
        //密码加密
        schoolUser.setPassword(new BCryptPasswordEncoder().encode(addUserDto.getPassword()));
        //设置操作人
        User user = SecurityUtils.getCurrentUser();
        schoolUser.setOperator(user.getId());
        schoolUser.setOperatorName(user.getUserName());
        Wrapper wrapper = schoolUserControllerClient.addSchoolUser(schoolUser);
        return wrapper;
    }

    @RequestMapping(value = "/modifySchoolUser", method = RequestMethod.POST)
    @ApiOperation(value = "修改学校用户信息", response = Boolean.class)
    public Object modifySchoolUser(@RequestBody ModifyUserDto modifyUserDto) {
        SchoolUser schoolUser = new SchoolUser();
        BeanUtils.copyProperties(modifyUserDto, schoolUser);
        //密码加密
        schoolUser.setPassword(new BCryptPasswordEncoder().encode(modifyUserDto.getPassword()));
        //设置操作人
        User user = SecurityUtils.getCurrentUser();
        schoolUser.setOperator(user.getId());
        schoolUser.setOperatorName(user.getUserName());
        Wrapper wrapper = schoolUserControllerClient.modifySchoolUser(schoolUser);
        return wrapper;
    }


    @RequestMapping(value = "/delSchoolUser", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除学校用户信息", response = Boolean.class)
    public Object delUser(@RequestParam(name = "id") Long id) {
        User user = SecurityUtils.getCurrentUser();
        if (id.equals(user.getId())) {
            //如果删除的id和当前登录的用户id相同，则不能删除
            return WrapMapper.error("不能删除当前登录账号");
        }
        Wrapper wrapper = schoolUserControllerClient.delSchoolUser(id);
        return wrapper;
    }


    @RequestMapping(value = "/delBatchSchoolUserInIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除学校用户信息", response = Boolean.class)
    public Object delBatchSchoolUserInIds(@RequestParam(name = "ids") List<Long> ids) {
        Wrapper wrapper = schoolUserControllerClient.delBatchSchoolUserInIds(ids);
        return wrapper;
    }

    @RequestMapping(value = "/modifySchoolUserStatusById", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户状态(启用或者禁用)", response = Boolean.class)
    public Object modifySchoolUserStatusById(@RequestParam(name = "id") Long id, @RequestParam(name = "status") Byte status) {
        Wrapper wrapper = schoolUserControllerClient.modifySchoolUserStatusById(id, status);
        return wrapper;
    }

}
