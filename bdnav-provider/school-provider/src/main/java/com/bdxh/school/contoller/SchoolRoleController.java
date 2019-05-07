package com.bdxh.school.contoller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.AddSchoolRoleDto;
import com.bdxh.school.dto.ModifySchoolRoleDto;
import com.bdxh.school.dto.SchoolRoleQueryDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.entity.SchoolRole;
import com.bdxh.school.service.SchoolRoleService;
import com.bdxh.school.service.SchoolService;
import com.bdxh.school.vo.SchoolRoleInfoVo;
import com.bdxh.school.vo.SchoolRoleShowVo;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 学校角色管理控制器
 * @Author: Kang
 * @Date: 2019/3/26 14:32
 */
@RestController
@RequestMapping("/schoolRole")
@Validated
@Slf4j
@Api(value = "学校角色管理", tags = "学校角色管理")
public class SchoolRoleController {

    @Autowired
    private SchoolRoleService schoolRoleService;

    @Autowired
    private SchoolService schoolService;

    /**
     * @Description: 添加学校角色信息
     * @Author: Kang
     * @Date: 2019/3/26 14:37
     */
    @ApiOperation(value = "添加学校角色信息", response = Boolean.class)
    @RequestMapping(value = "/addSchoolRole", method = RequestMethod.POST)
    public Object addSchoolRole(@Validated @RequestBody AddSchoolRoleDto addSchoolRoleDto) {
        SchoolRole roleData = schoolRoleService.getRoleByRole(addSchoolRoleDto.getRole());
        if (roleData != null) {
            return WrapMapper.error("角色已经存在");
        }
        SchoolRole schoolRole = new SchoolRole();
        BeanUtils.copyProperties(addSchoolRoleDto, schoolRole);
        return WrapMapper.ok(schoolRoleService.save(schoolRole) > 0);
    }

    /**
     * @Description: 修改学校角色信息
     * @Author: Kang
     * @Date: 2019/3/26 14:39
     */
    @ApiOperation(value = "修改学校角色信息", response = Boolean.class)
    @RequestMapping(value = "/modifySchoolRole", method = RequestMethod.POST)
    public Object modifySchoolRole(@Validated @RequestBody ModifySchoolRoleDto ModifySchoolRoleDto) {
        SchoolRole schoolRole = new SchoolRole();
        BeanUtils.copyProperties(ModifySchoolRoleDto, schoolRole);
        return WrapMapper.ok((schoolRoleService.update(schoolRole) > 0));
    }

    /**
     * @Description: 根据id删除角色
     * @Author: Kang
     * @Date: 2019/3/26 14:40
     */
    @ApiOperation(value = "根据id删除角色", response = Boolean.class)
    @RequestMapping(value = "/delSchoolRole", method = RequestMethod.POST)
    public Object delSchoolRole(@RequestParam(name = "schoolRoleId") Long schoolRoleId) {
        schoolRoleService.delRole(schoolRoleId);
        return WrapMapper.ok();
    }

    /**
     * 根据ids批量删除角色
     *
     * @param ids
     * @return
     */
    @ApiOperation("根据ids批量删除角色")
    @RequestMapping(value = "/delBatchSchoolRole", method = RequestMethod.POST)
    public Object delBatchSchoolRole(@RequestParam(name = "ids") List<Long> ids) {
        schoolRoleService.delBatchRole(ids);
        return WrapMapper.ok();
    }

    /**
     * @Description: 根据id查询角色信息
     * @Author: Kang
     * @Date: 2019/3/26 14:59
     */
    @ApiOperation(value = "根据id查询角色信息", response = SchoolRole.class)
    @RequestMapping(value = "/findSchoolRoleById", method = RequestMethod.GET)
    public Object findSchoolRoleById(@RequestParam(name = "id") Long id) {
        SchoolRole role = schoolRoleService.selectByKey(id);

        SchoolRoleInfoVo schoolRoleInfoVo = new SchoolRoleInfoVo();
        BeanUtils.copyProperties(role, schoolRoleInfoVo);

        if (schoolRoleInfoVo != null) {
            School school = schoolService.findSchoolById(schoolRoleInfoVo.getSchoolId()).orElse(new School());
            schoolRoleInfoVo.setSchoolName(school.getSchoolName());
        }
        return WrapMapper.ok(schoolRoleInfoVo);
    }

    /**
     * @Description: 根据用户id查询角色列表
     * @Author: Kang
     * @Date: 2019/3/26 15:00
     */
    @ApiOperation(value = "根据用户id查询角色列表", response = String.class)
    @RequestMapping(value = "/findSchoolRolesByUserId", method = RequestMethod.GET)
    public Object findSchoolRolesByUserId(@RequestParam(name = "userId") Long userId) {
        List<String> roles = schoolRoleService.getRoleListByUserId(userId);
        return WrapMapper.ok(roles);
    }

    /**
     * @Description: 用户id查询，角色id
     * @Author: Kang
     * @Date: 2019/5/7 15:32
     */
    @ApiOperation(value = "用户id查询，角色id列表", response = String.class)
    @RequestMapping(value = "/getRoleIdListByUserId", method = RequestMethod.GET)
    public Object getRoleIdListByUserId(@RequestParam(name = "userId") Long userId) {
        List<Long> result = schoolRoleService.getRoleIdListByUserId(userId);

        return WrapMapper.ok(result);
    }

    /**
     * @Description: 根据条件查询角色列表 并分页
     * @Author: Kang
     * @Date: 2019/3/26 15:07
     */
    @ApiOperation(value = "根据条件查询角色列表并分页", response = PageInfo.class)
    @RequestMapping(value = "/findRolesInConditionPage", method = RequestMethod.POST)
    public Object findRolesInConditionPage(@Validated @RequestBody SchoolRoleQueryDto roleQueryDto) {
        PageInfo<SchoolRoleShowVo> Roles = schoolRoleService.findListPage(roleQueryDto);
        return WrapMapper.ok(Roles);
    }

}
