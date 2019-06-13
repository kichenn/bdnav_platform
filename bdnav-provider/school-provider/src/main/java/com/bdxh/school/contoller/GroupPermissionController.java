package com.bdxh.school.contoller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.AddGroupPermissionDto;
import com.bdxh.school.dto.GroupPermissionQueryDto;
import com.bdxh.school.dto.ModifyGroupPermissionDto;
import com.bdxh.school.entity.*;
import com.bdxh.school.enums.GroupTypeEnum;
import com.bdxh.school.persistence.SchoolDeviceMapper;
import com.bdxh.school.service.*;
import com.bdxh.school.vo.GroupPermissionInfoVo;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 学校门禁组权限操作
 * @Author Kang
 * @Date 2019-03-27 12:28:42
 */
@RestController
@RequestMapping("/groupPermission")
@Slf4j
@Validated
@Api(value = "学校门禁组权限操作API", tags = "学校门禁组权限操作API")
public class GroupPermissionController {

    @Autowired
    private GroupPermissionService groupPermissionService;

    @Autowired
    private SchoolDeviceService schoolDeviceService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private SchoolOrgService schoolOrgService;


    /**
     * @Description: 增加学校组门禁
     * @Author: Kang
     * @Date: 2019/3/27 14:09
     */
    @PostMapping("/addGroupPermission")
    @ApiOperation(value = "增加学校组门禁", response = Boolean.class)
    public Object addGroupPermission(@Validated @RequestBody AddGroupPermissionDto addGroupPermissionDto) {
        //复制实体
        GroupPermission groupPermission = new GroupPermission();
        BeanUtils.copyProperties(addGroupPermissionDto, groupPermission);
        //设置选择状态
        groupPermission.setGroupType(addGroupPermissionDto.getGroupTypeEnum().getKey());
        groupPermission.setAccessFlag(addGroupPermissionDto.getAccessFlagEnum().getKey());
        groupPermission.setRecursionPermission(addGroupPermissionDto.getRecursionPermissionStatusEnum().getKey());
        try {
            groupPermissionService.save(groupPermission);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                //部门类型，部门id，设备不能重复
                return WrapMapper.error("该门禁组不能重复(请检查)");
            }
            e.printStackTrace();
        }
        return WrapMapper.ok();
    }

    /**
     * @Description: 增加学校组门禁
     * @Author: Kang
     * @Date: 2019/3/27 14:13
     */
    @PostMapping("/modifyGroupPermission")
    @ApiOperation(value = "修改学校组门禁", response = Boolean.class)
    public Object modifyGroupPermission(@Validated @RequestBody ModifyGroupPermissionDto modifyGroupPermissionDto) {
        //复制实体
        GroupPermission groupPermission = new GroupPermission();
        BeanUtils.copyProperties(modifyGroupPermissionDto, groupPermission);
        //设置选择状态
        groupPermission.setGroupType(modifyGroupPermissionDto.getGroupTypeEnum().getKey());
        groupPermission.setAccessFlag(modifyGroupPermissionDto.getAccessFlagEnum().getKey());
        groupPermission.setRecursionPermission(modifyGroupPermissionDto.getRecursionPermissionStatusEnum().getKey());

        try {
            groupPermissionService.update(groupPermission);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                //部门类型，部门id，设备不能重复
                return WrapMapper.error("该门禁组不能重复(请检查)");
            }
            e.printStackTrace();
        }
        return WrapMapper.ok();
    }

    /**
     * @Description: 删除学校组门禁根据id
     * @Author: Kang
     * @Date: 2019/3/27 14:09
     */
    @PostMapping("/delGroupPermissionById")
    @ApiOperation(value = "删除学校组门禁根据id", response = Boolean.class)
    public Object delGroupPermissionById(@RequestParam("id") Long id) {
        return WrapMapper.ok(groupPermissionService.deleteByKey(id) > 0);
    }

    /**
     * @Description: 批量删除学校组门禁根据id
     * @Author: Kang
     * @Date: 2019/3/27 14:09
     */
    @PostMapping("/delBatchGroupPermissionInIds")
    @ApiOperation(value = "批量删除学校组门禁根据id", response = Boolean.class)
    public Object delBatchGroupPermissionInIds(@RequestParam("ids") List<Long> ids) {
        return WrapMapper.ok(groupPermissionService.batchDelGroupPermissionInIds(ids));
    }

    /**
     * @Description: id查询学校组门禁
     * @Author: Kang
     * @Date: 2019/3/27 14:09
     */
    @GetMapping("/findGroupPermissionById")
    @ApiOperation(value = "id查询学校组门禁", response = Boolean.class)
    public Object findGroupPermissionById(@RequestParam("id") Long id) {
        //门禁组信息
        GroupPermission groupPermission = groupPermissionService.selectByKey(id);
        //设置名称
        GroupPermissionInfoVo groupPermissionInfoVos = new GroupPermissionInfoVo();
        if (groupPermission != null) {
            BeanUtils.copyProperties(groupPermission, groupPermissionInfoVos);
            SchoolDevice device = schoolDeviceService.selectByKey(groupPermissionInfoVos.getDeviceId());
            groupPermissionInfoVos.setDeviceName(device != null ? device.getDeviceName() : "");
            School school = schoolService.findSchoolById(groupPermissionInfoVos.getSchoolId()).orElse(new School());
            groupPermissionInfoVos.setSchoolName(school.getSchoolName());
            if (new Byte("1").equals(groupPermissionInfoVos.getGroupType())) {
                //学生
                SchoolOrg schoolOrg = schoolOrgService.selectByKey(groupPermissionInfoVos.getGroupId());
                groupPermissionInfoVos.setGroupName(schoolOrg != null ? schoolOrg.getOrgName() : "");
            } else if (new Byte("2").equals(groupPermissionInfoVos.getGroupType())) {
                //老师
                SchoolOrg schoolOrg = schoolOrgService.selectByKey(groupPermissionInfoVos.getGroupId());
                groupPermissionInfoVos.setGroupName(schoolOrg != null ? schoolOrg.getOrgName() : "");
            }
        }
        return WrapMapper.ok(groupPermissionInfoVos);
    }

    /**
     * @Description: 部门id+部门类型查询学校组门禁
     * @Author: Kang
     * @Date: 2019/3/27 14:09
     */
    @GetMapping("/findGroupByGroupIdsAndType")
    @ApiOperation(value = "部门id+部门类型查询学校组门禁", response = GroupPermission.class)
    public Object findGroupByGroupIdsAndType(@RequestParam("id") Long groupId, @RequestParam("groupTypeEnum") GroupTypeEnum groupTypeEnum) {
        return WrapMapper.ok(groupPermissionService.findGroupRecursionPermissionIds(groupId, groupTypeEnum.getKey()));
    }

    /**
     * @Description: 分页查询学校组门禁信息
     * @Author: Kang
     * @Date: 2019/3/27 14:09
     */
    @PostMapping("/findGroupPermissionInConditionPage")
    @ApiOperation(value = "分页查询学校组门禁信息", response = PageInfo.class)
    public Object findGroupPermissionInConditionPage(@RequestBody GroupPermissionQueryDto groupPermissionQueryDto) {
        return WrapMapper.ok(groupPermissionService.findGroupPermissionInConditionPage(groupPermissionQueryDto));
    }


}