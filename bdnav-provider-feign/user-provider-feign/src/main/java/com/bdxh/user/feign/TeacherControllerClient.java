package com.bdxh.user.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddTeacherDto;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.dto.UpdateTeacherDto;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.fallback.TeacherControllerFallback;
import com.bdxh.user.vo.TeacherVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @description:
 * @author: binzh
 * @create: 2019-03-13 10:18
 **/
@Service
@FeignClient(value = "user-provider-cluster", fallback = TeacherControllerFallback.class)
public interface TeacherControllerClient {

    /**
     * 新增老师信息
     *
     * @param addTeacherDto
     * @return
     */
    @RequestMapping(value = "/teacher/addTeacher", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addTeacher(@RequestBody AddTeacherDto addTeacherDto);

    /**
     * 删除老师信息
     *
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @RequestMapping(value = "/teacher/removeTeacher", method = RequestMethod.POST)
    @ResponseBody
    Wrapper removeTeacher(@RequestParam(name = "schoolCode") String schoolCode,
                          @RequestParam(name = "cardNumber") String cardNumber);

    /**
     * 批量删除老师信息
     *
     * @param schoolCodes
     * @param cardNumbers
     * @return
     */
    @RequestMapping(value = "/teacher/removeTeachers", method = RequestMethod.POST)
    @ResponseBody
    Wrapper removeTeachers(@RequestParam(name = "schoolCodes") String schoolCodes,
                           @RequestParam(name = "cardNumbers") String cardNumbers);

    /**
     * 修改老师信息
     *
     * @param updateTeacherDto
     * @return
     */
    @RequestMapping(value = "/teacher/updateTeacher", method = RequestMethod.POST)
    @ResponseBody
    Wrapper updateTeacher(@RequestBody UpdateTeacherDto updateTeacherDto);

    /**
     * 查询老师信息
     *
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @RequestMapping(value = "/teacher/queryTeacherInfo", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<TeacherVo> queryTeacherInfo(@RequestParam(name = "schoolCode") String schoolCode,
                                        @RequestParam(name = "cardNumber") String cardNumber);

    /**
     * 分页查询老师信息
     *
     * @param teacherQueryDto
     * @return
     */
    @RequestMapping(value = "/teacher/queryTeacherListPage", method = RequestMethod.POST)
    @ResponseBody
    Wrapper queryTeacherListPage(@RequestBody TeacherQueryDto teacherQueryDto);


    /**
     * @Description: 学校code，学校id，部门id查询老师信息
     * @Author: Kang
     * @Date: 2019/3/23 11:36
     */
    @RequestMapping(value = "/teacher/findTeacherBySchoolDeptId", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<TeacherDept> findTeacherBySchoolDeptId(@RequestParam("schoolCode") String schoolCode, @RequestParam("schoolId") Long schoolId, @RequestParam("deptId") Long deptId);

    /**
     * 批量新增老师信息
     *
     * @param teacherList
     * @return
     */
    @RequestMapping(value = "/teacher/batchSaveTeacherInfo", method = RequestMethod.POST)
    @ResponseBody
    Wrapper batchSaveTeacherInfo(@RequestBody List<AddTeacherDto> teacherList);

    /**
     * 根据学校Code查询所有老师卡号
     *
     * @param schoolCode
     * @return
     */
    @RequestMapping(value = "/teacher/queryTeacherCardNumberBySchoolCode", method = RequestMethod.POST)
    @ResponseBody
    Wrapper queryTeacherCardNumberBySchoolCode(@RequestParam("schoolCode") String schoolCode);

    /**
     * 查询出某个部门下面的老师
     *
     * @param schoolCode
     * @param parentIds
     * @return
     */
    @RequestMapping(value = "/teacher/findTeacherInfoByDeptOrg", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<List<Teacher>> findTeacherInfoByDeptOrg(@RequestParam("schoolCode") String schoolCode, @RequestParam("parentIds") String parentIds);

    /**
     * 查询所有老师信息
     * @return
     */
    @RequestMapping(value = "/teacher/findAllTeacher", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<Teacher>> findAllTeacher();
}

