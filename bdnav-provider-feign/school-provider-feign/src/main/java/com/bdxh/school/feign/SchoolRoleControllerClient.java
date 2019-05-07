package com.bdxh.school.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolRoleDto;
import com.bdxh.school.dto.ModifySchoolRoleDto;
import com.bdxh.school.dto.SchoolRoleQueryDto;
import com.bdxh.school.entity.SchoolRole;
import com.bdxh.school.fallback.SchoolRoleControllerClientFallback;
import com.bdxh.school.vo.SchoolRoleInfoVo;
import com.bdxh.school.vo.SchoolRoleShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description: 学校角色feign客户端
 * @Author: Kang
 * @Date: 2019/3/26 16:01
 */
@Service
@FeignClient(value = "school-provider-cluster", fallback = SchoolRoleControllerClientFallback.class)
public interface SchoolRoleControllerClient {

    /**
     * 根据用户id查询角色列表(角色名称)
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/schoolRole/findSchoolRolesByUserId", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<String>> findSchoolRolesByUserId(@RequestParam("userId") Long userId);

    /**
     *  用户id查询，角色id列表
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/schoolRole/getRoleIdListByUserId", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<Long>> getRoleIdListByUserId(@RequestParam("userId") Long userId);

    /**
     * 根据id查询角色信息
     *
     */
    @RequestMapping(value = "/schoolRole/findSchoolRoleById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<SchoolRoleInfoVo> findSchoolRoleById(@RequestParam(name = "id") Long id);

    /**
     * 分页条件筛选查询角色信息
     *
     * @return
     */
    @RequestMapping(value = "/schoolRole/findRolesInConditionPage", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<SchoolRoleShowVo>> findRolesInConditionPage(@RequestBody SchoolRoleQueryDto roleQueryDto);

    /**
     * 增加角色管理
     *
     * @return
     */
    @RequestMapping(value = "/schoolRole/addSchoolRole", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addSchoolRole(@RequestBody AddSchoolRoleDto addSchoolRoleDto);


    /**
     * 修改角色管理信息
     *
     * @return
     */
    @RequestMapping(value = "/schoolRole/modifySchoolRole", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifySchoolRole(@RequestBody ModifySchoolRoleDto ModifySchoolRoleDto);


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
