package com.bdxh.weixiao.controller.appburied;

import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.feign.UserDeviceControllerClient;
import com.bdxh.appburied.dto.ApplyLogQueryDto;
import com.bdxh.appburied.dto.FamilyQueryApplyLogDto;
import com.bdxh.appburied.dto.ModifyApplyLogDto;
import com.bdxh.appburied.entity.ApplyLog;
import com.bdxh.appburied.feign.ApplyLogControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.exception.PermitException;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-25 10:14
 **/
@Slf4j
@RequestMapping(value = "/applyLogWeb")
@RestController
@Api(value = "审批畅玩----微校审批畅玩API", tags = "审批畅玩----微校审批畅玩API")
@Validated
public class ApplyLogWebController {
    @Autowired
    private ApplyLogControllerClient applyLogControllerClient;

    @Autowired
    private UserDeviceControllerClient userDeviceControllerClient;

    /**(该方法已被@method findApplyLogInfoByFamily替换)
     * 家长查询自己孩子的App申请信息
     *
     * @param cardNumber
     * @return
     */
    @RolesAllowed({"TEST", "CONTROLE"})
    @RequestMapping(value = "/familyFindApplyLogInfo", method = RequestMethod.GET)
    @ApiOperation(value = "审批畅玩----家长查询自己孩子的App申请信息", response = ApplyLog.class)
    public Object familyFindApplyLogInfo(@RequestParam("cardNumber") String cardNumber) {
        try {
            //没有在试用，查看是否开通正式权限
            Map<String, List<String>> mapAuthorities = SecurityUtils.getCurrentAuthorized();
            //获取试用孩子列表信息
            List<String> caseCardNumber = mapAuthorities.get("ROLE_TEST");
            caseCardNumber = caseCardNumber == null ? new ArrayList<>() : caseCardNumber;
            Boolean isOnTrial = caseCardNumber.contains(cardNumber);
            //获取正式购买孩子列表信息
            List<String> thisCardNumbers = mapAuthorities.get("ROLE_CONTROLE");
            thisCardNumbers = thisCardNumbers == null ? new ArrayList<>() : thisCardNumbers;
            Boolean isBy = thisCardNumbers.contains(cardNumber);
            if (!(isBy || isOnTrial)) {
                throw new PermitException();
            }
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            return applyLogControllerClient.familyFindApplyLogInfo(userInfo.getSchoolCode(), cardNumber);
        } catch (Exception e) {
            String messge = "";
            if (e instanceof PermitException) {
                messge = "抱歉，您该孩子没开通管控权限";
                return WrapMapper.notNoTrial(messge);
            }
            return WrapMapper.error(messge);
        }
    }

    /**
     * 家长查询自己孩子的App申请记录
     *
     * @param
     * @return
     */
    @RolesAllowed({"TEST", "CONTROLE"})
    @RequestMapping(value = "/findApplyLogInfoByFamily", method = RequestMethod.POST)
    @ApiOperation(value = "审批畅玩----家长查询该学校所有孩子的申请畅玩记录", response = ApplyLog.class)
    public Object findApplyLogInfoByFamily(@RequestBody FamilyQueryApplyLogDto familyQueryApplyLogDto) {
        try {
            //查看此孩子是否开通权限
            Map<String, List<String>> mapAuthorities = SecurityUtils.getCurrentAuthorized();
            //获取试用孩子列表信息
            List<String> caseCardNumber = mapAuthorities.get("ROLE_TEST");
            //获取正式购买孩子列表信息
            List<String> thisCardNumbers = mapAuthorities.get("ROLE_CONTROLE");
            if(null==caseCardNumber && null==thisCardNumbers){
                throw new PermitException();
            }
            //卡号合并
            List<String> allCardNumber = new ArrayList<>();
            allCardNumber.addAll(caseCardNumber==null?new ArrayList<>():caseCardNumber);
            allCardNumber.addAll(thisCardNumbers==null?new ArrayList<>():thisCardNumbers);
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            familyQueryApplyLogDto.setSchoolCode(userInfo.getSchoolCode());
            familyQueryApplyLogDto.setOperatorCode(userInfo.getFamilyCardNumber());
            familyQueryApplyLogDto.setStudentCardNumbers(allCardNumber);
            return applyLogControllerClient.findApplyLogInfoByFamily(familyQueryApplyLogDto);
        } catch (PermitException e) {
            String messge = "";
            if (e instanceof PermitException) {
                messge = "抱歉，您没有孩子开通管控权限";
                return WrapMapper.notNoTrial(messge);
            }
            return WrapMapper.error(messge);
        }

    }


