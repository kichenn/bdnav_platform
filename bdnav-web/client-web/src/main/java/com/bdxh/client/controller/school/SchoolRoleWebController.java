package com.bdxh.client.controller.school;

import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolRoleDto;
import com.bdxh.school.dto.ModifySchoolRoleDto;
import com.bdxh.school.dto.SchoolRoleQueryDto;
import com.bdxh.school.entity.SchoolRole;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolRoleControllerClient;
import com.bdxh.school.vo.SchoolRoleShowVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * @Description: 学校角色交互控制层
 * @Author: Kang
 * @Date: 2019/3/26 16:47
 */
@RestController
@RequestMapping("/clientSchoolRoleWeb")
@Validated
@Slf4j
@Api(value = "学校管理员--学校角色交互API", tags = "学校管理员--学校角色交互API")
public class SchoolRoleWebController {

    @Autowired
    private SchoolRoleControllerClient schoolRoleControllerClient;


    @RequestMapping(value = "/findRolesInConditionPage", method = RequestMethod.POST)
    @ApiOperation(value = "分页条件筛选查询当前学校角色信息", response = SchoolRole.class)
    public Object findRolesInConditionPage(@Validated @RequestBody SchoolRoleQueryDto roleQueryDto) {
        //获取当前用户
        SchoolUser user = SecurityUtils.getCurrentUser();
        roleQueryDto.setSchoolId(user.getSchoolId());
        Wrapper<PageInfo<SchoolRoleShowVo>> wrapper = schoolRoleControllerClient.findRolesInConditionPage(roleQueryDto);
        return WrapMapper.ok(wrapper.getResult());
    }


    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/addSchoolRole", method = RequestMethod.POST)
    @ApiOperation(value = "增加学校角色信息（需学校admin权限）", response = Boolean.class)
    public Object addSchoolRole(@Validated @RequestBody AddSchoolRoleDto addRoleDto) {
        //设置操作人
        SchoolUser user = SecurityUtils.getCurrentUser();
        addRoleDto.setOperator(user.getId());
        addRoleDto.setOperatorName(user.getUserName());
        addRoleDto.setSchoolCode(user.getSchoolCode());
        addRoleDto.setSchoolId(user.getSchoolId());
        Wrapper wrapper = schoolRoleControllerClient.addSchoolRole(addRoleDto);
        return wrapper;
    }

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/modifySchoolRole", method = RequestMethod.POST)
    @ApiOperation(value = "修改学校角色信息（需学校admin权限）", response = Boolean.class)
    public Object modifySchoolRole(@Validated @RequestBody ModifySchoolRoleDto ModifySchoolRoleDto) {
        //设置操作人
        SchoolUser user = SecurityUtils.getCurrentUser();
        ModifySchoolRoleDto.setOperator(user.getId());
        ModifySchoolRoleDto.setOperatorName(user.getUserName());
        ModifySchoolRoleDto.setSchoolId(user.getSchoolId());
        ModifySchoolRoleDto.setSchoolCode(user.getSchoolCode());
        Wrapper wrapper = schoolRoleControllerClient.modifySchoolRole(ModifySchoolRoleDto);
        return wrapper;
    }

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/delSchoolRole", method = RequestMethod.GET)
    @ApiOperation(value = "删除角色信息（需学校admin权限）", response = Boolean.class)
    public Object delSchoolRole(@RequestParam(name = "roleId") Long roleId) {
        Wrapper wrapper = schoolRoleControllerClient.delSchoolRole(roleId);
        return wrapper;
    }

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/delBatchSchoolRole", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除角色（需学校admin权限）", response = Boolean.class)
    public Object delBatchSchoolRole(@RequestParam(name = "ids") List<Long> ids) {
        Wrapper wrapper = schoolRoleControllerClient.delBatchSchoolRole(ids);
        return wrapper;
    }


}
