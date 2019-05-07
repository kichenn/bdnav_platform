package com.bdxh.client.controller.school;

import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddRolePermissionBindMenuDto;
import com.bdxh.school.dto.AddSchoolPermissionDto;
import com.bdxh.school.dto.ModifySchoolPermissionDto;
import com.bdxh.school.entity.SchoolPermission;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolPermissionControllerClient;
import com.bdxh.school.vo.SchoolPermissionTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * @Description: web层学校权限交互api
 * @Author: Kang
 * @Date: 2019/3/26 16:33
 */
@RestController
@RequestMapping("/clientSchoolWebPermission")
@Validated
@Slf4j
@Api(value = "学校管理员--学校权限操作菜单管理API", tags = "学校管理员--学校权限操作菜单管理API")
public class SchoolPermissionWebController {

    @Autowired
    private SchoolPermissionControllerClient schoolPermissionControllerClient;


    @RequestMapping(value = "/permissionMenusById", method = RequestMethod.GET)
    @ApiOperation(value = "id查询菜单或者按钮详情", response = SchoolPermission.class)
    public Object permissionMenusById(@RequestParam("id") Long id) {
        SchoolPermission permissions = schoolPermissionControllerClient.permissionMenusById(id).getResult();
        return WrapMapper.ok(permissions);
    }

    @RequestMapping(value = "/findSchoolPermissionByRoleId", method = RequestMethod.GET)
    @ApiOperation(value = "学校角色id查询用户菜单or按钮权限", response = SchoolPermissionTreeVo.class)
    public Object findSchoolPermissionByRoleId(@RequestParam(name = "roleIds", required = false) String roleIds,
                                               @RequestParam(name = "type", required = false) Byte type) {
        //获取当前用户
        SchoolUser user = SecurityUtils.getCurrentUser();
        Wrapper wrapper = schoolPermissionControllerClient.findSchoolPermissionByRoleId(roleIds, type, user.getSchoolId());
        return WrapMapper.ok(wrapper.getResult());
    }

    @RequestMapping(value = "/findPermissionList", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前学校，菜单or按钮权限列表", response = SchoolPermissionTreeVo.class)
    public Object findPermissionList(@RequestParam(value = "roleIds", required = false) String roleIds) {
        //获取当前用户
        SchoolUser user = SecurityUtils.getCurrentUser();
        Wrapper wrapper = schoolPermissionControllerClient.findPermissionListBySchoolId(user.getSchoolId(),roleIds);
        return WrapMapper.ok(wrapper.getResult());
    }

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/addSchoolPermission", method = RequestMethod.POST)
    @ApiOperation(value = "添加权限菜单（需学校admin权限）", response = Boolean.class)
    public Object addSchoolPermission(@Validated @RequestBody AddSchoolPermissionDto aspd) {
        //设置操作人
        SchoolUser user = SecurityUtils.getCurrentUser();
        aspd.setOperator(user.getId());
        aspd.setOperatorName(user.getUserName());
        aspd.setSchoolCode(user.getSchoolCode());
        aspd.setSchoolId(user.getSchoolId());
        Wrapper wrapper = schoolPermissionControllerClient.addSchoolPermission(aspd);
        return wrapper;
    }

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/modifyPermission", method = RequestMethod.POST)
    @ApiOperation(value = "修改权限菜单（需学校admin权限）", response = Boolean.class)
    public Object modifyPermission(@Validated @RequestBody ModifySchoolPermissionDto mspd) {
        //设置操作人
        SchoolUser user = SecurityUtils.getCurrentUser();
        mspd.setOperator(user.getId());
        mspd.setOperatorName(user.getUserName());
        mspd.setSchoolId(user.getSchoolId());
        mspd.setSchoolCode(user.getSchoolCode());
        Wrapper wrapper = schoolPermissionControllerClient.modifySchoolPermission(mspd);
        return wrapper;
    }

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/delPermissionById", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除权限菜单（需学校admin权限）", response = Boolean.class)
    public Object delPermissionById(@RequestParam("id") Long id) {
        Wrapper wrapper = schoolPermissionControllerClient.delSchoolPermissionById(id);
        return wrapper;
    }


    /**
     * @Description: 角色与权限菜单的捆绑
     * @Author: Kang
     * @Date: 2019/3/28 12:01
     */
    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/addRolePermissionBindMenu", method = RequestMethod.POST)
    @ApiOperation(value = "角色与权限菜单or按钮的捆绑（需学校admin权限）")
    public Object addRolePermissionBindMenu(@Validated @RequestBody AddRolePermissionBindMenuDto addRolePermissionBindMenu) {
        //设置操作人
        SchoolUser user = SecurityUtils.getCurrentUser();
        addRolePermissionBindMenu.setSchoolCode(user.getSchoolCode());
        addRolePermissionBindMenu.setSchoolId(user.getSchoolId());
        Wrapper wrapper = schoolPermissionControllerClient.addRolePermissionBindMenu(addRolePermissionBindMenu);
        return wrapper;
    }
}
