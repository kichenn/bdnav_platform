package com.bdxh.weixiao.controller.appmarket;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.appburied.feign.InstallAppsControllerClient;
import com.bdxh.appmarket.entity.App;
import com.bdxh.appmarket.feign.AppControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.entity.Student;
import com.bdxh.user.feign.FamilyStudentControllerClient;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.vo.StudentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-15 15:23
 **/
@Slf4j
@RequestMapping(value = "/app")
@RestController
@Api(value = "微校平台----学校应用列表", tags = "微校平台----学校应用列表")
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
    public Object familyFindAppInfo(@RequestParam("schoolCode") String schoolCode,@RequestParam("cardNumber")String cardNumber) {
        try {
            StudentVo student=studentControllerClient.queryStudentInfo(schoolCode,cardNumber).getResult();
            /*installAppsControllerClient.findInstallAppsInContionPaging(schoolCode,cardNumber);*/
            JSONObject data=new JSONObject();
            data.put("studentName",student);
            //true为已安装，false为未安装
            data.put("isInstalled",true);
           List<App> list=appControllerClient.familyFindAppInfo(schoolCode).getResult();
            for (App app : list) {

            }
            return  null;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}