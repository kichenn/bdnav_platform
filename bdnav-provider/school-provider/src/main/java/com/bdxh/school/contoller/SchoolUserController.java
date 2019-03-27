package com.bdxh.school.contoller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.SchoolUserQueryDto;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.service.SchoolUserService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/schoolUser")
@Validated
@Slf4j
@Api(value = "学校系统用户管理", tags = "学校系统用户管理")
public class SchoolUserController {

    @Autowired
    private SchoolUserService schoolUserService;


    /**
     * @Description: 增加学校系统用户
     * @Author: Kang
     * @Date: 2019/3/26 15:12
     */
    @ApiOperation(value = "增加学校系统用户", response = Boolean.class)
    @RequestMapping(value = "/addSchoolUser", method = RequestMethod.POST)
    public Object addSchoolUser(@Valid @RequestBody SchoolUser schoolUser) {
        SchoolUser userData = schoolUserService.getByUserName(schoolUser.getUserName());
        if (userData != null) {
            return WrapMapper.error("该用户名已经存在");
        }
        return WrapMapper.ok(schoolUserService.save(schoolUser) > 0);
    }


    /**
     * @Description: 修改学校用户信息
     * @Author: Kang
     * @Date: 2019/3/26 15:14
     */
    @ApiOperation(value = "修改学校用户信息", response = Boolean.class)
    @RequestMapping(value = "/modifySchoolUser", method = RequestMethod.POST)
    public Object modifySchoolUser(@Valid @RequestBody SchoolUser schoolUser) {
        return WrapMapper.ok(schoolUserService.update(schoolUser) > 0);
    }

    /**
     * @Description: id删除学校用户信息
     * @Author: Kang
     * @Date: 2019/3/26 15:16
     */
    @ApiOperation(value = "id删除学校用户信息")
    @RequestMapping(value = "/delSchoolUser", method = RequestMethod.POST)
    public Object delSchoolUser(@RequestParam(name = "id") Long id) {
        schoolUserService.delUser(id);
        return WrapMapper.ok();
    }

    /**
     * @Description: id批量删除用户
     * @Author: Kang
     * @Date: 2019/3/26 15:18
     */
    @ApiOperation(value = "id批量删除学校用户", response = Boolean.class)
    @RequestMapping(value = "/delBatchSchoolUserInIds", method = RequestMethod.POST)
    public Object delBatchSchoolUserInIds(@RequestParam(name = "ids") List<Long> ids) {
        return WrapMapper.ok(schoolUserService.delBatchSchoolUserInIds(ids));
    }

    /**
     * @Description: 根据id查询学校用户信息
     * @Author: Kang
     * @Date: 2019/3/26 15:26
     */
    @ApiOperation(value = "根据id查询学校用户信息", response = SchoolUser.class)
    @RequestMapping(value = "/findSchoolUserById", method = RequestMethod.GET)
    public Object findSchoolUserById(@RequestParam(name = "id") Long id) {
        SchoolUser user = schoolUserService.selectByKey(id);
        return WrapMapper.ok(user);
    }

    /**
     * @Description: 根据条件分页查找学校系统用户
     * @Author: Kang
     * @Date: 2019/3/26 15:28
     */
    @ApiOperation(value = "根据条件分页查找学校系统用户", response = PageInfo.class)
    @RequestMapping(value = "/findSchoolUsersInConditionPage", method = RequestMethod.POST)
    public Object findSchoolUsersInConditionPage(@RequestBody SchoolUserQueryDto schoolUserQueryDto) {
        PageInfo<SchoolUser> Users = schoolUserService.findListPage(schoolUserQueryDto);
        return WrapMapper.ok(Users);
    }

    /**
     * @Description: 用户名称查询用户信息
     * @Author: Kang
     * @Date: 2019/3/26 15:39
     */
    @ApiOperation(value = "用户名称查询用户信息", response = SchoolUser.class)
    @RequestMapping(value = "/findSchoolUserByName", method = RequestMethod.GET)
    public Object findSchoolUserByName(@RequestParam("userName") String userName) {
        return WrapMapper.ok(schoolUserService.getByUserName(userName));
    }

    @ApiOperation(value = "修改用户状态(启用或者禁用)", response = Boolean.class)
    @RequestMapping(value = "/modifySchoolUserStatusById", method = RequestMethod.POST)
    public Object modifySchoolUserStatusById(@RequestParam(name = "id") Long id, @RequestParam(name = "status") Byte status) {
        return WrapMapper.ok(schoolUserService.modifySchoolUserStatusById(id, status));
    }

}
