package com.bdxh.user.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.service.TeacherDeptService;
import com.bdxh.user.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-10 16:19
 **/
@Api(value = "教师部门接口API", tags = "教师部门接口API")
@RestController
@RequestMapping("/teacherDept")
@Validated
@Slf4j
public class TeacherDeptController {
    @Autowired
    private TeacherDeptService teacherDeptService;

    /**
     * 删除教师部门绑定关系
     *
     * @param schoolCode
     * @param deptId
     * @return
     */
    @ApiOperation(value = "删除教师部门绑定关系")
    @RequestMapping(value = "/deleteTeacherDeptInfo", method = RequestMethod.GET)
    public Object deleteTeacherDeptInfo(@RequestParam(name = "schoolCode") String schoolCode,
                                        @RequestParam(name = "deptId") Integer deptId) {
        try {
            teacherDeptService.deleteTeacherDeptInfo(schoolCode, null, deptId);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 查询所有老师部门关系
     */
    @RequestMapping(value = "/findAllTeacherDeptInfo", method = RequestMethod.POST)
    @ApiOperation(value = "查询所有老师部门关系")
    public Object findAllTeacherDeptInfo(@RequestBody TeacherDept teacherDept) {
        try {
            return WrapMapper.ok(teacherDeptService.select(teacherDept));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}
