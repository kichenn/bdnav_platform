package com.bdxh.client.controller.school;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.feign.UserDeviceControllerClient;
import com.bdxh.appmarket.entity.App;
import com.bdxh.appmarket.feign.AppControllerClient;
import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.helper.getui.constant.GeTuiConstant;
import com.bdxh.common.helper.getui.entity.AppTransmissionTemplate;
import com.bdxh.common.helper.getui.request.AppPushRequest;
import com.bdxh.common.helper.getui.utils.GeTuiUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddPolicyDto;
import com.bdxh.school.dto.ModifyPolicyDto;
import com.bdxh.school.dto.QuerySchoolStrategy;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.feign.SchoolStrategyControllerClient;
import com.bdxh.school.vo.MobileStrategyVo;
import com.bdxh.school.vo.SchoolInfoVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private UserDeviceControllerClient userDeviceControllerClient;

    @Autowired
    private AppControllerClient appControllerClient;

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
            String aap=String.valueOf(wrapper.getResult());
            QuerySchoolStrategy ssl=schoolStrategyControllerClient.findStrategyById(Long.valueOf(aap)).getResult();
            if (ssl!=null){
                List<String> userDeviceList=userDeviceControllerClient.getUserDeviceAll(ssl.getSchoolCode(),ssl.getRecursionPermissionIds()).getResult();
                if (CollectionUtils.isNotEmpty(userDeviceList)){
                    AppPushRequest appPushRequest= new AppPushRequest();
                    appPushRequest.setAppId(GeTuiConstant.GeTuiParams.appId);
                    appPushRequest.setAppKey(GeTuiConstant.GeTuiParams.appKey);
                    appPushRequest.setMasterSecret(GeTuiConstant.GeTuiParams.MasterSecret);
                    //添加用户设备号
                    appPushRequest.setClientId(userDeviceList);
                    //穿透模版
                    AppTransmissionTemplate appTransmissionTemplate=new AppTransmissionTemplate();
                    MobileStrategyVo msv=new MobileStrategyVo();
                    msv.setPolicyName(ssl.getPolicyName());
                    msv.setDayMark(ssl.getDayMark());
                    msv.setEndDate(ssl.getEndDate());
                    msv.setExclusionDays(ssl.getExclusionDays());
                    msv.setPriority(ssl.getPriority());
                    msv.setStartDate(ssl.getStartDate());
                    msv.setTimeMark(ssl.getTimeMark());
                    msv.setUsableDevice(ssl.getUsableDevice());
                    if (StringUtils.isNotEmpty(ssl.getUsableApp())&&StringUtils.isNotBlank(ssl.getUsableApp())){
                        List<App> apks=appControllerClient.getAppListByids(ssl.getUsableApp()).getResult();
                        List<String> apkPackages=new ArrayList<>();
                        for (int i = 0; i < apks.size(); i++) {
                            apkPackages.add(apks.get(i).getAppPackage());
                        }
                        msv.setAppPackage(apkPackages);
                    }
                    JSONObject obj=new JSONObject();
                    obj.put("key","strategyToPush");
                    obj.put("data",msv);
                    appTransmissionTemplate.setTransmissionContent(obj.toJSONString());
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
            QuerySchoolStrategy ssl=schoolStrategyControllerClient.findStrategyById(modifyPolicyDto.getId()).getResult();
            List<String> userDeviceList=userDeviceControllerClient.getUserDeviceAll(ssl.getSchoolCode(),ssl.getRecursionPermissionIds()).getResult();
            if (CollectionUtils.isNotEmpty(userDeviceList)){
                AppPushRequest appPushRequest= new AppPushRequest();
                appPushRequest.setAppId(GeTuiConstant.GeTuiParams.appId);
                appPushRequest.setAppKey(GeTuiConstant.GeTuiParams.appKey);
                appPushRequest.setMasterSecret(GeTuiConstant.GeTuiParams.MasterSecret);
                List<String> clientIds = new ArrayList<>();
                //添加用户设备号
                appPushRequest.setClientId(userDeviceList);
                //穿透模版
                AppTransmissionTemplate appTransmissionTemplate=new AppTransmissionTemplate();
                MobileStrategyVo msv=new MobileStrategyVo();
                msv.setPolicyName(ssl.getPolicyName());
                msv.setDayMark(ssl.getDayMark());
                msv.setEndDate(ssl.getEndDate());
                msv.setExclusionDays(ssl.getExclusionDays());
                msv.setPriority(ssl.getPriority());
                msv.setStartDate(ssl.getStartDate());
                msv.setTimeMark(ssl.getTimeMark());
                msv.setUsableDevice(ssl.getUsableDevice());
                if (StringUtils.isNotEmpty(ssl.getUsableApp())&&StringUtils.isNotBlank(ssl.getUsableApp())){
                    List<App> apks=appControllerClient.getAppListByids(ssl.getUsableApp()).getResult();
                    List<String> apkPackages=new ArrayList<>();
                    for (int i = 0; i < apks.size(); i++) {
                        apkPackages.add(apks.get(i).getAppPackage());
                    }
                    msv.setAppPackage(apkPackages);
                }
                JSONObject obj=new JSONObject();
                obj.put("key","strategyToPush");
                obj.put("data",msv);
                appTransmissionTemplate.setTransmissionContent(obj.toJSONString());
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
    @ApiOperation(value = "删除策略信息", response = Boolean.class)
    public Object delSchoolStrategyById(@RequestParam("id")Long id,@RequestParam("ids") String ids) {
        try {
            SchoolUser user = SecurityUtils.getCurrentUser();
            Wrapper wrapper=schoolStrategyControllerClient.delSchoolStrategyById(id);
            if (wrapper.getResult()==Boolean.TRUE) {
                List<String> userDeviceList = userDeviceControllerClient.getUserDeviceAll(user.getSchoolCode(), ids).getResult();
                if (CollectionUtils.isNotEmpty(userDeviceList)) {
                    AppPushRequest appPushRequest = new AppPushRequest();
                    appPushRequest.setAppId(GeTuiConstant.GeTuiParams.appId);
                    appPushRequest.setAppKey(GeTuiConstant.GeTuiParams.appKey);
                    appPushRequest.setMasterSecret(GeTuiConstant.GeTuiParams.MasterSecret);
                    //添加用户设备号
                    appPushRequest.setClientId(userDeviceList);
                    //穿透模版
                    AppTransmissionTemplate appTransmissionTemplate = new AppTransmissionTemplate();
                    JSONObject obj = new JSONObject();
                    obj.put("key", "strategyToPush");
                    obj.put("data", "成功删除该策略");
                    appTransmissionTemplate.setTransmissionContent(obj.toJSONString());
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
