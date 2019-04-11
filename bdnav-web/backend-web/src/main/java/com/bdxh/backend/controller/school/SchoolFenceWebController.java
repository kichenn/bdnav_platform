package com.bdxh.backend.controller.school;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolFenceDto;
import com.bdxh.school.dto.ModifySchoolFenceDto;
import com.bdxh.school.dto.SchoolFenceQueryDto;
import com.bdxh.school.entity.SchoolFence;
import com.bdxh.school.feign.SchoolFenceControllerClient;
import com.bdxh.system.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-04-11 09:56:14
 */
@RestController
@RequestMapping("/schoolFenceWebController")
@Slf4j
@Validated
@Api(value = "学校围栏", tags = "学校围栏交互API")
public class SchoolFenceWebController {

    @Autowired
    private SchoolFenceControllerClient schoolFenceControllerClient;

    @RequestMapping(value = "/addFence", method = RequestMethod.POST)
    @ApiOperation(value = "增加学校围栏", response = Boolean.class)
    public Object addFence(@Validated @RequestBody AddSchoolFenceDto addSchoolFenceDto) {
        User user = SecurityUtils.getCurrentUser();
        addSchoolFenceDto.setOperator(user.getId());
        addSchoolFenceDto.setOperatorName(user.getUserName());
        Wrapper wapper = schoolFenceControllerClient.addFence(addSchoolFenceDto);
        return wapper;
    }

    @RequestMapping(value = "/modifyFence", method = RequestMethod.POST)
    @ApiOperation(value = "修改学校围栏", response = Boolean.class)
    public Object modifyFence(@Validated @RequestBody ModifySchoolFenceDto modifySchoolFenceDto) {
        User user = SecurityUtils.getCurrentUser();
        modifySchoolFenceDto.setOperator(user.getId());
        modifySchoolFenceDto.setOperatorName(user.getUserName());
        Wrapper wapper = schoolFenceControllerClient.modifyFence(modifySchoolFenceDto);
        return wapper;
    }

    @RequestMapping(value = "/delFenceById", method = RequestMethod.POST)
    @ApiOperation(value = "删除学校围栏", response = Boolean.class)
    public Object delFenceById(@RequestParam("id") Long id) {
        Wrapper wapper = schoolFenceControllerClient.delFenceById(id);
        return wapper;
    }

    @RequestMapping(value = "/delBatchFence", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除学校围栏", response = Boolean.class)
    public Object delBatchFence(@RequestParam("ids") List<Long> ids) {
        Wrapper wapper = schoolFenceControllerClient.delBatchFence(ids);
        return wapper;
    }

    @RequestMapping(value = "/findFenceById", method = RequestMethod.GET)
    @ApiOperation(value = "id查询学校围栏", response = SchoolFence.class)
    public Object findFenceById(@RequestParam("id") Long id) {
        Wrapper wapper = schoolFenceControllerClient.findFenceById(id);
        return WrapMapper.ok(wapper.getResult());
    }

    @RequestMapping(value = "/findFenceInConditionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "分页学校围栏查询", response = Boolean.class)
    public Object findFenceInConditionPaging(@Validated @RequestBody SchoolFenceQueryDto schoolFenceQueryDto) {
        Wrapper wapper = schoolFenceControllerClient.findFenceInConditionPaging(schoolFenceQueryDto);
        return WrapMapper.ok(wapper.getResult());
    }

}