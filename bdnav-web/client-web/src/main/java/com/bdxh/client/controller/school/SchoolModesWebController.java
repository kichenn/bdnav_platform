package com.bdxh.client.controller.school;

import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolModeDto;
import com.bdxh.school.dto.ModifySchoolModeDto;
import com.bdxh.school.dto.QuerySchoolMode;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolModeControllerClient;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

/**
 * @Description: 控制器
 * @Date 2019-05-05 09:56:14
 */
@RestController
@RequestMapping("/clientSchoolModesWeb")
@Slf4j
@Validated
@Api(value = "学校管理--学校模式", tags = "学校管理--学校模式交互API")
public class SchoolModesWebController {


    @Autowired
    private SchoolModeControllerClient schoolModeControllerClient;

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/addModesInCondition", method = RequestMethod.POST)
    @ApiOperation(value = "增加学校模式", response = Boolean.class)
    public Object findSchoolsInConditionPaging(@Validated @RequestBody AddSchoolModeDto addSchoolModeDto) {
        try {
            //设置操作人
            SchoolUser user = SecurityUtils.getCurrentUser();
            addSchoolModeDto.setOperator(user.getId());
            addSchoolModeDto.setOperatorName(user.getUserName());
            addSchoolModeDto.setSchoolCode(user.getSchoolCode());
            addSchoolModeDto.setSchoolId(user.getSchoolId());
            Wrapper wrapper = schoolModeControllerClient.addSchoolModes(addSchoolModeDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }

    }


    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/modifySchoolModes", method = RequestMethod.POST)
    @ApiOperation(value = "修改学校模式", response = Boolean.class)
    public Object findSchoolsInConditionPaging(@Validated @RequestBody ModifySchoolModeDto modifySchoolModeDto) {
        try{
            //设置操作人
            SchoolUser user = SecurityUtils.getCurrentUser();
            modifySchoolModeDto.setOperator(user.getId());
            modifySchoolModeDto.setOperatorName(user.getUserName());
            modifySchoolModeDto.setSchoolCode(user.getSchoolCode());
            modifySchoolModeDto.setSchoolId(user.getSchoolId());
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
        //获取当前用户
        SchoolUser user = SecurityUtils.getCurrentUser();
        querySchoolMode.setSchoolId(user.getSchoolId());
        Wrapper<PageInfo<QuerySchoolMode>> wrapper = schoolModeControllerClient.findModesInConditionPage(querySchoolMode);
        return WrapMapper.ok(wrapper.getResult());
    }
    /**
     * @Description: 删除信息
     * @Date 2019-04-18 09:52:43
     */
    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/delModesById", method = RequestMethod.GET)
    @ApiOperation(value = "删除模式信息", response = Boolean.class)
    public Object delModesById(@RequestParam("id")Long id) {
        try {
            Wrapper wrapper=schoolModeControllerClient.delSchoolModesById(id);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }

    }


    @RequestMapping(value = "/getModesById", method = RequestMethod.GET)
    @ApiOperation(value = "查询学校模式详情")
    public Object getModesById(@RequestParam("id") Long id) {
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
    public Object getListByPlatform(@RequestParam("platform") String platform) {
        //设置操作人
        SchoolUser user = SecurityUtils.getCurrentUser();
        Wrapper wrapper = schoolModeControllerClient.getListByPlatform(user.getSchoolId(),platform);
        return WrapMapper.ok(wrapper.getResult());
    }

}
