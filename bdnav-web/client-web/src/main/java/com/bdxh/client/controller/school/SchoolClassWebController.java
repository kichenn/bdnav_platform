package com.bdxh.client.controller.school;

import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.SchoolClassDto;
import com.bdxh.school.dto.SchoolClassModifyDto;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolClassControllerClient;
import com.bdxh.school.vo.SchoolClassTreeVo;
import com.bdxh.user.entity.Student;
import com.bdxh.user.feign.StudentControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/clientSchoolClassWeb")
@Validated
@Slf4j
@Api(value = "学校管理员--学生院系", tags = "学校管理员--学生院系交互API")
public class SchoolClassWebController {

    @Autowired
    private SchoolClassControllerClient schoolClassControllerClient;

    @Autowired
    private StudentControllerClient studentControllerClient;

    @RequestMapping(value = "/findSchoolClassTreeBySchoolId", method = RequestMethod.GET)
    @ApiOperation(value = "学校用户查询当前--院校结构关系", response = SchoolClassTreeVo.class)
    public Object findSchoolsInConditionPaging() {
        //获取当前用户
        SchoolUser user = SecurityUtils.getCurrentUser();
        Wrapper<List<SchoolClassTreeVo>> wrapper = schoolClassControllerClient.findSchoolClassTreeBySchoolId(user.getSchoolId());
        return WrapMapper.ok(wrapper.getResult());
    }


    @RequestMapping(value = "/findSchoolClassById", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询院系信息", response = SchoolClass.class)
    public Object findSchoolClassById(@RequestParam("id") Long id) {
        Wrapper wrapper = schoolClassControllerClient.findSchoolClassById(id);
        return WrapMapper.ok(wrapper.getResult());
    }


    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/addSchoolClass", method = RequestMethod.POST)
    @ApiOperation(value = "增加院校结构关系（需学校admin权限）", response = Boolean.class)
    public Object addSchoolClass(@Validated @RequestBody SchoolClassDto schoolClassDto) {
        //获取当前用户
        SchoolUser user = SecurityUtils.getCurrentUser();
        schoolClassDto.setSchoolCode(user.getSchoolCode());
        schoolClassDto.setSchoolId(user.getSchoolId());
        schoolClassDto.setOperator(user.getId());
        schoolClassDto.setOperatorName(user.getUserName());
        Wrapper wrapper = schoolClassControllerClient.addSchoolClass(schoolClassDto);
        return wrapper;
    }

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/modifySchoolClass", method = RequestMethod.POST)
    @ApiOperation(value = "修改院校结构关系（需学校admin权限）", response = Boolean.class)
    public Object modifySchoolClass(@Validated @RequestBody SchoolClassModifyDto schoolClassModifyDto) {
        //获取当前用户
        SchoolUser user = SecurityUtils.getCurrentUser();
        schoolClassModifyDto.setOperator(user.getId());
        schoolClassModifyDto.setOperatorName(user.getUserName());
        schoolClassModifyDto.setUpdateDate(new Date());
        schoolClassModifyDto.setSchoolId(user.getSchoolId());
        schoolClassModifyDto.setSchoolCode(user.getSchoolCode());
        Wrapper wrapper = schoolClassControllerClient.modifySchoolClass(schoolClassModifyDto);
        return wrapper;
    }

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/delSchoolClassById", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除院校关系（需学校admin权限）", response = Boolean.class)
    public Object delSchoolClassById(@RequestParam("id") Long id) {
        //删除该院系时，查看院系底下是否还存在子院系
        List<SchoolClass> schoolClass = schoolClassControllerClient.findSchoolClassByParentId(id).getResult();
        Student student = null;
        //院系底下不存在子院系，查看当前院系是否存在人员
        if (CollectionUtils.isEmpty(schoolClass)) {
            SchoolClass thisSchoolClass = schoolClassControllerClient.findSchoolClassById(id).getResult();
            student = studentControllerClient.findStudentBySchoolClassId(thisSchoolClass.getSchoolCode(), thisSchoolClass.getSchoolId(), id).getResult();
        }
        if (CollectionUtils.isNotEmpty(schoolClass)) {
            return WrapMapper.error("该院系底下存在子院系不能删除");
        } else if (student != null) {
            return WrapMapper.error("该院系底下存在人员不能删除");
        }
        Wrapper wrapper = schoolClassControllerClient.delSchoolClassById(id);
        return wrapper;
    }
}
