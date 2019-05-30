package com.bdxh.backend.controller.school;

import com.bdxh.school.dto.AddSchoolServicePermitDto;
import com.bdxh.school.dto.ModifySchoolServicePermitDto;
import com.bdxh.school.dto.SchoolServicePermitQueryDto;
import com.bdxh.school.entity.SchoolServicePermit;
import com.bdxh.school.feign.SchoolServicePermitControllerClient;
import com.bdxh.school.vo.SchoolServicePermitShowVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 学校服务的许可权限控制器
 * @author WanMing
 * @date 2019/5/28 19:57
 */
@RestController
@RequestMapping("/SchoolServicePermitWebController")
@Slf4j
@Validated
@Api(value = "学校服务的许可权限控制器", tags = "学校服务的许可权限控制器API")
public class SchoolServicePermitWebController {

    @Autowired
    private SchoolServicePermitControllerClient schoolServicePermitControllerClient;


    @RequestMapping(value = "addSchoolServicePermit",method = RequestMethod.POST)
    @ApiOperation(value = "增加学校服务的许可权限",response = Boolean.class)
    public Object addSchoolServicePermit(@Validated @RequestBody AddSchoolServicePermitDto addSchoolServicePermitDto){
        return schoolServicePermitControllerClient.addSchoolServicePermit(addSchoolServicePermitDto);
    }

    @RequestMapping(value = "modifySchoolServicePermit",method = RequestMethod.POST)
    @ApiOperation(value = "修改学校服务的许可权限",response = Boolean.class)
    public Object modifySchoolServicePermit(@Validated @RequestBody ModifySchoolServicePermitDto modifySchoolServicePermitDto){
        return schoolServicePermitControllerClient.modifySchoolServicePermit(modifySchoolServicePermitDto);
    }

    @RequestMapping(value = "findSchoolServicePermitInConditionPage",method = RequestMethod.POST)
    @ApiOperation(value = "分页查询学校服务许可权限信息",response = SchoolServicePermitShowVo.class)
    public Object findSchoolServicePermitInConditionPage(@Validated @RequestBody SchoolServicePermitQueryDto schoolServicePermitQueryDto){
        return schoolServicePermitControllerClient.findSchoolServicePermitInConditionPage(schoolServicePermitQueryDto);
    }


    @RequestMapping(value = "findSchoolServicePermitById",method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询学校服务许可权限信息",response = SchoolServicePermit.class)
    public Object findSchoolServicePermitById(@RequestParam(name = "id") Long id){
        return schoolServicePermitControllerClient.findSchoolServicePermitById(id);
    }


}
