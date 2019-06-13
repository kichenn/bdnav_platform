package com.bdxh.backend.controller.school;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.SchoolOrgAddDto;
import com.bdxh.school.dto.SchoolOrgQueryDto;
import com.bdxh.school.dto.SchoolOrgUpdateDto;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.entity.SchoolOrg;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolOrgControllerClient;
import com.bdxh.system.entity.User;
import com.bdxh.user.entity.Student;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.feign.TeacherControllerClient;
import com.bdxh.user.feign.TeacherDeptControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
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
     * @param schoolId
     * @return
     */
    @RequestMapping(value = "/findSchoolOrgTreeInfoBySchoolId", method = RequestMethod.GET)
    @ApiOperation(value = "查询单个学校的组织架构树形数据结构信息")
    public Object findSchoolOrgTreeInfo(@NotNull(message = "学校id不能为空") @RequestParam("schoolId") Long schoolId) {
        return schoolOrgControllerClient.findSchoolOrgTreeInfo(schoolId);
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
        } else if (student != null && teacher != null) {
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
    @RequestMapping(value = "/updateSchoolOrgInfo", method = RequestMethod.POST)
    @ApiOperation(value = "修改组织架构信息")
    public Object updateSchoolOrgInfo(@Validated @RequestBody SchoolOrgUpdateDto schoolOrgUpdateDto) {
        User user = SecurityUtils.getCurrentUser();
        schoolOrgUpdateDto.setOperator(user.getId());
        schoolOrgUpdateDto.setOperatorName(user.getUserName());
        schoolOrgUpdateDto.setUpdateDate(new Date());
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
     * @param schoolOrgAddDto
     * @return
     */
    @RequestMapping(value = "/insertSchoolOrgInfo",method = RequestMethod.POST)
    @ApiOperation(value = "新增组织架构")
    public Object insertSchoolOrgInfo(@RequestBody SchoolOrgAddDto schoolOrgAddDto){
        User user = SecurityUtils.getCurrentUser();
        schoolOrgAddDto.setOperator(user.getId());
        schoolOrgAddDto.setOperatorName(user.getUserName());
        schoolOrgAddDto.setUpdateDate(new Date());
        return schoolOrgControllerClient.insertSchoolOrgInfo(schoolOrgAddDto);
    }


}