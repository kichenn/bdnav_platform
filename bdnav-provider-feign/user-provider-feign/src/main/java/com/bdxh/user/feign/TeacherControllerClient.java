package com.bdxh.user.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddTeacherDto;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.dto.UpdateTeacherDto;
import com.bdxh.user.fallback.TeacherControllerFallback;
import com.bdxh.user.vo.TeacherVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


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
     * @param addTeacherDto
     * @return
     */
    @RequestMapping(value = "/teacher/addTeacher",method = RequestMethod.POST)
    @ResponseBody
    Wrapper addTeacher(@RequestBody AddTeacherDto addTeacherDto);

    /**
     * 删除老师信息
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @RequestMapping(value = "/teacher/removeTeacher",method = RequestMethod.POST)
    @ResponseBody
    Wrapper removeTeacher(@RequestParam(name = "schoolCode")String schoolCode,
                          @RequestParam(name = "cardNumber")String cardNumber);

    /**
     * 批量删除老师信息
     * @param schoolCodes
     * @param cardNumbers
     * @return
     */
    @RequestMapping(value = "/teacher/removeTeachers",method = RequestMethod.POST)
    @ResponseBody
    Wrapper removeTeachers(@RequestParam(name = "schoolCodes")String schoolCodes,
                           @RequestParam(name = "cardNumbers")String cardNumbers);

    /**
     * 修改老师信息
     * @param updateTeacherDto
     * @return
     */
    @RequestMapping(value = "/teacher/updateTeacher",method = RequestMethod.POST)
    @ResponseBody
    Wrapper updateTeacher(@RequestBody UpdateTeacherDto updateTeacherDto);

    /**
     * 查询老师信息
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @RequestMapping(value ="/teacher/queryTeacherInfo",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<TeacherVo> queryTeacherInfo(@RequestParam(name = "schoolCode")String schoolCode,
                                        @RequestParam(name = "cardNumber")String cardNumber);

    /**
     * 分页查询老师信息
     * @param teacherQueryDto
     * @return
     */
    @RequestMapping(value = "/teacher/queryTeacherListPage",method = RequestMethod.POST)
    @ResponseBody
    Wrapper queryTeacherListPage(@RequestBody TeacherQueryDto teacherQueryDto);
}