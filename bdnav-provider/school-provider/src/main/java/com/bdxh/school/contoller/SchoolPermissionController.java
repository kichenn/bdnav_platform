package com.bdxh.school.contoller;

import com.bdxh.common.helper.tree.utils.TreeLoopUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
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
     * @Description: 增加用户权限
     * @Author: Kang
     * @Date: 2019/3/26 14:29
     */
    @RequestMapping(value = "/addSchoolPermission", method = RequestMethod.POST)
    @ApiOperation(value = "新增用户权限", response = Boolean.class)
    public Object addSchoolPermission(@RequestBody SchoolPermission schoolPermission) {
        return WrapMapper.ok(schoolPermissionService.addPermission(schoolPermission));
    }

    /**
     * @Description: 修改用户权限
     * @Author: Kang
     * @Date: 2019/3/26 14:29
     */
    @RequestMapping(value = "/modifySchoolPermission", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户权限", response = Boolean.class)
    public Object modifySchoolPermission(@RequestBody SchoolPermission schoolPermission) {
        return WrapMapper.ok(schoolPermissionService.modifyPermission(schoolPermission));
    }

    /**
     * @Description: 删除用户权限
     * @Author: Kang
     * @Date: 2019/3/26 14:29
     */
    @RequestMapping(value = "/delSchoolPermissionById", method = RequestMethod.POST)
    @ApiOperation(value = "删除用户权限", response = Boolean.class)
    public Object delSchoolPermissionById(@RequestParam("id") Long id) {
        return WrapMapper.ok(schoolPermissionService.delPermissionById(id));
    }

    /**
     * @Description: 角色删除所有权限
     * @Author: Kang
     * @Date: 2019/3/26 14:29
     */
    @RequestMapping(value = "/delSchoolPermissionByRoleId", method = RequestMethod.POST)
    @ApiOperation(value = "角色删除所有权限", response = Boolean.class)
    public Object delSchoolPermissionByRoleId(@RequestParam("roleId") Long roleId) {
        List<Long> permissionIds = schoolRolePermissionService.findPermissionIdByRoleId(roleId);
        return WrapMapper.ok(schoolPermissionService.batchDelPermission(permissionIds));
    }

}
