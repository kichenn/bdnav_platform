package com.bdxh.school.contoller;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.helper.baidu.yingyan.constant.FenceConstant;
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
        try {
            Boolean result = schoolFenceService.addFence(schoolFence);
            return WrapMapper.ok(result);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
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

        try {
            Boolean result = schoolFenceService.modifyFence(schoolFence);
            return WrapMapper.ok(result);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/delFenceById", method = RequestMethod.POST)
    @ApiOperation(value = "删除学校围栏", response = Boolean.class)
    public Object delFenceById(@RequestParam("id") Long id) {
        try {
            Boolean result = schoolFenceService.delFence(id);
            return WrapMapper.ok(result);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/findFenceById", method = RequestMethod.GET)
    @ApiOperation(value = "id查询学校围栏", response = SchoolFence.class)
    public Object findFenceById(@RequestParam("id") Long id) {
        return WrapMapper.ok(schoolFenceService.selectByKey(id));
    }

    @RequestMapping(value = "/findFenceInConditionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "分页学校围栏查询", response = SchoolFence.class)
    public Object findFenceInConditionPaging(@Validated @RequestBody SchoolFenceQueryDto schoolFenceQueryDto) {
        return WrapMapper.ok(schoolFenceService.findFenceInConditionPaging(schoolFenceQueryDto));
    }

    @RequestMapping(value = "/fencePush", method = RequestMethod.POST)
    @ApiOperation(value = "围栏报警推送消息", response = SchoolFence.class)
    public Object fencePush() {
        System.err.println("报警小推送。。。。。。。。");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", 1);
        jsonObject.put("service_id", FenceConstant.SERVICE_ID);
//        jsonObject.put("SignId", "baidu_yingyan");
        return jsonObject.toJSONString();
    }
}