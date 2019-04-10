package com.bdxh.user.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddTeacherDto;
import com.bdxh.user.fallback.TeacherControllerFallback;
import com.bdxh.user.fallback.TeacherDeptControllerFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

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

}