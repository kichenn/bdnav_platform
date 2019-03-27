package com.bdxh.backend.controller.school;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
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
                                               @RequestParam(name = "schoolId",required = false) Long schoolId) {
        Wrapper wrapper = schoolPermissionControllerClient.findSchoolPermissionByRoleId(roleId, type,schoolId);
        return WrapMapper.ok(wrapper.getResult());
    }

    @RequestMapping(value = "/addSchoolPermission", method = RequestMethod.POST)
    @ApiOperation(value = "添加权限菜单", response = Boolean.class)
    public Object addSchoolPermission(@RequestBody AddSchoolPermissionDto aspd) {
        SchoolPermission schoolPermission = new SchoolPermission();
        BeanUtils.copyProperties(aspd, schoolPermission);
        //设置操作人
        User user = SecurityUtils.getCurrentUser();
        schoolPermission.setOperator(user.getId());
        schoolPermission.setOperatorName(user.getUserName());
        Wrapper wrapper = schoolPermissionControllerClient.addSchoolPermission(schoolPermission);
        return wrapper;
    }


    @RequestMapping(value = "/modifyPermission", method = RequestMethod.POST)
    @ApiOperation(value = "修改权限菜单", response = Boolean.class)
    public Object modifyPermission(@RequestBody ModifySchoolPermissionDto mspd) {
        SchoolPermission schoolPermission = new SchoolPermission();
        BeanUtils.copyProperties(mspd, schoolPermission);
        //设置操作人
        User user = SecurityUtils.getCurrentUser();
        schoolPermission.setOperator(user.getId());
        schoolPermission.setOperatorName(user.getUserName());
        Wrapper wrapper = schoolPermissionControllerClient.modifySchoolPermission(schoolPermission);
        return wrapper;
    }

}
