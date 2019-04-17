package com.bdxh.backend.controller.school;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.SchoolClassDto;
import com.bdxh.school.dto.SchoolClassModifyDto;
import com.bdxh.school.dto.SchoolDeptDto;
import com.bdxh.school.dto.SchoolDeptModifyDto;
import com.bdxh.school.entity.SchoolDept;
import com.bdxh.school.feign.SchoolClassControllerClient;
import com.bdxh.school.feign.SchoolDeptControllerClient;
import com.bdxh.school.vo.SchoolClassTreeVo;
import com.bdxh.school.vo.SchoolDeptTreeVo;
import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.feign.TeacherControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolDeptWebController")
@Validated
@Slf4j
@Api(value = "学校老师部门", tags = "学校老师部门交互API")
public class SchoolDeptWebController {

    @Autowired
    private SchoolDeptControllerClient schoolDeptControllerClient;

    @Autowired
    private TeacherControllerClient teacherControllerClient;

    @RequestMapping(value = "/findSchoolDeptTreeBySchoolId", method = RequestMethod.GET)
    @ApiOperation(value = "学校id递归查询部门关系", response = SchoolDeptTreeVo.class)
    public Object findSchoolDeptTreeBySchoolId(@RequestParam("schoolId") Long schoolId) {
        Wrapper<List<SchoolDeptTreeVo>> wrapper = schoolDeptControllerClient.findSchoolDeptTreeBySchoolId(schoolId);
        return WrapMapper.ok(wrapper.getResult());
    }


    @RequestMapping(value = "/findSchoolDeptById", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询部门关系信息", response = SchoolDept.class)
    public Object findSchoolDeptById(@RequestParam("id") Long id) {
        Wrapper wrapper = schoolDeptControllerClient.findSchoolDeptById(id);
        return WrapMapper.ok(wrapper.getResult());
    }


    @RequestMapping(value = "/addSchoolDept", method = RequestMethod.POST)
    @ApiOperation(value = "增加学校的部门关系信息", response = Boolean.class)
    public Object addSchoolDept(@Validated @RequestBody SchoolDeptDto schoolDeptDto) {
        Wrapper wrapper = schoolDeptControllerClient.addSchoolDept(schoolDeptDto);
        return WrapMapper.ok(wrapper.getResult());
    }


    @RequestMapping(value = "/modifySchoolDept", method = RequestMethod.POST)
    @ApiOperation(value = "修改学校的部门关系信息", response = Boolean.class)
    public Object modifySchoolDept(@Validated @RequestBody SchoolDeptModifyDto schoolDeptDto) {
        Wrapper wrapper = schoolDeptControllerClient.modifySchoolDept(schoolDeptDto);
        return WrapMapper.ok(wrapper.getResult());
    }

    @RequestMapping(value = "/delSchoolDeptById", method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除部门关系信息", response = Boolean.class)
    public Object delSchoolDeptById(@RequestParam("id") Long id) {
        //删除该部门时，查看部门底下是否还存在子部门
        List<SchoolDept> schoolDept = schoolDeptControllerClient.findSchoolDeptByParentId(id).getResult();
        TeacherDept teacherDept = null;
        if (CollectionUtils.isEmpty(schoolDept)) {
            SchoolDept thisSchoolDept = schoolDeptControllerClient.findSchoolDeptById(id).getResult();
            teacherDept = teacherControllerClient.findTeacherBySchoolDeptId(thisSchoolDept.getSchoolCode(), thisSchoolDept.getSchoolId(), id).getResult();
        }
        if (CollectionUtils.isNotEmpty(schoolDept)) {
            return WrapMapper.error("该部门底下存在子部门不能删除");
        } else if (teacherDept != null) {
            return WrapMapper.error("该部门底下存在人员不能删除");
        }
        Wrapper wrapper = schoolDeptControllerClient.delSchoolDeptById(id);
        return WrapMapper.ok(wrapper.getResult());
    }

    @RequestMapping(value = "/batchDelSchoolDeptInIds", method = RequestMethod.POST)
    @ApiOperation(value = "根据ids批量删除学校部门信息", response = Boolean.class)
    public Object batchDelSchoolDeptInIds(@RequestBody List<Long> ids) {
        Wrapper wrapper = schoolDeptControllerClient.batchDelSchoolDeptInIds(ids);
        return WrapMapper.ok(wrapper.getResult());
    }

    @RequestMapping(value = "/delSchoolDeptBySchoolId", method = RequestMethod.GET)
    @ApiOperation(value = "根据院校id 删除该学校部门所有关系", response = Boolean.class)
    public Object delSchoolDeptBySchoolId(@RequestParam("schoolId") Long schoolId) {
        Wrapper wrapper = schoolDeptControllerClient.delSchoolDeptBySchoolId(schoolId);
        return WrapMapper.ok(wrapper.getResult());
    }
}