    /**
     * 家长审批自己孩子的App申请信息
     *
     * @param modifyApplyLogDto
     * @return
     */
    @RolesAllowed({"TEST", "CONTROLE"})
    @RequestMapping(value = "/modifyVerifyApplyLog", method = RequestMethod.POST)
    @ApiOperation(value = "审批畅玩----家长审批自己孩子的App申请信息")
    public Object modifyVerifyApplyLog(@RequestBody ModifyApplyLogDto modifyApplyLogDto) {
        try {
            //查看此孩子是否开通权限
            Map<String, List<String>> mapAuthorities = SecurityUtils.getCurrentAuthorized();
            //获取试用孩子列表信息
            List<String> caseCardNumber = mapAuthorities.get("ROLE_TEST");
            caseCardNumber = caseCardNumber == null ? new ArrayList<>() : caseCardNumber;
            Boolean isOnTrial = caseCardNumber.contains(modifyApplyLogDto.getCardNumber());
            //获取正式购买孩子列表信息
            List<String> thisCardNumbers = mapAuthorities.get("ROLE_CONTROLE");
            thisCardNumbers = thisCardNumbers == null ? new ArrayList<>() : thisCardNumbers;
            Boolean isBy = thisCardNumbers.contains(modifyApplyLogDto.getCardNumber());
            if (!(isBy || isOnTrial)) {
                throw new PermitException();
            }
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            modifyApplyLogDto.setSchoolCode(userInfo.getSchoolCode());
            List<String> clientId = new ArrayList<>();
            //先给测试默认的clientId
            UserDevice userDevice = userDeviceControllerClient.findUserDeviceByCodeOrCard(modifyApplyLogDto.getSchoolCode(), modifyApplyLogDto.getCardNumber()).getResult();
            if (StringUtils.isEmpty(userDevice.getClientId())) {
                return WrapMapper.error("该子女暂未登录过手机账号");
            }
            clientId.add(userDevice.getClientId());
            modifyApplyLogDto.setClientId(clientId);
            return applyLogControllerClient.modifyVerifyApplyLog(modifyApplyLogDto);
        } catch (Exception e) {
            String messge = "";
            if (e instanceof PermitException) {
                messge = "抱歉，您该孩子没开通管控权限";
                return WrapMapper.notNoTrial(messge);
            }
            return WrapMapper.error(messge);
        }
    }

    @RolesAllowed({"TEST", "CONTROLE"})
    @RequestMapping(value = "/modifyVerifyApplyLogRead", method = RequestMethod.POST)
    @ApiOperation(value = "审批畅玩----家长查看消息时修改消息状态")
    public Object modifyVerifyApplyLogRead(@RequestBody ModifyApplyLogDto modifyApplyLogDto) {
        try {
            //查看此孩子是否开通权限
            Map<String, List<String>> mapAuthorities = SecurityUtils.getCurrentAuthorized();
            //获取试用孩子列表信息
            List<String> caseCardNumber = mapAuthorities.get("ROLE_TEST");
            caseCardNumber = caseCardNumber == null ? new ArrayList<>() : caseCardNumber;
            Boolean isOnTrial = caseCardNumber.contains(modifyApplyLogDto.getCardNumber());
            //获取正式购买孩子列表信息
            List<String> thisCardNumbers = mapAuthorities.get("ROLE_CONTROLE");
            thisCardNumbers = thisCardNumbers == null ? new ArrayList<>() : thisCardNumbers;
            Boolean isBy = thisCardNumbers.contains(modifyApplyLogDto.getCardNumber());
            if (!(isBy || isOnTrial)) {
                throw new PermitException();
            }
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            modifyApplyLogDto.setSchoolCode(userInfo.getSchoolCode());
            modifyApplyLogDto.setIsRead(Byte.valueOf("1"));
            return applyLogControllerClient.modifyApplyLog(modifyApplyLogDto);
        } catch (Exception e) {
            String messge = "";
            if (e instanceof PermitException) {
                messge = "抱歉，您该孩子没开通管控权限";
                return WrapMapper.notNoTrial(messge);
            }
            return WrapMapper.error(messge);
        }
    }
}