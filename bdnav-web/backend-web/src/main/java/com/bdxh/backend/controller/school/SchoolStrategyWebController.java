package com.bdxh.backend.controller.school;

import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.feign.UserDeviceControllerClient;
import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.helper.getui.constant.GeTuiConstant;
import com.bdxh.common.helper.getui.entity.AppTransmissionTemplate;
import com.bdxh.common.helper.getui.request.AppPushRequest;
import com.bdxh.common.helper.getui.utils.GeTuiUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.*;
import com.bdxh.school.feign.SchoolStrategyControllerClient;
import com.bdxh.system.entity.User;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schoolStrategyWebController")
@Slf4j
@Validated
@Api(value = "学校策略模式", tags = "学校策略模式交互API")
public class SchoolStrategyWebController {

    @Autowired
    private SchoolStrategyControllerClient schoolStrategyControllerClient;

    @Autowired
    private UserDeviceControllerClient userDeviceControllerClient;

    @RequestMapping(value = "/addPolicyInCondition", method = RequestMethod.POST)
    @ApiOperation(value = "增加学校模式策略", response = Boolean.class)
    public Object addPolicyInCondition(@Validated @RequestBody AddPolicyDto addPolicyDto) {
        try {
            //设置操作人
            User user = SecurityUtils.getCurrentUser();
            addPolicyDto.setOperator(user.getId());
            addPolicyDto.setOperatorName(user.getUserName());
            Wrapper wrapper = schoolStrategyControllerClient.addPolicyInCondition(addPolicyDto);
            System.out.println(wrapper.getResult());
            String aap=String.valueOf(wrapper.getResult());
            QuerySchoolStrategy ssl=schoolStrategyControllerClient.findStrategyById(Long.valueOf(aap)).getResult();
            if (ssl!=null){
                List<UserDevice> userDeviceList=userDeviceControllerClient.getUserDeviceAll(ssl.getSchoolCode(),ssl.getGroupId()).getResult();
                if (CollectionUtils.isNotEmpty(userDeviceList)){
                    AppPushRequest appPushRequest= new AppPushRequest();
                    appPushRequest.setAppId(GeTuiConstant.GeTuiParams.appId);
                    appPushRequest.setAppKey(GeTuiConstant.GeTuiParams.appKey);
                    appPushRequest.setMasterSecret(GeTuiConstant.GeTuiParams.MasterSecret);
                    List<String> clientIds = new ArrayList<>();
                    //添加用户设备号
                    for(UserDevice attribute : userDeviceList) {
                        clientIds.add(attribute.getClientId());
                    }
                    appPushRequest.setClientId(clientIds);
                    //穿透模版
                    AppTransmissionTemplate appTransmissionTemplate=new AppTransmissionTemplate();
                    appTransmissionTemplate.setTransmissionType(2);
                    appTransmissionTemplate.setTransmissionContent(ssl.toString());
                    appPushRequest.setAppTransmissionTemplate(appTransmissionTemplate);
                    //群发穿透模版
                    Map<String, Object> resultMap = GeTuiUtil.appCustomBatchPush(appPushRequest);
                    System.out.println(resultMap.toString());
                }
            }
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
                QuerySchoolStrategy ssl=schoolStrategyControllerClient.findStrategyById(modifyPolicyDto.getId()).getResult();
                List<UserDevice> userDeviceList=userDeviceControllerClient.getUserDeviceAll(ssl.getSchoolCode(),ssl.getGroupId()).getResult();
               if (CollectionUtils.isNotEmpty(userDeviceList)){
                    AppPushRequest appPushRequest= new AppPushRequest();
                    appPushRequest.setAppId(GeTuiConstant.GeTuiParams.appId);
                    appPushRequest.setAppKey(GeTuiConstant.GeTuiParams.appKey);
                    appPushRequest.setMasterSecret(GeTuiConstant.GeTuiParams.MasterSecret);
                    List<String> clientIds = new ArrayList<>();
                    //添加用户设备号
                    for(UserDevice attribute : userDeviceList) {
                        clientIds.add(attribute.getClientId());
                    }
                    appPushRequest.setClientId(clientIds);
                   //穿透模版
                   AppTransmissionTemplate appTransmissionTemplate=new AppTransmissionTemplate();
                   appTransmissionTemplate.setTransmissionType(2);
                   appTransmissionTemplate.setTransmissionContent(ssl.toString());
                   appPushRequest.setAppTransmissionTemplate(appTransmissionTemplate);
                    //群发穿透模版
                    Map<String, Object> resultMap = GeTuiUtil.appCustomBatchPush(appPushRequest);
                    System.out.println(resultMap.toString());
                }
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
    public Object getByPriority(@RequestParam("schoolCode") String schoolCode, @RequestParam("priority")Integer priority) {
        try {
            Wrapper wrapper=schoolStrategyControllerClient.getByPriority(schoolCode,priority);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }

    }

    /**
     * @Description: 查询列表信息
     * @Date 2019-04-18 09:52:43
     */
    @PostMapping("/findAllStrategy")
    @ApiOperation(value = "查询列表信息")
    public Object findAllStrategy() {
        Wrapper wrapper = schoolStrategyControllerClient.findAllStrategy();
        return WrapMapper.ok(wrapper.getResult());
    }

    /**
     * @Description: 根据schoolcode查询学校列表信息
     * @Date 2019-04-18 09:52:43
     */
   @RequestMapping(value="/getStrategyList",method = RequestMethod.GET)
    @ApiOperation(value = "根据schoolcode查询列表信息")
    public Object getStrategyList(@RequestParam("schoolCode") String schoolCode,@RequestParam("pushState")Byte pushState) {
       QuerySchoolStrategy schoolStrategy=new QuerySchoolStrategy();
       schoolStrategy.setSchoolCode(schoolCode);
       schoolStrategy.setPushState(pushState);
       Wrapper wrapper = schoolStrategyControllerClient.getStrategyList(schoolStrategy);
        return WrapMapper.ok(wrapper.getResult());
    }


}
