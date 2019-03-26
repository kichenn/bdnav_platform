package com.bdxh.school.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.SchoolRoleQueryDto;
import com.bdxh.school.entity.SchoolRole;
import com.bdxh.school.fallback.SchoolRoleControllerClientFallback;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 学校角色feign客户端
 * @Author: Kang
 * @Date: 2019/3/26 16:01
 */
@Service
@FeignClient(value = "school-provider-cluster", fallback = SchoolRoleControllerClientFallback.class)
public interface SchoolRoleControllerClient {

    /**
     * 根据用户id查询角色列表
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/schoolRole/findSchoolRolesByUserId", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<String>> findSchoolRolesByUserId(@RequestParam("userId") Long userId);

    /**
     * 分页条件筛选查询角色信息
     *
     * @return
     */
    @RequestMapping(value = "/schoolRole/findRolesInConditionPage", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<SchoolRole>> findRolesInConditionPage(@RequestBody SchoolRoleQueryDto roleQueryDto);

    /**
     * 增加角色管理
     *
     * @return
     */
    @RequestMapping(value = "/schoolRole/addSchoolRole", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addSchoolRole(@RequestBody SchoolRole schoolRole);


    /**
     * 修改角色管理信息
     *
     * @return
     */
    @RequestMapping(value = "/schoolRole/modifySchoolRole", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifySchoolRole(@RequestBody SchoolRole schoolRole);


    /**
     * 删除角色管理信息
     *
     * @return
     */
    @RequestMapping(value = "/schoolRole/delSchoolRole", method = RequestMethod.POST)
    @ResponseBody
    Wrapper delSchoolRole(@RequestParam(name = "schoolRoleId") Long roleId);


    /**
     * 批量删除管理信息
     *
     * @return
     */
    @RequestMapping(value = "/schoolRole/delBatchSchoolRole", method = RequestMethod.POST)
    @ResponseBody
    Wrapper delBatchSchoolRole(@RequestParam(name = "ids") List<Long> ids);


}
