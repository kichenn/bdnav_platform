package com.bdxh.backend.controller.school;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.*;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.feign.SchoolClassControllerClient;
import com.bdxh.school.vo.SchoolClassTreeVo;
import com.bdxh.user.entity.Student;
import com.bdxh.user.feign.StudentControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolClassWebController")
@Validated
@Slf4j
@Api(value = "学校学生院系", tags = "学校学生院系交互API")
public class SchoolClassWebController {

    @Autowired
    private SchoolClassControllerClient schoolClassControllerClient;

    @Autowired
    private StudentControllerClient studentControllerClient;

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


    @RequestMapping(value = "/modifySchoolClass", method = RequestMethod.POST)
    @ApiOperation(value = "修改院校结构关系", response = Boolean.class)
    public Object modifySchoolClass(@Validated @RequestBody SchoolClassModifyDto schoolClassModifyDto) {
        Wrapper wrapper = schoolClassControllerClient.modifySchoolClass(schoolClassModifyDto);
        return WrapMapper.ok(wrapper.getResult());
    }

    @RequestMapping(value = "/delSchoolClassById", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除院校关系", response = Boolean.class)
    public Object delSchoolClassById(@RequestParam("id") Long id) {
        //删除该院系时，查看院系底下是否还存在子院系
        SchoolClass schoolClass = schoolClassControllerClient.findSchoolClassByParentId(id).getResult();
        Student student = null;
        //院系底下不存在子院系，查看当前院系是否存在人员
        if (schoolClass == null) {
            student = studentControllerClient.findStudentBySchoolClassId(schoolClass.getSchoolCode(), schoolClass.getSchoolId(), id).getResult();
        }
        if (schoolClass != null) {
            return WrapMapper.error("该院系底下存在子院系不能删除");
        } else if (student != null) {
            return WrapMapper.error("该院系底下存在人员不能删除");
        }
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
