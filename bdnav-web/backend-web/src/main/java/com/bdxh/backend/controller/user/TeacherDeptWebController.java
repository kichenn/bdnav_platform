package com.bdxh.backend.controller.user;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.feign.TeacherDeptControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-10 17:31
 **/
@RestController
@RequestMapping("/teacherDeptWeb")
@Validated
@Slf4j
@Api(value = "教师部门信息交互API", tags = "教师部门信息交互API")
public class TeacherDeptWebController {
    @Autowired
    private TeacherDeptControllerClient teacherDeptControllerClient;
    /**
     * 删除教师部门绑定关系
     * @param schoolCode
     * @param deptId
     * @return
     */
    @ApiOperation(value = "删除教师部门绑定关系")
    @RequestMapping(value = "/deleteTeacherDeptInfo",method = RequestMethod.GET)
    public Object deleteTeacherDeptInfo(@RequestParam(name = "schoolCode")String schoolCode,
                                        @RequestParam(name = "deptId") Integer deptId){
        try{
            teacherDeptControllerClient.deleteTeacherDeptInfo(schoolCode,deptId);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}