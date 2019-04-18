package com.bdxh.school.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddGroupPermissionDto;
import com.bdxh.school.dto.GroupPermissionQueryDto;
import com.bdxh.school.dto.ModifyGroupPermissionDto;
import com.bdxh.school.entity.GroupPermission;
import com.bdxh.school.enums.GroupTypeEnum;
import com.bdxh.school.fallback.GroupPermissionControllerClientFallback;
import com.bdxh.school.vo.GroupPermissionInfoVo;
import com.bdxh.school.vo.GroupPermissionShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 学校门禁组feign客户端
 * @Author: Kang
 * @Date: 2019/3/27 16:44
 */
@Service
@FeignClient(value = "school-provider-cluster", fallback = GroupPermissionControllerClientFallback.class)
public interface GroupPermissionControllerClient {

    /**
     * @Description: 增加门禁组信息
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/groupPermission/addGroupPermission", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addGroupPermission(@RequestBody AddGroupPermissionDto addGroupPermissionDto);

    /**
     * @Description: 修改门禁组信息
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/groupPermission/modifyGroupPermission", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifyGroupPermission(@RequestBody ModifyGroupPermissionDto modifyGroupPermissionDto);

    /**
     * @Description: 删除门禁组信息
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/groupPermission/delGroupPermissionById", method = RequestMethod.POST)
    @ResponseBody
    Wrapper delGroupPermissionById(@RequestParam("id") Long id);

    /**
     * @Description: 批量删除门禁组信息
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/groupPermission/delBatchGroupPermissionInIds", method = RequestMethod.POST)
    @ResponseBody
    Wrapper delBatchGroupPermissionInIds(@RequestParam("ids") List<Long> ids);

    /**
     * @Description: id查询门禁组信息
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/groupPermission/findGroupPermissionById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<GroupPermissionInfoVo> findGroupPermissionById(@RequestParam("id") Long id);

    /**
     * @Description: 部门id+部门类型查询学校组门禁
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/groupPermission/findGroupByGroupIdsAndType", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<GroupPermission> findGroupByGroupIdsAndType(@RequestParam("id") Long groupId, @RequestParam("groupTypeEnum") GroupTypeEnum groupTypeEnum);

    /**
     * @Description: 门禁组信息根据条件分页查询
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/groupPermission/findGroupPermissionInConditionPage", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<GroupPermissionShowVo>> findGroupPermissionInConditionPage(@RequestBody GroupPermissionQueryDto groupPermissionQueryDto);
}
