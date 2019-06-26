package com.bdxh.client.controller.school;

import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.ClassAdministratorsUpdateDto;
import com.bdxh.school.dto.SchoolOrgAddDto;
import com.bdxh.school.dto.SchoolOrgQueryDto;
import com.bdxh.school.dto.SchoolOrgUpdateDto;
import com.bdxh.school.entity.SchoolOrg;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolOrgControllerClient;
import com.bdxh.user.entity.Student;
import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.feign.TeacherControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-06-03 20:25
 **/
@RestController
@RequestMapping("/schoolOrgWebController")
@Slf4j
@Validated
@Api(value = "学校组织架构控制器", tags = "学校组织架构控制器")
public class SchoolOrgWebController {

    @Autowired
    private SchoolOrgControllerClient schoolOrgControllerClient;

    @Autowired
    private StudentControllerClient studentControllerClient;

    @Autowired
    private TeacherControllerClient teacherControllerClient;

    /**
     * 根据条件查询所有的学校组织架构信息
     *
     * @param schoolOrgQueryDto
     * @return
     */
    @RequestMapping(value = "/findAllSchoolOrgInfo", method = RequestMethod.POST)
    @ApiOperation(value = "根据条件查询所有的学校组织架构信息")
    public Object findAllSchoolOrgInfo(@RequestBody SchoolOrgQueryDto schoolOrgQueryDto) {
        return schoolOrgControllerClient.findAllSchoolOrgInfo(schoolOrgQueryDto);
    }

    /**
     * 查询单个学校的组织架构树形数据结构信息
     *
     * @return
     */
    @RequestMapping(value = "/findSchoolOrgTreeInfoBySchoolId", method = RequestMethod.GET)
    @ApiOperation(value = "查询单个学校的组织架构树形数据结构信息")
    public Object findSchoolOrgTreeInfo() {
        //获取当前用户
        SchoolUser user = SecurityUtils.getCurrentUser();
        return schoolOrgControllerClient.findSchoolOrgTreeInfo(user.getSchoolId());
    }

    /**
     * 根据条件查询单个学校组织架构信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/findSchoolOrgInfo", method = RequestMethod.GET)
    @ApiOperation(value = "根据条件查询单个学校组织架构信息")
    public Object findSchoolOrgInfo(@NotNull(message = "id不能为空") @RequestParam("id") Long id) {

        SchoolOrg schoolOrgs = schoolOrgControllerClient.findSchoolOrgInfo(id).getResult();
        return schoolOrgControllerClient.findSchoolOrgInfo(id);
    }


    /**
     * 根据学校Id查询学生组织架构信息
     *
     * @param schoolId
     * @return
     */
    @RequestMapping(value = "/findClassOrg", method = RequestMethod.GET)
    @ApiOperation(value = "根据学校Id查询学生组织架构信息")
    public Object findClassOrg(@NotNull(message = "schoolId不能为空") @RequestParam("schoolId") Long schoolId) {
        return schoolOrgControllerClient.findClassOrg(schoolId);
    }


    /**
     * 根据ID删除组织架构信息
     *
     * @param id
     * @return
     */
    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/removeSchoolOrgInfo", method = RequestMethod.POST)
    @ApiOperation(value = "根据ID删除组织架构信息")
    public Object removeSchoolOrgInfo(@NotNull(message = "id不能为空") @RequestParam("id") Long id) {
        //删除组织时，查看院系底下是否还存在子组织信息
        List<SchoolOrg> schoolOrgs = schoolOrgControllerClient.findBySchoolOrgByParentId(id).getResult();
        Student student = new Student();
        TeacherDept teacher =  new TeacherDept();
        //院系底下不存在子院系，查看当前院系是否存在人员
        if (CollectionUtils.isEmpty(schoolOrgs)) {
            SchoolOrg thisSchoolOrg = schoolOrgControllerClient.findSchoolOrgInfo(id).getResult();
            student = studentControllerClient.findStudentBySchoolClassId(thisSchoolOrg.getSchoolCode(), thisSchoolOrg.getSchoolId(), id).getResult();
            teacher = teacherControllerClient.findTeacherBySchoolDeptId(thisSchoolOrg.getSchoolCode(), thisSchoolOrg.getSchoolId(), id).getResult();
        }
        if (CollectionUtils.isNotEmpty(schoolOrgs)) {
            return WrapMapper.error("该组织底下存在其他组织不能删除");
        } else if (student != null || teacher != null) {
            return WrapMapper.error("该组织底下存在人员不能删除");
        }
        return schoolOrgControllerClient.removeSchoolOrgInfo(id);
    }


