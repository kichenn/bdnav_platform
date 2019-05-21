package com.bdxh.client.controller.school;

import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddPolicyDto;
import com.bdxh.school.dto.ModifyPolicyDto;
import com.bdxh.school.dto.QuerySchoolStrategy;
import com.bdxh.school.entity.School;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.feign.SchoolStrategyControllerClient;
import com.bdxh.school.vo.SchoolInfoVo;
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
@RequestMapping("/clientSchoolStrategyWeb")
@Slf4j
@Validated
@Api(value = "学校管理--学校策略", tags = "学校管理--学校策略交互API")
public class SchoolStrategyWebController {

    @Autowired
    private SchoolStrategyControllerClient schoolStrategyControllerClient;

    @Autowired
    private SchoolControllerClient schoolControllerClient;

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/addPolicyInCondition", method = RequestMethod.POST)
    @ApiOperation(value = "增加学校模式", response = Boolean.class)
    public Object addPolicyInCondition(@Validated @RequestBody AddPolicyDto addPolicyDto) {
        try {
            //设置操作人
            SchoolUser user = SecurityUtils.getCurrentUser();
            addPolicyDto.setOperator(user.getId());
            addPolicyDto.setOperatorName(user.getUserName());
            addPolicyDto.setSchoolCode(user.getSchoolCode());
            addPolicyDto.setSchoolId(user.getSchoolId());
            SchoolInfoVo school=schoolControllerClient.findSchoolById(user.getSchoolId()).getResult();
            addPolicyDto.setSchoolName(school.getSchoolName());
            Wrapper wrapper = schoolStrategyControllerClient.addPolicyInCondition(addPolicyDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }

    }


    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/updatePolicyInCondition", method = RequestMethod.POST)
    @ApiOperation(value = "修改学校模式", response = Boolean.class)
    public Object updatePolicyInCondition(@Validated @RequestBody ModifyPolicyDto modifyPolicyDto) {
        try{
            //设置操作人
            SchoolUser user = SecurityUtils.getCurrentUser();
            modifyPolicyDto.setOperator(user.getId());
            modifyPolicyDto.setOperatorName(user.getUserName());
            modifyPolicyDto.setSchoolCode(user.getSchoolCode());
            modifyPolicyDto.setSchoolId(user.getSchoolId());
            Wrapper wrapper = schoolStrategyControllerClient.updatePolicyInCondition(modifyPolicyDto);
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
    @PostMapping("/findPolicyInConditionPage")
    @ApiOperation(value = "带条件分页查询列表信息", response = PageInfo.class)
    public Object findPolicyInConditionPage(@RequestBody QuerySchoolStrategy querySchoolStrategy) {
        SchoolUser user = SecurityUtils.getCurrentUser();
        querySchoolStrategy.setSchoolId(user.getSchoolId());
        Wrapper<PageInfo<QuerySchoolStrategy>> wrapper = schoolStrategyControllerClient.findPolicyInConditionPage(querySchoolStrategy);
        return WrapMapper.ok(wrapper.getResult());
    }
    /**
     * @Description: 删除信息
     * @Date 2019-04-18 09:52:43
     */
    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/delSchoolStrategyById", method = RequestMethod.GET)
    @ApiOperation(value = "删除模式信息", response = Boolean.class)
    public Object delSchoolStrategyById(@RequestParam("id")Long id) {
        try {
            Wrapper wrapper=schoolStrategyControllerClient.delSchoolStrategyById(id);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }

    }


    /**
     * @Description: 验证学校策略优先级
     * @Date 2019-04-18 09:52:43
     */
    @RequestMapping(value = "/getBySchoolPriority", method = RequestMethod.GET)
    @ApiOperation(value = "验证学校策略优先级", response = Boolean.class)
    public Object getBySchoolPriority(@RequestParam("schoolCode") String schoolCode, @RequestParam("priority")Integer priority) {
        try {
            Wrapper wrapper=schoolStrategyControllerClient.getByPriority(schoolCode,priority);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }

    }
}
