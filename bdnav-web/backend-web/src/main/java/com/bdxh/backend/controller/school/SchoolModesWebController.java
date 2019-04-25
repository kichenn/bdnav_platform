package com.bdxh.backend.controller.school;


import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolModeDto;
import com.bdxh.school.dto.ModifySchoolModeDto;
import com.bdxh.school.dto.QuerySchoolMode;
import com.bdxh.school.feign.SchoolModeControllerClient;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schoolModesWebController")
@Slf4j
@Validated
@Api(value = "学校模式", tags = "学校模式交互API")
public class SchoolModesWebController {

    @Autowired
    private SchoolModeControllerClient schoolModeControllerClient;


    @RequestMapping(value = "/addModesInCondition", method = RequestMethod.POST)
    @ApiOperation(value = "增加学校模式", response = Boolean.class)
    public Object findSchoolsInConditionPaging(@Validated @RequestBody AddSchoolModeDto addSchoolModeDto) {
        try {
            Wrapper wrapper = schoolModeControllerClient.addSchoolModes(addSchoolModeDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }

    }



    @RequestMapping(value = "/modifySchoolModes", method = RequestMethod.POST)
    @ApiOperation(value = "修改学校模式", response = Boolean.class)
    public Object findSchoolsInConditionPaging(@Validated @RequestBody ModifySchoolModeDto modifySchoolModeDto) {
        try{
        Wrapper wrapper = schoolModeControllerClient.modifySchoolModes(modifySchoolModeDto);
        return wrapper;
       } catch (Exception e) {
        e.printStackTrace();
        return WrapMapper.error(e.getMessage());
      }
    }

    /**
     * @Description: 带条件分页查询列表信息
     * @Date 2019-04-18 09:52:43
     */
    @PostMapping("/findModesInConditionPage")
    @ApiOperation(value = "带条件分页查询列表信息", response = PageInfo.class)
    public Object findModesInConditionPage(@RequestBody QuerySchoolMode querySchoolMode) {
        Wrapper<PageInfo<QuerySchoolMode>> wrapper = schoolModeControllerClient.findModesInConditionPage(querySchoolMode);
        return WrapMapper.ok(wrapper.getResult());
    }
    /**
     * @Description: 删除信息
     * @Date 2019-04-18 09:52:43
     */
    @RequestMapping(value = "/delModesById", method = RequestMethod.GET)
    @ApiOperation(value = "删除模式信息", response = Boolean.class)
    public Object delModesById(@RequestParam("id")Long id) {
        return WrapMapper.ok(schoolModeControllerClient.delSchoolModesById(id));
    }


    @RequestMapping(value = "/getModesById", method = RequestMethod.GET)
    @ApiOperation(value = "查询学校模式详情")
    public Object getModesById(@RequestParam("schoolId") Long id) {
        Wrapper wrapper = schoolModeControllerClient.getModesById(id);
        return WrapMapper.ok(wrapper.getResult());
    }

    @RequestMapping(value = "/getModesAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询学校模式列表")
    public Object getModesAll() {
        Wrapper wrapper = schoolModeControllerClient.getModesAll();
        return WrapMapper.ok(wrapper.getResult());
    }

    @RequestMapping(value = "/getListByPlatform", method = RequestMethod.GET)
    @ApiOperation(value = "根据平台查询所有模式")
    public Object getListByPlatform(@RequestParam("Platform") String Platform) {
        Wrapper wrapper = schoolModeControllerClient.getListByPlatform(Platform);
        return WrapMapper.ok(wrapper.getResult());
    }
}
