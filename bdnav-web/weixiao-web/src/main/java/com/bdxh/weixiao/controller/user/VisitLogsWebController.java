package com.bdxh.weixiao.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.feign.UserDeviceControllerClient;
import com.bdxh.common.helper.getui.constant.GeTuiConstant;
import com.bdxh.common.helper.getui.entity.AppTransmissionTemplate;
import com.bdxh.common.helper.getui.request.AppPushRequest;
import com.bdxh.common.helper.getui.utils.GeTuiUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddFamilyBlackUrlDto;
import com.bdxh.user.entity.FamilyBlackUrl;
import com.bdxh.user.feign.FamilyBlackUrlControllerClient;
import com.bdxh.user.feign.VisitLogsControllerClient;
import com.bdxh.user.vo.VisitLogsVo;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.exception.PermitException;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-21 19:37
 **/
@Slf4j
@RequestMapping(value = "/visitLogWeb")
@RestController
@Api(value = "上网行为管理----上网行为管理控制器", tags = "上网行为管理----上网行为管理控制器")
@Validated
public class VisitLogsWebController {
    @Autowired
    private VisitLogsControllerClient visitLogsControllerClient;

    @Autowired
    private FamilyBlackUrlControllerClient familyBlackUrlControllerClient;

    @Autowired
    private UserDeviceControllerClient userDeviceControllerClient;

    /**
     * 收费服务
     *
     * @param cardNumber
     * @return
     */
    @RolesAllowed({"TEST", "NET"})
    @ApiOperation(value = "家长查询单个孩子浏览网站日志接口", response = VisitLogsVo.class)
    @RequestMapping(value = "/queryVisitLogByCardNumber", method = RequestMethod.POST)
    public Object queryVisitLogByCardNumber(@RequestParam(name = "cardNumber") @NotNull(message = "cardNumber不能为空") String cardNumber) {
        try {
            //查看此孩子是否开通权限
            Map<String, List<String>> mapAuthorities = SecurityUtils.getCurrentAuthorized();
            //获取试用孩子列表信息
            List<String> caseCardNumber = mapAuthorities.get("ROLE_TEST");
            caseCardNumber=caseCardNumber==null ? new ArrayList<>() :caseCardNumber;
            Boolean isOnTrial = caseCardNumber.contains(cardNumber);
            //获取正式购买孩子列表信息
            List<String> thisCardNumbers = mapAuthorities.get("ROLE_NET");
            thisCardNumbers=thisCardNumbers==null ? new ArrayList<>() :thisCardNumbers;
            Boolean isBy = thisCardNumbers.contains(cardNumber);
            if (!(isBy || isOnTrial)) {
                throw new PermitException();
            }
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            return visitLogsControllerClient.queryVisitLogByCardNumber(userInfo.getSchoolCode(), cardNumber);
        } catch (Exception e) {
            String messge = "";
            if (e instanceof PermitException) {
                messge = "抱歉，您该孩子没开通上网行为权限";
                return WrapMapper.notNoTrial(messge);
            }
            return WrapMapper.error(messge);
        }
    }

    @RolesAllowed({"TEST", "NET"})
    @RequestMapping(value = "/addBlacklist", method = RequestMethod.POST)
    @ApiOperation(value = "家长添加url黑名单", response = Boolean.class)
    public Object addBlacklist(@Validated @RequestBody AddFamilyBlackUrlDto addFamilyBlackUrlDto) {
        try {
            //查看此孩子是否开通权限
            Map<String, List<String>> mapAuthorities = SecurityUtils.getCurrentAuthorized();
            //获取试用孩子列表信息
            List<String> caseCardNumber = mapAuthorities.get("ROLE_TEST");
            caseCardNumber=caseCardNumber==null ? new ArrayList<>() :caseCardNumber;
            Boolean isOnTrial = caseCardNumber.contains(addFamilyBlackUrlDto.getStudentNumber());
            //获取正式购买孩子列表信息
            List<String> thisCardNumbers = mapAuthorities.get("ROLE_NET");
            thisCardNumbers=thisCardNumbers==null ? new ArrayList<>() :thisCardNumbers;
            Boolean isBy = thisCardNumbers.contains(addFamilyBlackUrlDto.getStudentNumber());
            if (!(isBy || isOnTrial)) {
                throw new PermitException();
            }
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            addFamilyBlackUrlDto.setSchoolCode(userInfo.getSchoolCode());
     /*       addFamilyBlackUrlDto.setSchoolCode("1011347968");
            addFamilyBlackUrlDto.setCardNumber("20190617005");*/
            Wrapper wrapMapper = familyBlackUrlControllerClient.addFamilyBlackUrl(addFamilyBlackUrlDto);
            String aap = String.valueOf(wrapMapper.getResult());

           FamilyBlackUrl bu = familyBlackUrlControllerClient.findBlackUrlById(userInfo.getSchoolCode(),userInfo.getFamilyCardNumber(),Long.valueOf(aap)).getResult();
          /*  FamilyBlackUrl bu = familyBlackUrlControllerClient.findBlackUrlById("1011347968","20190617005",Long.valueOf(aap)).getResult();*/
            if (bu != null) {
                UserDevice userDevices = userDeviceControllerClient.findUserDeviceByCodeOrCard(bu.getSchoolCode(), bu.getCardNumber()).getResult();
                if (userDevices != null) {
                    AppPushRequest appPushRequest = new AppPushRequest();
                    appPushRequest.setAppId(GeTuiConstant.GeTuiParams.appId);
                    appPushRequest.setAppKey(GeTuiConstant.GeTuiParams.appKey);
                    appPushRequest.setMasterSecret(GeTuiConstant.GeTuiParams.MasterSecret);
                    List<String> clientIds = new ArrayList<>();
                    clientIds.add(userDevices.getClientId());
                    appPushRequest.setClientId(clientIds);
                    //穿透模版
                    AppTransmissionTemplate appTransmissionTemplate = new AppTransmissionTemplate();
                    JSONObject obj = new JSONObject();
                    obj.put("key", "studentBlackUrlToPush");
                    obj.put("data", bu);
                    appTransmissionTemplate.setTransmissionContent(obj.toJSONString());
                    appPushRequest.setAppTransmissionTemplate(appTransmissionTemplate);
                    //群发穿透模版
                    Map<String, Object> resultMap = GeTuiUtil.appCustomPush(appPushRequest);
                    System.out.println(resultMap.toString());
                }
            }
            return wrapMapper;
        } catch (Exception e) {
            String messge = "";
            if (e instanceof PermitException) {
                messge = "抱歉，您该孩子没开通上网行为权限";
                return WrapMapper.notNoTrial(messge);
            }
            return WrapMapper.error(messge);
        }


    }


}