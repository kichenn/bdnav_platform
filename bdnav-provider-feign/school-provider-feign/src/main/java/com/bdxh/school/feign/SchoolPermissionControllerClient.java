package com.bdxh.school.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddRolePermissionBindMenuDto;
import com.bdxh.school.dto.AddSchoolPermissionDto;
import com.bdxh.school.dto.ModifySchoolPermissionDto;
import com.bdxh.school.entity.SchoolPermission;
import com.bdxh.school.fallback.SchoolPermissionControllerClientFallback;
import com.bdxh.school.vo.SchoolPermissionTreeVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 学校权限feign客户端
 * @Author: Kang
 * @Date: 2019/3/26 15:50
 */
@Service
@FeignClient(value = "school-provider-cluster", fallback = SchoolPermissionControllerClientFallback.class)
public interface SchoolPermissionControllerClient {


    @RequestMapping(value = "/schoolPermission/permissionMenusById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<SchoolPermission> permissionMenusById(@RequestParam("id") Long id);

    /**
     * 根据用户id查询权限列表(菜单 and  按钮)
     *
     * @return
     */
    @RequestMapping(value = "/schoolPermission/permissionMenusByUserId", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<String>> permissionMenusByUserId(@RequestParam("userId") Long userId);


    /**
     * 学校角色id查询用户菜单or按钮权限
     *
     * @return
     */
    @RequestMapping(value = "/schoolPermission/findSchoolPermissionByRoleId", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SchoolPermissionTreeVo>> findSchoolPermissionByRoleId(@RequestParam(name = "roleIds", required = false) String roleIds, @RequestParam(name = "type", required = false) Byte type, @RequestParam(name = "schoolId", required = false) Long schoolId);

    /**
     * 菜单or按钮权限列表
     *
     * @return
     */
    @RequestMapping(value = "/schoolPermission/findPermissionList", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SchoolPermissionTreeVo>> findPermissionList(@RequestParam(value = "roleIds") String roleIds, @RequestParam(value = "schoolId") Long schoolId);

    /**
     * 菜单or按钮权限列表(根据学校id)
     *
     * @return
     */
    @RequestMapping(value = "/schoolPermission/findPermissionListBySchoolId", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SchoolPermissionTreeVo>> findPermissionListBySchoolId(@RequestParam("schoolId") Long schoolId,@RequestParam(value = "roleIds", required = false) String roleIds);

    /**
     * 添加权限信息
     *
     * @return
     */
    @RequestMapping(value = "/schoolPermission/addSchoolPermission", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addSchoolPermission(@RequestBody AddSchoolPermissionDto addSchoolPermissionDto);

    /**
     * 修改权限信息
     *
     * @return
     */
    @RequestMapping(value = "/schoolPermission/modifySchoolPermission", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifySchoolPermission(@RequestBody ModifySchoolPermissionDto modifySchoolPermissionDto);


    /**
     * @Description: 删除用户权限
     * @Author: Kang
     * @Date: 2019/3/29 14:39
     */
    @RequestMapping(value = "/schoolPermission/delSchoolPermissionById", method = RequestMethod.POST)
    @ResponseBody
    Wrapper delSchoolPermissionById(@RequestParam("id") Long id);

    /**
     * @Description: 角色与权限菜单的捆绑
     * @Author: Kang
     * @Date: 2019/3/28 12:01
     */
    @RequestMapping(value = "/schoolPermission/addRolePermissionBindMenu", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addRolePermissionBindMenu(@Validated @RequestBody AddRolePermissionBindMenuDto addRolePermissionBindMenu);
}
