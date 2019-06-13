package com.bdxh.client.controller.school;

import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolFenceDto;
import com.bdxh.school.dto.FenceEntityDto;
import com.bdxh.school.dto.ModifySchoolFenceDto;
import com.bdxh.school.dto.SchoolFenceQueryDto;
import com.bdxh.school.entity.*;
import com.bdxh.school.enums.GroupTypeEnum;
import com.bdxh.school.feign.SchoolClassControllerClient;
import com.bdxh.school.feign.SchoolDeptControllerClient;
import com.bdxh.school.feign.SchoolFenceControllerClient;
import com.bdxh.school.feign.SchoolOrgControllerClient;
import com.bdxh.school.vo.SchoolFenceShowVo;
import com.bdxh.user.entity.Student;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.feign.TeacherControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-04-11 09:56:14
 */
@RestController
@RequestMapping("/clientSchoolFenceWeb")
@Slf4j
@Validated
@Api(value = "学校管理--学校围栏", tags = "学校管理--学校围栏交互API")
public class SchoolFenceWebController {

    @Autowired
    private SchoolFenceControllerClient schoolFenceControllerClient;

/*    @Autowired
    private SchoolClassControllerClient schoolClassControllerClient;

    @Autowired
    private SchoolDeptControllerClient schoolDeptControllerClient;*/

    @Autowired
    private SchoolOrgControllerClient schoolOrgControllerClient;

    @Autowired
    private StudentControllerClient studentControllerClient;

    @Autowired
    private TeacherControllerClient teacherControllerClient;

