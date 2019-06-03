package com.bdxh.backend.controller.school;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.SchoolOrgQueryDto;
import com.bdxh.school.dto.SchoolOrgUpdateDto;
import com.bdxh.school.feign.SchoolOrgControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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


    /**
     * 根据条件查询所有的学校组织架构信息
     *
     * @param schoolOrgQueryDto
     * @return
     */
    @RequestMapping(value = "/findAllSchoolOrgInfo", method = RequestMethod.POST)
    @ApiOperation(value = "根据条件查询所有的学校组织架构信息")
    public Object findAllSchoolOrgInfo(@RequestBody SchoolOrgQueryDto schoolOrgQueryDto) {
        return WrapMapper.ok(schoolOrgControllerClient.findAllSchoolOrgInfo(schoolOrgQueryDto));
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
        return WrapMapper.ok(schoolOrgControllerClient.findSchoolOrgTreeInfo(schoolId));
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
    public Object findClassOrg(@NotBlank(message = "schoolId不能为空") @RequestParam("schoolId") Long schoolId) {
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
}