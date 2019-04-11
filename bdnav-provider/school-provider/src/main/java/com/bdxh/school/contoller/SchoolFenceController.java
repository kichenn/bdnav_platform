package com.bdxh.school.contoller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.AddBlackUrlDto;
import com.bdxh.school.dto.AddSchoolFenceDto;
import com.bdxh.school.dto.ModifySchoolFenceDto;
import com.bdxh.school.dto.SchoolFenceQueryDto;
import com.bdxh.school.entity.BlackUrl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bdxh.school.entity.SchoolFence;
import com.bdxh.school.service.SchoolFenceService;

import java.util.List;

/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-04-11 09:56:14
 */
@RestController
@RequestMapping("/schoolFence")
@Slf4j
@Validated
@Api(value = "学校围栏", tags = "学校围栏交互API")
public class SchoolFenceController {

    @Autowired
    private SchoolFenceService schoolFenceService;

    @RequestMapping(value = "/addFence", method = RequestMethod.POST)
    @ApiOperation(value = "增加学校围栏", response = Boolean.class)
    public Object addFence(@Validated @RequestBody AddSchoolFenceDto addSchoolFenceDto) {
        SchoolFence schoolFence = new SchoolFence();
        BeanUtils.copyProperties(addSchoolFenceDto, schoolFence);
        //设置状态值
        schoolFence.setGroupType(addSchoolFenceDto.getGroupTypeEnum().getKey());
        schoolFence.setRecursionPermission(addSchoolFenceDto.getRecursionPermissionStatusEnum().getKey());
        schoolFence.setStatus(addSchoolFenceDto.getBlackStatusEnum().getKey());

        return WrapMapper.ok(schoolFenceService.save(schoolFence) > 0);
    }

    @RequestMapping(value = "/modifyFence", method = RequestMethod.POST)
    @ApiOperation(value = "修改学校围栏", response = Boolean.class)
    public Object modifyFence(@Validated @RequestBody ModifySchoolFenceDto modifySchoolFenceDto) {
        SchoolFence schoolFence = new SchoolFence();
        BeanUtils.copyProperties(modifySchoolFenceDto, schoolFence);
        //设置状态值
        if (modifySchoolFenceDto.getGroupTypeEnum() != null) {
            schoolFence.setGroupType(modifySchoolFenceDto.getGroupTypeEnum().getKey());
        }
        if (modifySchoolFenceDto.getRecursionPermissionStatusEnum() != null) {
            schoolFence.setRecursionPermission(modifySchoolFenceDto.getRecursionPermissionStatusEnum().getKey());
        }
        if (modifySchoolFenceDto.getBlackStatusEnum() != null) {
            schoolFence.setStatus(modifySchoolFenceDto.getBlackStatusEnum().getKey());
        }
        return WrapMapper.ok(schoolFenceService.update(schoolFence) > 0);
    }

    @RequestMapping(value = "/delFenceById", method = RequestMethod.POST)
    @ApiOperation(value = "删除学校围栏", response = Boolean.class)
    public Object delFenceById(@RequestParam("id") Long id) {
        return WrapMapper.ok(schoolFenceService.deleteByKey(id) > 0);
    }

    @RequestMapping(value = "/delBatchFence", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除学校围栏", response = Boolean.class)
    public Object delBatchFence(@RequestParam("ids") List<Long> ids) {
        return WrapMapper.ok(schoolFenceService.batchDelSchoolFenceInIds(ids));
    }

    @RequestMapping(value = "/findFenceById", method = RequestMethod.GET)
    @ApiOperation(value = "id查询学校围栏", response = SchoolFence.class)
    public Object findFenceById(@RequestParam("id") Long id) {
        return WrapMapper.ok(schoolFenceService.selectByKey(id));
    }

    @RequestMapping(value = "/findFenceInConditionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询", response = Boolean.class)
    public Object addBlack(@Validated @RequestBody SchoolFenceQueryDto schoolFenceQueryDto) {
        return WrapMapper.ok();
    }

}