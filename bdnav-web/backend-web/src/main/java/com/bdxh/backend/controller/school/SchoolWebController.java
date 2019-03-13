package com.bdxh.backend.controller.school;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.ModifySchoolDto;
import com.bdxh.school.dto.SchoolDto;
import com.bdxh.school.dto.SchoolExcelDto;
import com.bdxh.school.dto.SchoolQueryDto;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.vo.SchoolInfoVo;
import com.bdxh.school.vo.SchoolShowVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolWebController")
@Validated
@Slf4j
@Api(value = "学校信息", tags = "学校信息交互API")
public class SchoolWebController {

    @Autowired
    private SchoolControllerClient schoolControllerClient;

    @RequestMapping(value = "/findSchoolsInConditionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "学校信息列表数据[分页筛选]", response = SchoolShowVo.class)
    public Object findSchoolsInConditionPaging(@Validated @RequestBody SchoolQueryDto schoolQueryDto) {
        Wrapper wrapper = schoolControllerClient.findSchoolsInConditionPaging(schoolQueryDto);
        return wrapper;
    }

    @RequestMapping(value = "/addSchool", method = RequestMethod.POST)
    @ApiOperation(value = "增加学校信息", response = Boolean.class)
    public Object findSchoolsInConditionPaging(@Validated @RequestBody SchoolDto schoolDto) {
        Wrapper wrapper = schoolControllerClient.addSchool(schoolDto);
        return wrapper;
    }


    @RequestMapping(value = "/modifySchoolInfo", method = RequestMethod.PATCH)
    @ApiOperation(value = "修改学校信息", response = Boolean.class)
    public Object modifySchoolInfo(@Validated @RequestBody ModifySchoolDto modifySchoolDto) {
        Wrapper wrapper = schoolControllerClient.modifySchoolInfo(modifySchoolDto);
        return WrapMapper.ok(wrapper.getResult());
    }


    @RequestMapping(value = "/delSchool", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除学校信息", response = Boolean.class)
    public Object delSchool(@RequestParam("id") Long id) {
        Wrapper wrapper = schoolControllerClient.delSchool(id);
        return wrapper;
    }

    @RequestMapping(value = "/batchDelSchool", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量删除学校信息", response = Boolean.class)
    public Object batchDelSchool(@RequestBody List<Long> ids) {
        Wrapper wrapper = schoolControllerClient.batchDelSchool(ids);
        return wrapper;
    }

    @RequestMapping(value = "/findSchoolById", method = RequestMethod.GET)
    @ApiOperation(value = "查询学校详情", response = SchoolInfoVo.class)
    public Object findSchoolById(@RequestParam("schoolId") Long id) {
        Wrapper wrapper = schoolControllerClient.findSchoolById(id);
        return wrapper;
    }


    @RequestMapping(value = "/downloadReportSchoolExcel", method = RequestMethod.POST)
    @ApiOperation(value = "导出学校列表信息", response = Boolean.class)
    public Object downloadReportSchoolExcel(@RequestBody SchoolExcelDto schoolExcelDto) {
        Wrapper wrapper = schoolControllerClient.downloadReportSchoolExcel(schoolExcelDto);
        return wrapper;
    }
}
