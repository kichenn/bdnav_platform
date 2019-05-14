package com.bdxh.backend.controller.school;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.*;
import com.bdxh.school.feign.SchoolStrategyControllerClient;
import com.bdxh.system.entity.User;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schoolStrategyWebController")
@Slf4j
@Validated
@Api(value = "学校策略模式", tags = "学校策略模式交互API")
public class SchoolStrategyWebController {

    @Autowired
    private SchoolStrategyControllerClient schoolStrategyControllerClient;

    @RequestMapping(value = "/addPolicyInCondition", method = RequestMethod.POST)
    @ApiOperation(value = "增加学校模式", response = Boolean.class)
    public Object addPolicyInCondition(@Validated @RequestBody AddPolicyDto addPolicyDto) {
        try {
            //设置操作人
            User user = SecurityUtils.getCurrentUser();
            addPolicyDto.setOperator(user.getId());
            addPolicyDto.setOperatorName(user.getUserName());
            Wrapper wrapper = schoolStrategyControllerClient.addPolicyInCondition(addPolicyDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }

    }



    @RequestMapping(value = "/updatePolicyInCondition", method = RequestMethod.POST)
    @ApiOperation(value = "修改学校模式", response = Boolean.class)
    public Object updatePolicyInCondition(@Validated @RequestBody ModifyPolicyDto modifyPolicyDto) {
        try{
            //设置操作人
            User user = SecurityUtils.getCurrentUser();
            modifyPolicyDto.setOperator(user.getId());
            modifyPolicyDto.setOperatorName(user.getUserName());
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
        Wrapper<PageInfo<QuerySchoolStrategy>> wrapper = schoolStrategyControllerClient.findPolicyInConditionPage(querySchoolStrategy);
        return WrapMapper.ok(wrapper.getResult());
    }
    /**
     * @Description: 删除信息
     * @Date 2019-04-18 09:52:43
     */
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
    @RequestMapping(value = "/getByPriority", method = RequestMethod.GET)
    @ApiOperation(value = "验证学校策略优先级", response = Boolean.class)
    public Object getByPriority(@RequestParam("SchoolCode") String SchoolCode, @RequestParam("Priority")Integer Priority) {
        try {
            Wrapper wrapper=schoolStrategyControllerClient.getByPriority(SchoolCode,Priority);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }

    }


    /**
     * @RequestMapping(value = "/getByPriority", method = RequestMethod.GET)
     * 	@ApiOperation(value = "验证学校策略优先级", response = Boolean.class)
     * 	public Object getByPriority(@RequestParam("SchoolCode") String SchoolCode, @RequestParam("Priority")Integer Priority) {
     * 		try{
     * 			SchoolStrategy schoolStrategy=schoolStrategyService.getByPriority(SchoolCode,Priority);
     * 			if (schoolStrategy!=null){
     * 				return WrapMapper.ok("该学校策略已有该优先级值,请更换后重试");
     * 			}else{
     * 				return WrapMapper.ok();
     * 			}
     * 		} catch (Exception e) {
     * 			e.printStackTrace();
     * 			return WrapMapper.error(e.getMessage());
     * 		}
     *
     * 	}
     */

}