    /**
     * 设置监控对象
     *
     * @param groupTypeEnum
     * @param groupId
     * @return
     */
    private List<FenceEntityDto> setFenceEntity(GroupTypeEnum groupTypeEnum, Long groupId) {
        //设置监控对象list
        List<FenceEntityDto> fenceEntitys = new ArrayList<>();

        // 查询用户群类型 1 学生 2 老师
        if (new Byte("1").equals(groupTypeEnum.getKey())) {
            //学生院系信息
            SchoolOrg schoolClass = schoolOrgControllerClient.findSchoolOrgInfo(groupId).getResult();
            //父ids+当前id
            String classIds = schoolClass.getParentIds() + schoolClass.getId();
            if (schoolClass.getParentIds().contains(",")) {
                classIds = schoolClass.getParentIds().substring(schoolClass.getParentIds().indexOf(",")) + schoolClass.getId();
            }
            //增加监控对象信息
            List<Student> students = Optional.ofNullable(studentControllerClient.findStudentInfoByClassOrg(schoolClass.getSchoolCode(), classIds, schoolClass.getOrgType()).getResult()).orElse(new ArrayList<>());//Optional.of().orElse(new ArrayList<>());
            students.forEach(e -> {
                FenceEntityDto fenceEntity = new FenceEntityDto();
                fenceEntity.setId(e.getId());
                fenceEntity.setName(e.getName());
                fenceEntity.setSchoolName(e.getSchoolName());
                fenceEntity.setClassName(e.getClassName());
                fenceEntity.setGroupTypeEnum(GroupTypeEnum.STUDENT);
                StringBuilder sbDesc = new StringBuilder();
                sbDesc.append("学校id:" + e.getSchoolId() + ",")
                        .append("校区名称:" + e.getCampusName() + ",")
                        .append("学院名称:" + e.getCollegeName() + ",")
                        .append("系名称:" + e.getFacultyName() + ",")
                        .append("专业名称:" + e.getProfessionName() + ",")
                        .append("年级名称:" + e.getGradeName() + ",")
                        .append("班级名称:" + e.getClassName() + ",")
                        .append("学生id:" + e.getId());
                fenceEntity.setDesc(sbDesc.toString());
                fenceEntitys.add(fenceEntity);
            });
        } else if (new Byte("2").equals(groupTypeEnum.getKey())) {
            SchoolOrg schoolDept = schoolOrgControllerClient.findSchoolOrgInfo(groupId).getResult();
            //父ids+当前id
            String classIds = schoolDept.getParentIds() + schoolDept.getId();
            if (schoolDept.getParentIds().contains(",")) {
                classIds = schoolDept.getParentIds().substring(schoolDept.getParentIds().indexOf(",")) + schoolDept.getId();
            }
            //增加监控对象信息
            List<Teacher> teachers = Optional.ofNullable(teacherControllerClient.findTeacherInfoByDeptOrg(schoolDept.getSchoolCode(), classIds).getResult()).orElse(new ArrayList<>());//Optional.of().orElse(new ArrayList<>());
            teachers.forEach(e -> {
                FenceEntityDto fenceEntity = new FenceEntityDto();
                fenceEntity.setId(e.getId());
                fenceEntity.setName(e.getName());
                fenceEntity.setSchoolName(e.getSchoolName());
                fenceEntity.setClassName("");
                fenceEntity.setGroupTypeEnum(GroupTypeEnum.STUDENT);
                StringBuilder sbDesc = new StringBuilder();
                sbDesc.append("学校id:" + e.getSchoolId() + ",")
                        .append("校区名称:" + e.getCampusName() + ",")
                        .append("老师id:" + e.getId());
                fenceEntity.setDesc(sbDesc.toString());
                fenceEntitys.add(fenceEntity);
            });
        }
        return fenceEntitys;
    }


    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/addFence", method = RequestMethod.POST)
    @ApiOperation(value = "增加学校围栏", response = Boolean.class)
    public Object addFence(@Validated @RequestBody AddSchoolFenceDto addSchoolFenceDto) {
        SchoolUser user = SecurityUtils.getCurrentUser();
        addSchoolFenceDto.setOperator(user.getId());
        addSchoolFenceDto.setOperatorName(user.getUserName());
        addSchoolFenceDto.setSchoolCode(user.getSchoolCode());
        addSchoolFenceDto.setSchoolId(user.getSchoolId());
        List<FenceEntityDto> fenceEntitys = setFenceEntity(addSchoolFenceDto.getGroupTypeEnum(), addSchoolFenceDto.getGroupId());
        if (CollectionUtils.isEmpty(fenceEntitys)) {
            return WrapMapper.error("监控对象为空，创建围栏失败");
        }
        addSchoolFenceDto.setFenceEntityDtos(fenceEntitys);
        Wrapper wapper = schoolFenceControllerClient.addFence(addSchoolFenceDto);
        return wapper;
    }

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/modifyFence", method = RequestMethod.POST)
    @ApiOperation(value = "修改学校围栏", response = Boolean.class)
    public Object modifyFence(@Validated @RequestBody ModifySchoolFenceDto modifySchoolFenceDto) {
        SchoolUser user = SecurityUtils.getCurrentUser();
        modifySchoolFenceDto.setOperator(user.getId());
        modifySchoolFenceDto.setOperatorName(user.getUserName());
        modifySchoolFenceDto.setSchoolCode(user.getSchoolCode());
        modifySchoolFenceDto.setSchoolId(user.getSchoolId());
        SchoolFence superSchoolFence = Optional.ofNullable(schoolFenceControllerClient.findFenceById(modifySchoolFenceDto.getId()).getResult()).orElse(new SchoolFence());
        //此围栏底下的人员有变动
        if ((!superSchoolFence.getGroupType().equals(modifySchoolFenceDto.getGroupTypeEnum().getKey())) || (
                !superSchoolFence.getGroupId().equals(modifySchoolFenceDto.getGroupId()))) {
            List<FenceEntityDto> fenceEntitys = setFenceEntity(modifySchoolFenceDto.getGroupTypeEnum(), modifySchoolFenceDto.getGroupId());
            modifySchoolFenceDto.setFenceEntityDtos(fenceEntitys);
        }
        Wrapper wapper = schoolFenceControllerClient.modifyFence(modifySchoolFenceDto);
        return wapper;
    }

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/delFenceById", method = RequestMethod.POST)
    @ApiOperation(value = "删除学校围栏", response = Boolean.class)
    public Object delFenceById(@RequestParam("id") Long id) {
        Wrapper wapper = schoolFenceControllerClient.delFenceById(id);
        return wapper;
    }

    @RequestMapping(value = "/findFenceById", method = RequestMethod.GET)
    @ApiOperation(value = "id查询学校围栏", response = SchoolFence.class)
    public Object findFenceById(@RequestParam("id") Long id) {
        Wrapper wapper = schoolFenceControllerClient.findFenceById(id);
        return WrapMapper.ok(wapper.getResult());
    }

    @RequestMapping(value = "/findFenceInConditionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "分页学校围栏查询", response = SchoolFenceShowVo.class)
    public Object findFenceInConditionPaging(@Validated @RequestBody SchoolFenceQueryDto schoolFenceQueryDto) {
        SchoolUser user = SecurityUtils.getCurrentUser();
        schoolFenceQueryDto.setSchoolId(user.getSchoolId());
        Wrapper wapper = schoolFenceControllerClient.findFenceInConditionPaging(schoolFenceQueryDto);
        return WrapMapper.ok(wapper.getResult());
    }

}