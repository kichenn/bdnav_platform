package com.bdxh.school.contoller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.AddSinglePermission;
import com.bdxh.school.dto.ModifySinglePermission;
import com.bdxh.school.dto.SinglePermissionQueryDto;
import com.bdxh.school.entity.SchoolDevice;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bdxh.school.entity.SinglePermission;
import com.bdxh.school.service.SinglePermissionService;

import java.util.List;

/**
 * @Description: 学校门禁单控制器
 * @Author Kang
 * @Date 2019-03-27 12:28:42
 */
@RestController
@RequestMapping("/singlePermission")
@Slf4j
@Validated
@Api(value = "学校门禁单交互API", tags = "学校门禁单控制器交互API")
public class SinglePermissionController {

    @Autowired
    private SinglePermissionService singlePermissionService;

    /**
     * @Description: 增加门禁单信息
     * @Author: Kang
     * @Date: 2019/3/27 16:20
     */
    @PostMapping("/addSinglePermission")
    @ApiOperation(value = "增加门禁单信息", response = Boolean.class)
    public Object addSinglePermission(@Validated @RequestBody AddSinglePermission addSinglePermission) {
        //复制实体
        SinglePermission singlePermission = new SinglePermission();
        BeanUtils.copyProperties(addSinglePermission, singlePermission);
        //赋值用户类型
        singlePermission.setUserType(addSinglePermission.getSingleUserTypeEnum().getKey());
        try {
            singlePermissionService.save(singlePermission);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                //学校id，用户id，设备id
                return WrapMapper.error("该门禁单不能重复(请检查)");
            }
            e.printStackTrace();
        }
        return WrapMapper.ok();
    }

    /**
     * @Description: 修改门禁单信息
     * @Author: Kang
     * @Date: 2019/3/27 16:20
     */
    @PostMapping("/modifySinglePermission")
    @ApiOperation(value = "修改门禁单信息", response = Boolean.class)
    public Object modifySinglePermission(@Validated @RequestBody ModifySinglePermission modifySinglePermission) {
        //复制实体
        SinglePermission singlePermission = new SinglePermission();
        BeanUtils.copyProperties(modifySinglePermission, singlePermission);
        //赋值用户类型
        singlePermission.setUserType(modifySinglePermission.getSingleUserTypeEnum().getKey());
        try {
            singlePermissionService.update(singlePermission);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                //学校id，用户id，设备id
                return WrapMapper.error("该门禁单不能重复(请检查)");
            }
            e.printStackTrace();
        }
        return WrapMapper.ok();
    }

    /**
     * @Description: 删除门禁单信息
     * @Author: Kang
     * @Date: 2019/3/27 16:20
     */
    @PostMapping("/delSinglePermissionById")
    @ApiOperation(value = "删除门禁单信息", response = Boolean.class)
    public Object delSinglePermissionById(@RequestParam("id") Long id) {
        return WrapMapper.ok(singlePermissionService.deleteByKey(id) > 0);
    }

    /**
     * @Description: 批量删除门禁单信息
     * @Author: Kang
     * @Date: 2019/3/27 16:20
     */
    @PostMapping("/delBatchSinglePermissionByInIds")
    @ApiOperation(value = "批量删除门禁单信息", response = Boolean.class)
    public Object delBatchSinglePermissionInIds(@RequestParam("ids") List<Long> ids) {
        return WrapMapper.ok(singlePermissionService.batchDelSinglePermissionInIds(ids));
    }

    /**
     * @Description: id查询门禁单信息
     * @Author: Kang
     * @Date: 2019/3/27 16:20
     */
    @GetMapping("/findSinglePermissionById")
    @ApiOperation(value = "id查询门禁单信息", response = Boolean.class)
    public Object delBatchSinglePermissionByInIds(@RequestParam("id") Long id) {
        return WrapMapper.ok(singlePermissionService.selectByKey(id));
    }

    /**
     * @Description: 门禁单人信息根据条件分页查询
     * @Author: Kang
     * @Date: 2019/3/27 16:20
     */
    @PostMapping("/findSinglePermissionInConditionPage")
    @ApiOperation(value = "门禁单人信息根据条件分页查询", response = PageInfo.class)
    public Object findSinglePermissionInConditionPage(@RequestBody SinglePermissionQueryDto singlePermissionQueryDto) {
        return WrapMapper.ok(singlePermissionService.findSinglePermissionInConditionPage(singlePermissionQueryDto));
    }
}