    /**
     * 修改组织架构信息
     *
     * @param schoolOrgUpdateDto
     * @return
     */
    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/updateSchoolOrgInfo", method = RequestMethod.POST)
    @ApiOperation(value = "修改组织架构信息")
    public Object updateSchoolOrgInfo(@RequestBody SchoolOrgUpdateDto schoolOrgUpdateDto) {
        SchoolUser user = SecurityUtils.getCurrentUser();
        schoolOrgUpdateDto.setOperator(user.getId());
        schoolOrgUpdateDto.setOperatorName(user.getUserName());
        schoolOrgUpdateDto.setUpdateDate(new Date());
        schoolOrgUpdateDto.setSchoolId(user.getSchoolId());
        schoolOrgUpdateDto.setSchoolCode(user.getSchoolCode());
        return schoolOrgControllerClient.updateSchoolOrgInfo(schoolOrgUpdateDto);
    }

    /**
     * 查询所有组织架构信息
     *
     * @return
     */
    @RequestMapping(value = "/findAllSchoolOrgInfo", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有组织架构信息")
    public Object findAllOrgInfo() {
        return schoolOrgControllerClient.findAllOrgInfo();
    }

    /**
     * 根据父ID查询学校组织架构
     *
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/schoolOrg/findBySchoolOrgByParentId", method = RequestMethod.GET)
    @ApiOperation(value = "根据父ID查询学校组织架构")
    public Object findBySchoolOrgByParentId(@RequestParam("parentId") @NotNull(message = "父级ID不能为空") Long parentId) {
        return schoolOrgControllerClient.findBySchoolOrgByParentId(parentId);
    }

    /**
     * 新增组织架构
     *
     * @param schoolOrgAddDto
     * @return
     */
    @RequestMapping(value = "/insertSchoolOrgInfo", method = RequestMethod.POST)
    @ApiOperation(value = "新增组织架构")
    public Object insertSchoolOrgInfo(@RequestBody SchoolOrgAddDto schoolOrgAddDto) {
        SchoolUser user = SecurityUtils.getCurrentUser();
        schoolOrgAddDto.setSchoolCode(user.getSchoolCode());
        schoolOrgAddDto.setSchoolId(user.getSchoolId());
        schoolOrgAddDto.setOperator(user.getId());
        schoolOrgAddDto.setOperatorName(user.getUserName());
        return schoolOrgControllerClient.insertSchoolOrgInfo(schoolOrgAddDto);
    }

    /**
     * 修改班级管理员信息
     * @param classAdministratorsUpdateDto
     * @return
     */
    @RequestMapping(value = "/updateSchoolClassInfo", method = RequestMethod.POST)
    @ApiOperation(value = "修改班级管理员信息")
    public Object updateSchoolClassInfo(@Validated @RequestBody ClassAdministratorsUpdateDto classAdministratorsUpdateDto) {
        SchoolUser user = SecurityUtils.getCurrentUser();
        classAdministratorsUpdateDto.setOperator(user.getId());
        classAdministratorsUpdateDto.setOperatorName(user.getUserName());
        classAdministratorsUpdateDto.setUpdateDate(new Date());
        return schoolOrgControllerClient.updateSchoolClassInfo(classAdministratorsUpdateDto);
    }

    /**
     * 查询出老师的树形结构数据
     * @param schoolId
     * @return
     */
    @RequestMapping(value = "/findTeacherDeptInfo", method = RequestMethod.GET)
    @ApiOperation(value = "根据学校Id查询学生组织架构信息")
    public Object findTeacherDeptInfo(@NotNull(message = "schoolId不能为空") @RequestParam("schoolId") Long schoolId) {
        return schoolOrgControllerClient.findTeacherDeptInfo(schoolId);
    }
}