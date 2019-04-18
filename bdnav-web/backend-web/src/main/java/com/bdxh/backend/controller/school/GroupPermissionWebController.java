package com.bdxh.backend.controller.school;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.*;
import com.bdxh.school.entity.GroupPermission;
import com.bdxh.school.enums.GroupTypeEnum;
import com.bdxh.school.feign.GroupPermissionControllerClient;
import com.bdxh.school.vo.GroupPermissionShowVo;
import com.bdxh.system.entity.User;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groupPermissionWebController")
@Validated
@Slf4j
@Api(value = "学校门禁组", tags = "学校门禁组交互API")
public class GroupPermissionWebController {

    @Autowired
    private GroupPermissionControllerClient groupPermissionControllerClient;

    @PostMapping("/addGroupPermission")
    @ApiOperation(value = "增加门禁组信息", response = Boolean.class)
    public Object addGroupPermission(@Validated @RequestBody AddGroupPermissionDto addGroupPermissionDto) {
        //获取当前用户
        User user = SecurityUtils.getCurrentUser();
        //设置操作人
        addGroupPermissionDto.setOperator(user.getId());
        addGroupPermissionDto.setOperatorName(user.getUserName());
        Wrapper wrapper = groupPermissionControllerClient.addGroupPermission(addGroupPermissionDto);
        return wrapper;
    }

    @PostMapping("/modifyGroupPermission")
    @ApiOperation(value = "修改门禁组信息", response = Boolean.class)
    public Object modifyGroupPermission(@Validated @RequestBody ModifyGroupPermissionDto modifyGroupPermissionDto) {
        //获取当前用户
        User user = SecurityUtils.getCurrentUser();
        //设置操作人
        modifyGroupPermissionDto.setOperator(user.getId());
        modifyGroupPermissionDto.setOperatorName(user.getUserName());
        Wrapper wrapper = groupPermissionControllerClient.modifyGroupPermission(modifyGroupPermissionDto);
        return wrapper;
    }

    @PostMapping("/delGroupPermissionById")
    @ApiOperation(value = "删除门禁组信息", response = Boolean.class)
    public Object delGroupPermissionById(@RequestParam("id") Long id) {
        Wrapper wrapper = groupPermissionControllerClient.delGroupPermissionById(id);
        return wrapper;
    }

    @PostMapping("/delBatchGroupPermissionInIds")
    @ApiOperation(value = "批量删除门禁组信息", response = Boolean.class)
    public Object delBatchGroupPermissionInIds(@RequestParam("ids") List<Long> ids) {
        Wrapper wrapper = groupPermissionControllerClient.delBatchGroupPermissionInIds(ids);
        return wrapper;
    }

    @GetMapping("/findGroupPermissionById")
    @ApiOperation(value = "id查询门禁组信息", response = GroupPermission.class)
    public Object findGroupPermissionById(@RequestParam("id") Long id) {
        Wrapper wrapper = groupPermissionControllerClient.findGroupPermissionById(id);
        return WrapMapper.ok(wrapper.getResult());
    }

    @GetMapping("/findGroupByGroupIdsAndType")
    @ApiOperation(value = "部门id+部门类型查询学校组门禁", response = GroupPermission.class)
    public Object findGroupByGroupIdsAndType(@RequestParam("id") Long groupId, @RequestParam("groupTypeEnum") GroupTypeEnum groupTypeEnum) {
        Wrapper wrapper = groupPermissionControllerClient.findGroupByGroupIdsAndType(groupId, groupTypeEnum);
        return WrapMapper.ok(wrapper.getResult());
    }

    @PostMapping("/findGroupPermissionInConditionPage")
    @ApiOperation(value = "门禁组信息根据条件分页查询", response = PageInfo.class)
    public Object findGroupPermissionInConditionPage(@RequestBody GroupPermissionQueryDto groupPermissionQueryDto) {
        Wrapper<PageInfo<GroupPermissionShowVo>> wrapper = groupPermissionControllerClient.findGroupPermissionInConditionPage(groupPermissionQueryDto);
        return WrapMapper.ok(wrapper.getResult());
    }
}
