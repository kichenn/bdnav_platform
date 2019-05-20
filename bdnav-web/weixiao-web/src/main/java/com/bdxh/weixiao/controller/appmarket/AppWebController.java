package com.bdxh.weixiao.controller.appmarket;


import com.bdxh.appburied.entity.InstallApps;
import com.bdxh.appburied.feign.InstallAppsControllerClient;
import com.bdxh.appmarket.entity.App;
import com.bdxh.appmarket.feign.AppControllerClient;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.feign.FamilyStudentControllerClient;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.weixiao.vo.WeiXiaoAppVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(value = "/app")
@RestController
@Api(value = "微校平台----应用市场", tags = "微校平台----应用市场")
@Validated
public class AppWebController {

    @Autowired
    private AppControllerClient appControllerClient;

    @Autowired
    private FamilyStudentControllerClient familyStudentControllerClient;

    @Autowired
    private StudentControllerClient studentControllerClient;

    @Autowired
    private InstallAppsControllerClient installAppsControllerClient;

    @ApiOperation("家长查询学校应用列表")
    @RequestMapping(value = "/familyFindAppInfo", method = RequestMethod.POST)
    public Object familyFindAppInfo(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber) {
        try {
            //查出当前所在学校的应用和平台开放应用
            List<App> appList = appControllerClient.familyFindAppInfo(schoolCode).getResult();
            //根据学号查询出学生的应用安装记录
            List<InstallApps> installAppsList = installAppsControllerClient.findInstallAppsInConation(schoolCode, cardNumber).getResult();
            List<WeiXiaoAppVo> weiXiaoAppVoList = BeanMapUtils.mapList(appList, WeiXiaoAppVo.class);
            for (WeiXiaoAppVo weiXiaoAppVo : weiXiaoAppVoList) {
                for (InstallApps installApps : installAppsList) {
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
    @ApiOperation("根据APPId查看应用详情")
    @RequestMapping(value = "/findAppDetailsById", method = RequestMethod.POST)
    public Object findAppDetailsById(@RequestParam(value = "id")
                                     @NotNull(message = "应用ID不能为空") Long id){
        try {
            App app=appControllerClient.queryApp(id).getResult();
            return WrapMapper.ok(app);
        }catch (Exception e){
            return WrapMapper.error();
        }
    }

    @ApiOperation("家长给孩子安装应用推送")
    @RequestMapping(value = "/pushInstalledApp",method = RequestMethod.POST)
    public Object pushInstalledApp(@RequestParam(value = "id")
                                   @NotNull(message = "应用ID不能为空") Long id){
        try {
            return WrapMapper.ok(appControllerClient.pushInstallApps(id).getResult());
        }catch (Exception e){
         return WrapMapper.error();
        }
    }
}