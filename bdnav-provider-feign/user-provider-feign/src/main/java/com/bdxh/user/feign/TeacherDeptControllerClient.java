package com.bdxh.user.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddTeacherDto;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.fallback.TeacherControllerFallback;
import com.bdxh.user.fallback.TeacherDeptControllerFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-10 17:23
 **/
@Service
@FeignClient(value = "user-provider-cluster", fallback = TeacherDeptControllerFallback.class)
public interface TeacherDeptControllerClient {
    /**
     * 新增老师信息
     * @param schoolCode cardNumber deptId
     * @return
     */
    @RequestMapping(value = "/teacherDept/deleteTeacherDeptInfo",method = RequestMethod.GET)
    @ResponseBody
    Wrapper deleteTeacherDeptInfo(@RequestParam(name = "schoolCode")String schoolCode,
                                  @RequestParam(name = "deptId")Integer deptId);
    /**
     * 查询所有老师部门关系
     */
    @RequestMapping(value = "/teacherDept/findAllTeacherDeptInfo",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<List<TeacherDept>> findAllTeacherDeptInfo(@RequestBody TeacherDept teacherDept);
}