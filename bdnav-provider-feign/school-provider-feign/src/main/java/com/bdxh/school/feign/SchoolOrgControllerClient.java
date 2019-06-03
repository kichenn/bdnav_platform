package com.bdxh.school.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.SchoolOrgQueryDto;
import com.bdxh.school.dto.SchoolOrgUpdateDto;
import com.bdxh.school.entity.SchoolOrg;
import com.bdxh.school.fallback.SchoolOrgControllerClientFallback;
import com.bdxh.school.vo.SchoolOrgTreeVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @description:
 * @author: binzh
 * @create: 2019-06-03 20:10
 **/
@Service
@FeignClient(value = "school-provider-cluster", fallback = SchoolOrgControllerClientFallback.class)
public interface SchoolOrgControllerClient {

    /**
     * 根据条件查询所有的学校组织架构信息
     *
     * @param schoolOrgQueryDto
     * @return
     */
    @RequestMapping(value = "/findAllSchoolOrgInfo", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<List<SchoolOrg>> findAllSchoolOrgInfo(@RequestBody SchoolOrgQueryDto schoolOrgQueryDto);

    /**
     * 查询单个学校的组织架构树形数据结构信息
     *
     * @param schoolId
     * @return
     */
    @RequestMapping(value = "/findSchoolOrgTreeInfoBySchoolId", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SchoolOrgTreeVo>> findSchoolOrgTreeInfo(@RequestParam("schoolId") Long schoolId);

    /**
     * 根据条件查询单个学校组织架构信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/findSchoolOrgInfo", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<SchoolOrg> findSchoolOrgInfo(@RequestParam("id") Long id);


    /**
     * 根据学校Id查询学生组织架构信息
     *
     * @param schoolId
     * @return
     */
    @RequestMapping(value = "/findClassOrg", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SchoolOrg>> findClassOrg(@RequestParam("schoolId") Long schoolId);


    /**
     * 根据ID删除组织架构信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/removeSchoolOrgInfo", method = RequestMethod.POST)
    @ResponseBody
    Wrapper removeSchoolOrgInfo(@RequestParam("id") Long id);


    /**
     * 修改组织架构信息
     *
     * @param schoolOrgUpdateDto
     * @return
     */
    @RequestMapping(value = "/updateSchoolOrgInfo", method = RequestMethod.POST)
    @ResponseBody
    Wrapper updateSchoolOrgInfo(@RequestBody SchoolOrgUpdateDto schoolOrgUpdateDto);


    /**
     * 查询所有组织架构信息
     *
     * @return
     */
    @RequestMapping(value = "/findAllSchoolOrgInfo", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SchoolOrg>> findAllOrgInfo();
}