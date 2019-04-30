package com.bdxh.client.controller.school;

import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSinglePermission;
import com.bdxh.school.dto.ModifySinglePermission;
import com.bdxh.school.dto.SinglePermissionQueryDto;
import com.bdxh.school.entity.SchoolDevice;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.entity.SinglePermission;
import com.bdxh.school.feign.SinglePermissionControllerClient;
import com.bdxh.school.vo.SinglePermissionShowVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * @Description: 学校门禁单信息
 * @Author: Kang
 * @Date: 2019/3/27 17:37
 */
@RestController
@RequestMapping("/clientSinglePermissionWeb")
@Validated
@Slf4j
@Api(value = "学校管理员--学校门禁单信息", tags = "学校管理员--学校门禁单信息")
public class SinglePermissionWebController {

    @Autowired
    private SinglePermissionControllerClient singlePermissionControllerClient;

    @RolesAllowed({"ADMIN"})
    @PostMapping("/addSinglePermission")
    @ApiOperation(value = "增加门禁单信息（需学校admin权限）", response = Boolean.class)
    public Object addSinglePermission(@Validated @RequestBody AddSinglePermission addSinglePermission) {
        //获取当前用户
        SchoolUser user = SecurityUtils.getCurrentUser();
        //设置操作人
        addSinglePermission.setOperator(user.getId());
        addSinglePermission.setOperatorName(user.getUserName());
        addSinglePermission.setSchoolCode(user.getSchoolCode());
        addSinglePermission.setSchoolId(user.getSchoolId());
        Wrapper wrapper = singlePermissionControllerClient.addSinglePermission(addSinglePermission);
        return wrapper;
    }

    @RolesAllowed({"ADMIN"})
    @PostMapping("/modifySinglePermission")
    @ApiOperation(value = "修改门禁单信息（需学校admin权限）", response = Boolean.class)
    public Object modifySinglePermission(@Validated @RequestBody ModifySinglePermission modifySinglePermission) {
        //获取当前用户
        SchoolUser user = SecurityUtils.getCurrentUser();
        //设置操作人
        modifySinglePermission.setOperator(user.getId());
        modifySinglePermission.setOperatorName(user.getUserName());
        modifySinglePermission.setSchoolCode(user.getSchoolCode());
        modifySinglePermission.setSchoolId(user.getSchoolId());
        Wrapper wrapper = singlePermissionControllerClient.modifySinglePermission(modifySinglePermission);
        return wrapper;
    }

    @RolesAllowed({"ADMIN"})
    @PostMapping("/delSinglePermissionById")
    @ApiOperation(value = "删除门禁单信息（需学校admin权限）", response = Boolean.class)
    public Object delSinglePermissionById(@RequestParam("id") Long id) {
        Wrapper wrapper = singlePermissionControllerClient.delSinglePermissionById(id);
        return wrapper;
    }

    @RolesAllowed({"ADMIN"})
    @PostMapping("/delBatchSinglePermissionByInIds")
    @ApiOperation(value = "批量删除门禁单信息（需学校admin权限）", response = Boolean.class)
    public Object delBatchSinglePermissionByInIds(@RequestParam("ids") List<Long> ids) {
        Wrapper wrapper = singlePermissionControllerClient.delBatchSinglePermissionByInIds(ids);
        return wrapper;
    }

    @GetMapping("/findSinglePermissionById")
    @ApiOperation(value = "id查询门禁单信息", response = SchoolDevice.class)
    public Object findSinglePermissionById(@RequestParam("id") Long id) {
        Wrapper wrapper = singlePermissionControllerClient.findSinglePermissionById(id);
        return WrapMapper.ok(wrapper.getResult());
    }

    @PostMapping("/findSinglePermissionInConditionPage")
    @ApiOperation(value = "门禁单信息根据条件分页查询", response = PageInfo.class)
    public Object findSinglePermissionInConditionPage(@RequestBody SinglePermissionQueryDto singlePermissionQueryDto) {
        //获取当前用户
        SchoolUser user = SecurityUtils.getCurrentUser();
        singlePermissionQueryDto.setSchoolId(user.getSchoolId());
        Wrapper<PageInfo<SinglePermissionShowVo>> wrapper = singlePermissionControllerClient.findSinglePermissionInConditionPage(singlePermissionQueryDto);
        return WrapMapper.ok(wrapper.getResult());
    }
}
