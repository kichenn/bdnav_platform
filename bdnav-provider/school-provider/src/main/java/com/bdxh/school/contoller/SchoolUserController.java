package com.bdxh.school.contoller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolUserDto;
import com.bdxh.school.dto.ModifySchoolUserDto;
import com.bdxh.school.dto.SchoolUserQueryDto;
import com.bdxh.school.dto.ShowSchoolUserModifyPrefixDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.entity.SchoolDept;
import com.bdxh.school.entity.SchoolRole;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.enums.SchoolUserStatusEnum;
import com.bdxh.school.service.SchoolDeptService;
import com.bdxh.school.service.SchoolRoleService;
import com.bdxh.school.service.SchoolService;
import com.bdxh.school.service.SchoolUserService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/schoolUser")
@Validated
@Slf4j
@Api(value = "学校系统用户管理", tags = "学校系统用户管理")
public class SchoolUserController {

    @Autowired
    private SchoolUserService schoolUserService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private SchoolDeptService schoolDeptService;

    @Autowired
    private SchoolRoleService schoolRoleService;


    /**
     * @Description: 增加学校系统用户
     * @Author: Kang
     * @Date: 2019/3/26 15:12
     */
    @ApiOperation(value = "增加学校系统用户", response = Boolean.class)
    @RequestMapping(value = "/addSchoolUser", method = RequestMethod.POST)
    public Object addSchoolUser(@Validated @RequestBody AddSchoolUserDto addSchoolUserDto) {
        SchoolUser userData = schoolUserService.getByUserName(addSchoolUserDto.getUserName());
        if (userData != null) {
            return WrapMapper.error("该用户名已经存在");
        }
        SchoolUser schoolUser = new SchoolUser();
        BeanUtils.copyProperties(addSchoolUserDto, schoolUser);
        //设置类型值
        schoolUser.setStatus(addSchoolUserDto.getSchoolUserStatusEnum().getKey());
        schoolUser.setType(addSchoolUserDto.getSchoolUserTypeEnum().getKey());
        schoolUser.setSex(addSchoolUserDto.getSchoolUserSexEnum().getKey());
        return WrapMapper.ok(schoolUserService.save(schoolUser) > 0);
    }


    /**
     * @Description: 修改学校用户信息
     * @Author: Kang
     * @Date: 2019/3/26 15:14
     */
    @ApiOperation(value = "修改学校用户信息", response = Boolean.class)
    @RequestMapping(value = "/modifySchoolUser", method = RequestMethod.POST)
    public Object modifySchoolUser(@Validated @RequestBody ModifySchoolUserDto modifySchoolUserDto) {
        schoolUserService.modifySchoolUser(modifySchoolUserDto);
        return WrapMapper.ok();
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
    @ApiOperation(value = "根据id查询学校用户信息", response = ShowSchoolUserModifyPrefixDto.class)
    @RequestMapping(value = "/findSchoolUserById", method = RequestMethod.GET)
    public Object findSchoolUserById(@RequestParam(name = "id") Long id) {
        SchoolUser user = schoolUserService.selectByKey(id);

        //组装数据
        ShowSchoolUserModifyPrefixDto ssumpd = new ShowSchoolUserModifyPrefixDto();
        BeanUtils.copyProperties(user, ssumpd);
        School school = schoolService.findSchoolById(ssumpd.getSchoolId()).orElse(new School());
        SchoolDept schoolDept = schoolDeptService.selectByKey(ssumpd.getDeptId());
        ssumpd.setSchoolName(school.getSchoolName());
        ssumpd.setDeptName(schoolDept != null ? schoolDept.getName() : "");
        Map<Long, String> map = schoolRoleService.findRoleByUserIdResultMap(ssumpd.getId());
        if (map != null) {
            ssumpd.setRoles(map.toString());
        }
        return WrapMapper.ok(ssumpd);
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
    public Object modifySchoolUserStatusById(@RequestParam(name = "id") Long id, @RequestParam(name = "statusEnum") SchoolUserStatusEnum statusEnum) {
        return WrapMapper.ok(schoolUserService.modifySchoolUserStatusById(id, statusEnum.getKey()));
    }

}
