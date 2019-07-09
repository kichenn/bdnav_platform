package com.bdxh.weixiao.controller.appburied;

import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.feign.AccountControllerClient;
import com.bdxh.account.feign.UserDeviceControllerClient;
import com.bdxh.appburied.entity.AppStatus;
import com.bdxh.appburied.entity.InstallApps;
import com.bdxh.appburied.feign.AppStatusControllerClient;
import com.bdxh.appburied.feign.InstallAppsControllerClient;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.exception.PermitException;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import com.bdxh.weixiao.dto.WeiXiaoAppStatusUnlockOrLokingDto;
import com.bdxh.weixiao.vo.WeiXiaoInstallAppsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-21 09:50
 **/
@Slf4j
@RequestMapping(value = "/appStatusWeb")
@RestController
@Api(value = "应用管控----微校应用管控API", tags = "应用管控----微校应用管控API")
@Validated
public class AppStatusWebController {

    @Autowired
    private AppStatusControllerClient appStatusControllerClient;

    @Autowired
    private InstallAppsControllerClient installAppsControllerClient;


    @Autowired
    private UserDeviceControllerClient userDeviceControllerClient;

    /**
     * 收费服务
     * 家长应用管控----获取某个孩子的应用列表以及状态
     *
     * @param cardNumber 学生学号
     * @return
     */
    /*    @RolesAllowed({"TEST", "CONTROLE"})*/
    @ApiOperation(value = "家长应用管控----获取某个孩子的应用列表以及状态", response = WeiXiaoInstallAppsVo.class)
    @RequestMapping(value = "/queryAppStatusInfo222", method = RequestMethod.POST)
    public Object queryAppStatusInfo222(@RequestParam(name = "cardNumber") @NotNull(message = "学生CardNumber不能为空") String cardNumber) {
        try {
            //查看此孩子是否开通权限
            Map<String, List<String>> mapAuthorities = SecurityUtils.getCurrentAuthorized();
            //获取试用孩子列表信息
            List<String> caseCardNumber = mapAuthorities.get("ROLE_TEST");
            caseCardNumber=caseCardNumber==null ? new ArrayList<>() :caseCardNumber;
            Boolean isOnTrial = caseCardNumber.contains(cardNumber);
            //获取正式购买孩子列表信息
            List<String> thisCardNumbers = mapAuthorities.get("ROLE_CONTROLE");
            thisCardNumbers=thisCardNumbers==null ? new ArrayList<>() :thisCardNumbers;
            Boolean isBy = thisCardNumbers.contains(cardNumber);
            if (!(isBy || isOnTrial)) {
                throw new PermitException();
            }
              UserInfo userInfo = SecurityUtils.getCurrentUser();
            //根据学号查询出学生的应用安装记录
            List<InstallApps> installAppsList = installAppsControllerClient.findInstallAppsInConation(userInfo.getSchoolCode(), cardNumber).getResult();
            List<AppStatus> appStatusList = appStatusControllerClient.findAppStatusInfoBySchoolCodeAndCardNumber(userInfo.getSchoolCode(), cardNumber).getResult();
            List<WeiXiaoInstallAppsVo> weiXiaoInstallAppsVoList = BeanMapUtils.mapList(installAppsList, WeiXiaoInstallAppsVo.class);
            for (WeiXiaoInstallAppsVo weiXiaoInstallAppsVo : weiXiaoInstallAppsVoList) {
                for (AppStatus appStatus : appStatusList) {
                    //如果安装的包名对应的应用状态包名切状态为锁定时为Vo类添加锁定状态默认为1
                    if (weiXiaoInstallAppsVo.getAppPackage().equals(appStatus.getAppPackage()) && appStatus.getAppStatus().equals(Byte.valueOf("2"))) {
                        weiXiaoInstallAppsVo.setAppStatus(Byte.valueOf("2"));
                    }
                }
            }
            //排序,将锁定的应用置顶
            List<WeiXiaoInstallAppsVo> appsVos = weiXiaoInstallAppsVoList.stream().sorted(Comparator.comparing(WeiXiaoInstallAppsVo::getAppStatus).reversed()).collect(Collectors.toList());
            return WrapMapper.ok(appsVos);
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
     * 家长应用管控----锁定以及解锁App
     *
     * @param weiXiaoAppStatusUnlockOrLokingDto
     * @return
     */
    @RolesAllowed({"TEST", "CONTROLE"})
    @ApiOperation(value = "家长应用管控----锁定以及解锁App")
    @RequestMapping(value = "/appStatusLockingAndUnlock", method = RequestMethod.POST)
    public Object appStatusLockingAndUnlock(@RequestBody @Validated WeiXiaoAppStatusUnlockOrLokingDto weiXiaoAppStatusUnlockOrLokingDto) {
        try {
            //查看此孩子是否开通权限
            Map<String, List<String>> mapAuthorities = SecurityUtils.getCurrentAuthorized();
            //获取试用孩子列表信息
            List<String> caseCardNumber = mapAuthorities.get("ROLE_TEST");
            caseCardNumber = caseCardNumber == null ? new ArrayList<>() : caseCardNumber;
            Boolean isOnTrial = caseCardNumber.contains(weiXiaoAppStatusUnlockOrLokingDto.getCardNumber());
            //获取正式购买孩子列表信息
            List<String> thisCardNumbers = mapAuthorities.get("ROLE_CONTROLE");
            thisCardNumbers = thisCardNumbers == null ? new ArrayList<>() : thisCardNumbers;
            Boolean isBy = thisCardNumbers.contains(weiXiaoAppStatusUnlockOrLokingDto.getCardNumber());
            if (!(isBy || isOnTrial)) {
                throw new PermitException();
            }
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            log.debug("---------------------------------家长锁定解锁应用WEB层");
            weiXiaoAppStatusUnlockOrLokingDto.setSchoolCode(userInfo.getSchoolCode());
            List<String> clientId = new ArrayList<>();
            //获取clientId
            UserDevice userDevice = userDeviceControllerClient.findUserDeviceByCodeOrCard(weiXiaoAppStatusUnlockOrLokingDto.getSchoolCode(), weiXiaoAppStatusUnlockOrLokingDto.getCardNumber()).getResult();
            if (StringUtils.isEmpty(userDevice.getClientId())) {
                return WrapMapper.error("该子女暂未登录过手机账号");
            }
            clientId.add(userDevice.getClientId());
            weiXiaoAppStatusUnlockOrLokingDto.setClientId(clientId);
            return appStatusControllerClient.appStatusLockingAndUnlock(weiXiaoAppStatusUnlockOrLokingDto);
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