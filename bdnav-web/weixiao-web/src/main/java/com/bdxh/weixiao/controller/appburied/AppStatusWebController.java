package com.bdxh.weixiao.controller.appburied;

import com.bdxh.appburied.entity.AppStatus;
import com.bdxh.appburied.entity.InstallApps;
import com.bdxh.appburied.feign.AppStatusControllerClient;
import com.bdxh.appburied.feign.InstallAppsControllerClient;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.weixiao.dto.WeiXiaoAppStatusUnlockOrLokingDto;
import com.bdxh.weixiao.vo.WeiXiaoInstallAppsVo;
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

    /**
     * 收费服务
     * 家长应用管控----获取某个孩子的应用列表以及状态
     *
     * @param schoolCode 学校Code
     * @param cardNumber 学生学号
     * @return
     */

    @ApiOperation(value = "家长应用管控----获取某个孩子的应用列表以及状态")
    @RequestMapping(value = "/queryAppStatusInfo", method = RequestMethod.POST)
    public Object queryAppStatusInfo(@RequestParam(name = "schoolCode") @NotNull(message = "学校Code不能为空") String schoolCode,
                                     @RequestParam(name = "cardNumber") @NotNull(message = "学生CardNumber不能为空") String cardNumber) {
        try {
            //根据学号查询出学生的应用安装记录
            List<InstallApps> installAppsList = installAppsControllerClient.findInstallAppsInConation(schoolCode, cardNumber).getResult();
            List<AppStatus> appStatusList = appStatusControllerClient.findAppStatusInfoBySchoolCodeAndCardNumber(schoolCode, cardNumber).getResult();
            List<WeiXiaoInstallAppsVo> weiXiaoInstallAppsVoList = BeanMapUtils.mapList(installAppsList, WeiXiaoInstallAppsVo.class);
            for (WeiXiaoInstallAppsVo weiXiaoInstallAppsVo : weiXiaoInstallAppsVoList) {
                for (AppStatus appStatus : appStatusList) {
                    //如果安装的包名对应的应用状态包名切状态为锁定时为Vo类添加锁定状态默认为1
                    if (weiXiaoInstallAppsVo.getAppPackage().equals(appStatus.getAppPackage()) &&
                            appStatus.getAppStatus().equals(Byte.valueOf("2"))) {
                            weiXiaoInstallAppsVo.setAppStatus(Byte.valueOf("2"));
                    }
                }
            }
            return WrapMapper.ok(weiXiaoInstallAppsVoList);
        } catch (Exception e) {
            return WrapMapper.error();
        }
    }

    /**
     * 家长应用管控----锁定以及解锁App
     *
     * @param weiXiaoAppStatusUnlockOrLokingDto
     * @return
     */
    @ApiOperation(value = "家长应用管控----锁定以及解锁App")
    @RequestMapping(value = "/appStatusLockingAndUnlock", method = RequestMethod.POST)
    public Object appStatusLockingAndUnlock(@RequestBody @Validated WeiXiaoAppStatusUnlockOrLokingDto weiXiaoAppStatusUnlockOrLokingDto) {
        try {
            log.debug("---------------------------------家长锁定解锁应用WEB层");
            List<String> clientId = new ArrayList<>();
            //先给测试默认的clientId
            clientId.add("1b53a4daab144cec986d6ccf5a3fd745");
            weiXiaoAppStatusUnlockOrLokingDto.setClientId(clientId);
            return appStatusControllerClient.appStatusLockingAndUnlock(weiXiaoAppStatusUnlockOrLokingDto);
        } catch (Exception e) {
            return WrapMapper.error();
        }
    }
}