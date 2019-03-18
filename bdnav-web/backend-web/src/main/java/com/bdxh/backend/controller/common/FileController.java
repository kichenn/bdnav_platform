package com.bdxh.backend.controller.common;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.vo.SchoolClassTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fileController")
@Validated
@Slf4j
@Api(value = "文件操作", tags = "文件操作交互API")
public class FileController {

    @RequestMapping(value = "/findSchoolClassTreeBySchoolId", method = RequestMethod.GET)
    @ApiOperation(value = "学校id递归查询院校结构关系", response = SchoolClassTreeVo.class)
    public Object findSchoolsInConditionPaging(@RequestParam("schoolId") Long schoolId) {
        //Wrapper wrapper = schoolClassControllerClient.findSchoolClassTreeBySchoolId(schoolId);
        return null;
    }
}
