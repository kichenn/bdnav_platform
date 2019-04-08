package com.bdxh.school.contoller;

import com.bdxh.common.helper.tree.utils.TreeLoopUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.AddRolePermissionBindMenuDto;
import com.bdxh.school.dto.AddSchoolPermissionDto;
import com.bdxh.school.dto.ModifySchoolPermissionDto;
import com.bdxh.school.entity.SchoolPermission;
import com.bdxh.school.service.SchoolPermissionService;
import com.bdxh.school.service.SchoolRolePermissionService;
import com.bdxh.school.vo.SchoolPermissionTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description: 学校权限操作菜单
 * @Author: Kang
 * @Date: 2019/3/26 14:13
 */
@RestController
@RequestMapping("/schoolPermission")
@Validated
@Slf4j
@Api(value = "学校权限操作菜单管理", tags = "学校权限操作菜单管理")
public class SchoolPermissionController {

    @Autowired
    private SchoolPermissionService schoolPermissionService;

    @Autowired
    private SchoolRolePermissionService schoolRolePermissionService;

    /**
     * @Description: 学校 角色id查询用户菜单or按钮权限
     * @Author: Kang
     * @Date: 2019/3/26 14:29
     */
    @GetMapping("/findSchoolPermissionByRoleId")
    @ApiOperation(value = "学校角色id查询用户菜单or按钮权限", response = List.class)
    public Object findPermissionByRoleId(@RequestParam("roleId") Long roleId, @RequestParam("type") Byte type, @RequestParam(name = "schoolId", required = false) Long schoolId) {
        List<SchoolPermission> permissions = schoolPermissionService.findPermissionByRoleId(roleId, type, schoolId);
        List<SchoolPermissionTreeVo> treeVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(permissions)) {
            permissions.stream().forEach(e -> {
                SchoolPermissionTreeVo treeVo = new SchoolPermissionTreeVo();
                treeVo.setTitle(e.getName());
                treeVo.setCreateDate(e.getCreateDate());
                BeanUtils.copyProperties(e, treeVo);
                treeVos.add(treeVo);
            });
        }
        TreeLoopUtils<SchoolPermissionTreeVo> treeLoopUtils = new TreeLoopUtils<>();
        List<SchoolPermissionTreeVo> result = treeLoopUtils.getTree(treeVos);
        return WrapMapper.ok(result);
    }


    /**
     * @Description: 菜单or按钮权限列表
     * @Author: Kang
     * @Date: 2019/3/26 14:29
     */
    @GetMapping("/findPermissionList")
    @ApiOperation(value = "菜单or按钮权限列表", response = List.class)
    public Object findPermissionList() {
        List<SchoolPermission> permissions = schoolPermissionService.selectAll();
        List<SchoolPermissionTreeVo> treeVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(permissions)) {
            permissions.stream().forEach(e -> {
                SchoolPermissionTreeVo treeVo = new SchoolPermissionTreeVo();
                treeVo.setTitle(e.getName());
                treeVo.setCreateDate(e.getCreateDate());
                BeanUtils.copyProperties(e, treeVo);
                treeVos.add(treeVo);
            });
        }
        TreeLoopUtils<SchoolPermissionTreeVo> treeLoopUtils = new TreeLoopUtils<>();
        List<SchoolPermissionTreeVo> result = treeLoopUtils.getTree(treeVos);
        return WrapMapper.ok(result);
    }

    /**
     * @Description: 菜单or按钮权限列表(根据学校id)
     * @Author: Kang
     * @Date: 2019/4/8 12:08
     */
    @GetMapping("/findPermissionListBySchoolId")
    @ApiOperation(value = "菜单or按钮权限列表(根据学校id)", response = List.class)
    public Object findPermissionListBySchoolId(@RequestParam("schoolId") Long schoolId) {
        List<SchoolPermission> permissions = schoolPermissionService.findPermissionByRoleId(null, null, schoolId);
        List<SchoolPermissionTreeVo> treeVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(permissions)) {
            permissions.stream().forEach(e -> {
                SchoolPermissionTreeVo treeVo = new SchoolPermissionTreeVo();
                treeVo.setTitle(e.getName());
                treeVo.setCreateDate(e.getCreateDate());
                BeanUtils.copyProperties(e, treeVo);
                treeVos.add(treeVo);
            });
        }
        TreeLoopUtils<SchoolPermissionTreeVo> treeLoopUtils = new TreeLoopUtils<>();
        List<SchoolPermissionTreeVo> result = treeLoopUtils.getTree(treeVos);
        return WrapMapper.ok(result);
    }


    /**
     * @Description: 增加用户权限
     * @Author: Kang
     * @Date: 2019/3/26 14:29
     */
    @RequestMapping(value = "/addSchoolPermission", method = RequestMethod.POST)
    @ApiOperation(value = "新增用户权限菜单or按钮", response = Boolean.class)
    public Object addSchoolPermission(@Validated @RequestBody AddSchoolPermissionDto addSchoolPermissionDto) {
        SchoolPermission schoolPermission = new SchoolPermission();
        BeanUtils.copyProperties(addSchoolPermissionDto, schoolPermission);
        //赋值类型
        schoolPermission.setType(addSchoolPermissionDto.getSchoolPermissionTypeEnum().getKey());
        return WrapMapper.ok(schoolPermissionService.addPermission(schoolPermission));
    }

    /**
     * @Description: 修改用户权限
     * @Author: Kang
     * @Date: 2019/3/26 14:29
     */
    @RequestMapping(value = "/modifySchoolPermission", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户权限菜单or按钮", response = Boolean.class)
    public Object modifySchoolPermission(@Validated @RequestBody ModifySchoolPermissionDto ModifySchoolPermissionDto) {
        SchoolPermission schoolPermission = new SchoolPermission();
        BeanUtils.copyProperties(ModifySchoolPermissionDto, schoolPermission);
        //赋值类型
        schoolPermission.setType(ModifySchoolPermissionDto.getSchoolPermissionTypeEnum().getKey());
        return WrapMapper.ok(schoolPermissionService.modifyPermission(schoolPermission));
    }

    /**
     * @Description: 删除用户权限
     * @Author: Kang
     * @Date: 2019/3/26 14:29
     */
    @RequestMapping(value = "/delSchoolPermissionById", method = RequestMethod.POST)
    @ApiOperation(value = "删除用户权限菜单or按钮", response = Boolean.class)
    public Object delSchoolPermissionById(@RequestParam("id") Long id) {
        List<SchoolPermission> childPermissions = schoolPermissionService.findSchoolPermissionByParentId(id);
        if (CollectionUtils.isNotEmpty(childPermissions)) {
            return WrapMapper.error("存在子菜单,不能被删除");
        }
        return WrapMapper.ok(schoolPermissionService.delPermissionById(id));
    }

    /**
     * @Description: 角色与权限菜单的捆绑（addOrModify）
     * @Author: Kang
     * @Date: 2019/3/28 14:02
     */
    @RequestMapping(value = "/addRolePermissionBindMenu", method = RequestMethod.POST)
    @ApiOperation(value = "角色与权限菜单or按钮的捆绑")
    public Object addRolePermissionBindMenu(@Validated @RequestBody AddRolePermissionBindMenuDto addRolePermissionBindMenu) {
        schoolRolePermissionService.addRolePermissionBindMenu(addRolePermissionBindMenu);
        return WrapMapper.ok();
    }

    @RequestMapping(value = "/permissionMenusByUserId", method = RequestMethod.GET)
    @ApiOperation(value = "用户id查询用户菜单、按钮", response = List.class)
    @ResponseBody
    public Object permissionMenusByUserId(@RequestParam("userId") Long userId) {
        List<String> permissions = schoolPermissionService.permissionMenusByUserId(userId);
        return WrapMapper.ok(permissions);
    }
}

