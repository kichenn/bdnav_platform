package com.bdxh.backend.controller.school;

import com.bdxh.common.helper.tree.bean.TreeBean;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.*;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.feign.SchoolClassControllerClient;
import com.bdxh.school.vo.SchoolClassTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schoolClassWebController")
@Validated
@Slf4j
@Api(value = "学校学生院系", tags = "学校学生院系交互API")
public class SchoolClassWebController {

    @Autowired
    private SchoolClassControllerClient schoolClassControllerClient;

    @RequestMapping(value = "/findSchoolClassTreeBySchoolId", method = RequestMethod.GET)
    @ApiOperation(value = "学校id递归查询院校结构关系", response = SchoolClassTreeVo.class)
    public Object findSchoolsInConditionPaging(@RequestParam("schoolId") Long schoolId) {
        Wrapper wrapper = schoolClassControllerClient.findSchoolClassTreeBySchoolId(schoolId);
        return WrapMapper.ok(wrapper.getResult());
    }


    @RequestMapping(value = "/findSchoolClassById", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询院系信息", response = SchoolClass.class)
    public Object findSchoolClassById(@RequestParam("id") Long id) {
        Wrapper wrapper = schoolClassControllerClient.findSchoolClassById(id);
        return wrapper;
    }


    @RequestMapping(value = "/addSchoolClass", method = RequestMethod.POST)
    @ApiOperation(value = "增加院校结构关系", response = Boolean.class)
    public Object addSchoolClass(@Validated @RequestBody SchoolClassDto schoolClassDto) {
        Wrapper wrapper = schoolClassControllerClient.addSchoolClass(schoolClassDto);
        return WrapMapper.ok(wrapper.getResult());
    }


    @RequestMapping(value = "/modifySchoolClass", method = RequestMethod.PATCH)
    @ApiOperation(value = "修改院校结构关系", response = Boolean.class)
    public Object modifySchoolClass(@Validated @RequestBody SchoolClassModifyDto schoolClassModifyDto) {
        Wrapper wrapper = schoolClassControllerClient.modifySchoolClass(schoolClassModifyDto);
        return WrapMapper.ok(wrapper.getResult());
    }

    @RequestMapping(value = "/delSchoolClassById", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除院校关系", response = Boolean.class)
    public Object delSchoolClassById(@RequestParam("id") Long id) {
        Wrapper wrapper = schoolClassControllerClient.delSchoolClassById(id);
        return WrapMapper.ok(wrapper.getResult());
    }

    @RequestMapping(value = "/batchDelSchoolClassInIds", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ids批量删除院校关系", response = Boolean.class)
    public Object batchDelSchoolClassInIds(@RequestBody List<Long> ids) {
        Wrapper wrapper = schoolClassControllerClient.batchDelSchoolClassInIds(ids);
        return WrapMapper.ok(wrapper.getResult());
    }

    @RequestMapping(value = "/delSchoolClassBySchoolId", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据院校id 删除该院校底下所有关系", response = Boolean.class)
    public Object delSchoolClassBySchoolId(@RequestParam("schoolId") Long schoolId) {
        Wrapper wrapper = schoolClassControllerClient.delSchoolClassBySchoolId(schoolId);
        return WrapMapper.ok(wrapper.getResult());
    }
}
