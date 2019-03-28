package com.bdxh.school.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolPermissionDto;
import com.bdxh.school.dto.ModifySchoolPermissionDto;
import com.bdxh.school.entity.SchoolPermission;
import com.bdxh.school.fallback.SchoolPermissionControllerClientFallback;
import com.bdxh.school.vo.SchoolPermissionTreeVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @Description:  学校权限feign客户端
* @Author: Kang
* @Date: 2019/3/26 15:50
*/
@Service
@FeignClient(value = "school-provider-cluster",fallback= SchoolPermissionControllerClientFallback.class)
public interface SchoolPermissionControllerClient {



    /**
     * 学校角色id查询用户菜单or按钮权限
     * @return
     */
    @RequestMapping(value = "/schoolPermission/findSchoolPermissionByRoleId",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SchoolPermissionTreeVo>> findSchoolPermissionByRoleId(@RequestParam("roleId") Long roleId, @RequestParam("type") Byte type,@RequestParam(name = "schoolId",required = false) Long schoolId);

    /**
     * 添加权限信息
     * @return
     */
    @RequestMapping(value = "/schoolPermission/addSchoolPermission",method = RequestMethod.POST)
    @ResponseBody
    Wrapper addSchoolPermission(@RequestBody AddSchoolPermissionDto addSchoolPermissionDto);

    /**
     * 修改权限信息
     * @return
     */
    @RequestMapping(value = "/schoolPermission/modifySchoolPermission",method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifySchoolPermission(@RequestBody ModifySchoolPermissionDto modifySchoolPermissionDto);

}
