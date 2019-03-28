package com.bdxh.backend.controller.school;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddRolePermissionBindMenuDto;
import com.bdxh.school.dto.AddSchoolPermissionDto;
import com.bdxh.school.dto.ModifySchoolPermissionDto;
import com.bdxh.school.entity.SchoolPermission;
import com.bdxh.school.feign.SchoolPermissionControllerClient;
import com.bdxh.school.vo.SchoolPermissionTreeVo;
import com.bdxh.system.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: web层学校权限交互api
 * @Author: Kang
 * @Date: 2019/3/26 16:33
 */
@RestController
@RequestMapping("/schoolWebPermissionController")
@Validated
@Slf4j
@Api(value = "学校权限交互API", tags = "学校权限交互API")
public class SchoolPermissionWebController {

    @Autowired
    private SchoolPermissionControllerClient schoolPermissionControllerClient;


    @RequestMapping(value = "/findSchoolPermissionByRoleId", method = RequestMethod.GET)
    @ApiOperation(value = "学校角色id查询用户菜单or按钮权限", response = SchoolPermissionTreeVo.class)
    public Object findSchoolPermissionByRoleId(@RequestParam(name = "roleId") Long roleId,
                                               @RequestParam(name = "type") Byte type,
                                               @RequestParam(name = "schoolId", required = false) Long schoolId) {
        Wrapper wrapper = schoolPermissionControllerClient.findSchoolPermissionByRoleId(roleId, type, schoolId);
        return WrapMapper.ok(wrapper.getResult());
    }

    @RequestMapping(value = "/findPermissionList", method = RequestMethod.GET)
    @ApiOperation(value = "菜单or按钮权限列表", response = SchoolPermissionTreeVo.class)
    public Object findPermissionList() {
        Wrapper wrapper = schoolPermissionControllerClient.findPermissionList();
        return WrapMapper.ok(wrapper.getResult());
    }

    @RequestMapping(value = "/addSchoolPermission", method = RequestMethod.POST)
    @ApiOperation(value = "添加权限菜单", response = Boolean.class)
    public Object addSchoolPermission(@Validated @RequestBody AddSchoolPermissionDto aspd) {
        //设置操作人
        User user = SecurityUtils.getCurrentUser();
        aspd.setOperator(user.getId());
        aspd.setOperatorName(user.getUserName());
        Wrapper wrapper = schoolPermissionControllerClient.addSchoolPermission(aspd);
        return wrapper;
    }


    @RequestMapping(value = "/modifyPermission", method = RequestMethod.POST)
    @ApiOperation(value = "修改权限菜单", response = Boolean.class)
    public Object modifyPermission(@Validated @RequestBody ModifySchoolPermissionDto mspd) {
        //设置操作人
        User user = SecurityUtils.getCurrentUser();
        mspd.setOperator(user.getId());
        mspd.setOperatorName(user.getUserName());
        Wrapper wrapper = schoolPermissionControllerClient.modifySchoolPermission(mspd);
        return wrapper;
    }


    /**
     * @Description: 角色与权限菜单的捆绑
     * @Author: Kang
     * @Date: 2019/3/28 12:01
     */
    @RequestMapping(value = "/addRolePermissionBindMenu", method = RequestMethod.POST)
    @ApiOperation(value = "角色与权限菜单or按钮的捆绑")
    public Object addRolePermissionBindMenu(@Validated @RequestBody AddRolePermissionBindMenuDto addRolePermissionBindMenu) {
        Wrapper wrapper = schoolPermissionControllerClient.addRolePermissionBindMenu(addRolePermissionBindMenu);
        return wrapper;
    }
}
