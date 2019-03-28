package com.bdxh.backend.controller.school;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolRoleDto;
import com.bdxh.school.dto.SchoolRoleQueryDto;
import com.bdxh.school.entity.SchoolRole;
import com.bdxh.school.feign.SchoolRoleControllerClient;
import com.bdxh.system.dto.UpdateRoleDto;
import com.bdxh.system.entity.User;
import com.bdxh.system.feign.PermissionControllerClient;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 学校角色交互控制层
 * @Author: Kang
 * @Date: 2019/3/26 16:47
 */
@RestController
@RequestMapping("/schoolRoleWebController")
@Validated
@Slf4j
@Api(value = "学校角色交互API", tags = "学校角色交互API")
public class SchoolRoleWebController {

    @Autowired
    private SchoolRoleControllerClient schoolRoleControllerClient;


    @RequestMapping(value = "/findRolesInConditionPage", method = RequestMethod.POST)
    @ApiOperation(value = "分页条件筛选查询学校角色信息", response = SchoolRole.class)
    public Object findRolesInConditionPage(@RequestBody SchoolRoleQueryDto roleQueryDto) {
        Wrapper<PageInfo<SchoolRole>> wrapper = schoolRoleControllerClient.findRolesInConditionPage(roleQueryDto);
        return WrapMapper.ok(wrapper.getResult());
    }


    @RequestMapping(value = "/addSchoolRole", method = RequestMethod.POST)
    @ApiOperation(value = "增加学校角色信息", response = Boolean.class)
    public Object addSchoolRole(@RequestBody AddSchoolRoleDto addRoleDto) {
        SchoolRole schoolRole = new SchoolRole();
        BeanUtils.copyProperties(addRoleDto, schoolRole);
        //设置操作人
        User user = SecurityUtils.getCurrentUser();
        schoolRole.setOperator(user.getId());
        schoolRole.setOperatorName(user.getUserName());
        Wrapper wrapper = schoolRoleControllerClient.addSchoolRole(schoolRole);
        return wrapper;
    }


    @RequestMapping(value = "/modifySchoolRole", method = RequestMethod.POST)
    @ApiOperation(value = "修改学校角色信息", response = Boolean.class)
    public Object modifySchoolRole(@RequestBody UpdateRoleDto updateRoleDto) {
        SchoolRole schoolRole = new SchoolRole();
        BeanUtils.copyProperties(updateRoleDto, schoolRole);
        //设置操作人
        User user = SecurityUtils.getCurrentUser();
        schoolRole.setOperator(user.getId());
        schoolRole.setOperatorName(user.getUserName());
        Wrapper wrapper = schoolRoleControllerClient.modifySchoolRole(schoolRole);
        return wrapper;
    }

    @RequestMapping(value = "/delSchoolRole", method = RequestMethod.GET)
    @ApiOperation(value = "删除角色信息", response = Boolean.class)
    public Object delSchoolRole(@RequestParam(name = "roleId") Long roleId) {
        Wrapper wrapper = schoolRoleControllerClient.delSchoolRole(roleId);
        return wrapper;
    }


    @RequestMapping(value = "/delBatchSchoolRole", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除角色", response = Boolean.class)
    public Object delBatchSchoolRole(@RequestParam(name = "ids") List<Long> ids) {
        Wrapper wrapper = schoolRoleControllerClient.delBatchSchoolRole(ids);
        return wrapper;
    }


}
