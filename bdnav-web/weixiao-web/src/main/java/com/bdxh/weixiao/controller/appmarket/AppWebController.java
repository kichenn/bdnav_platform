package com.bdxh.weixiao.controller.appmarket;


import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.feign.AccountControllerClient;
import com.bdxh.account.feign.UserDeviceControllerClient;
import com.bdxh.appburied.entity.InstallApps;
import com.bdxh.appburied.feign.InstallAppsControllerClient;
import com.bdxh.appmarket.entity.App;
import com.bdxh.appmarket.feign.AppControllerClient;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import com.bdxh.weixiao.vo.WeiXiaoAppVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-15 15:23
 **/
@Slf4j
@RequestMapping(value = "/appWeb")
@RestController
@Api(value = "应用市场----微校应用市场API", tags = "应用市场----微校应用市场API")
@Validated
public class AppWebController {

    @Autowired
    private AppControllerClient appControllerClient;

    @Autowired
    private InstallAppsControllerClient installAppsControllerClient;

    @Autowired
    private UserDeviceControllerClient userDeviceControllerClient;

    /**
     *
     * @param cardNumber 学生学号
     * @return
     */
    @ApiOperation(value = "家长应用市场----家长查询学校应用列表",response = WeiXiaoAppVo.class)
    @RequestMapping(value = "/familyFindAppInfo", method = RequestMethod.POST)
    public Object familyFindAppInfo(@RequestParam("cardNumber") String cardNumber) {
        try {
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            String schoolCode=userInfo.getSchoolCode();
            //查出当前所在学校的应用和平台开放应用
            List<App> appList = appControllerClient.familyFindAppInfo(schoolCode).getResult();
            //根据学号查询出学生的应用安装记录 已安装 ：2，未安装 ：1
            List<InstallApps> installAppsList = installAppsControllerClient.findInstallAppsInConation(schoolCode, cardNumber).getResult();
            List<WeiXiaoAppVo> weiXiaoAppVoList = BeanMapUtils.mapList(appList, WeiXiaoAppVo.class);
            for (WeiXiaoAppVo weiXiaoAppVo : weiXiaoAppVoList) {
                for (InstallApps installApps : installAppsList) {
                    //如果存在就修改状态1为未安装，2为已安装
                    if (weiXiaoAppVo.getAppPackage().equals(installApps.getAppPackage())) {
                        weiXiaoAppVo.setIsInstalled(Byte.valueOf("2"));
                    }
                }
            }
            return WrapMapper.ok(weiXiaoAppVoList);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     *家长应用市场----根据APPId查看应用详情
     * @param id 应用Id
     * @return
     */
    @ApiOperation(value = "家长应用市场----根据APPId查看应用详情",response = App.class)
    @RequestMapping(value = "/findAppDetailsById", method = RequestMethod.POST)
    public Object findAppDetailsById(@RequestParam(value = "id")
                                     @NotNull(message = "应用ID不能为空") Long id) {
        try {
            App app = appControllerClient.queryApp(id).getResult();
            return app;
        } catch (Exception e) {
            return WrapMapper.error();
        }
    }

    /**
     * 家长应用市场----家长给孩子安装应用推送
     * @param id
     * @param userName
     * @param cardNumber
     * @return
     */
    @ApiOperation(value = "家长应用市场----家长给孩子安装应用推送",response = Boolean.class)
    @RequestMapping(value = "/pushInstalledApp", method = RequestMethod.POST)
    public Object pushInstalledApp(@RequestParam("id") @NotNull(message = "应用ID不能为空") Long id,
                                   @RequestParam("userName") @NotNull(message = "学生姓名不能为空") String userName,
                                   @RequestParam("cardNumber") @NotNull(message = "学生卡号不能为空") String cardNumber) {
        try {
            //先给测试默认的clientId
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            UserDevice userDevice=userDeviceControllerClient.findUserDeviceByCodeOrCard(userInfo.getSchoolCode(),cardNumber).getResult();
            if(StringUtils.isEmpty(userDevice.getClientId())){
                return WrapMapper.error("该子女暂未登录过手机账号");
            }
           String  clientId=userDevice.getClientId();
            return appControllerClient.pushInstallApps(id, userName, cardNumber,clientId);
        } catch (Exception e) {
            return WrapMapper.error();
        }
    }
}