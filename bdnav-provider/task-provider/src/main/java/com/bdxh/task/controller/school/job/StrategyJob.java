package com.bdxh.task.controller.school.job;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.feign.UserDeviceControllerClient;
import com.bdxh.common.helper.getui.constant.GeTuiConstant;
import com.bdxh.common.helper.getui.entity.AppNotificationTemplate;
import com.bdxh.common.helper.getui.request.AppPushRequest;
import com.bdxh.common.helper.getui.utils.GeTuiUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: Quartz学校策略定时任务执行类
 * @Author: Kang
 * @Date: 2019/5/16 10:21
 */
public class StrategyJob implements Job {

    @Autowired
    private UserDeviceControllerClient userDeviceControllerClient;

    /**
     * 执行逻辑。
     *
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String data = jobExecutionContext.getMergedJobDataMap().getString("data");
        JSONObject json=(JSONObject)JSONObject.parse(data);
        String schoolCode=json.getString("schoolCode");
        Long groupId=json.getLong("groupId");
        JSONArray jsonArray = JSON.parseArray(json.getString("strategyList"));
        System.out.println(json.getString("strategyList"));
        //打印当前的执行时间
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("result:" + data + "打印出现在的时间是：" + sf.format(date));
        //具体的业务逻辑
        List<UserDevice> userDeviceList=userDeviceControllerClient.getUserDeviceAll(schoolCode,groupId).getResult();
        AppPushRequest appPushRequest= new AppPushRequest();
        appPushRequest.setAppId(GeTuiConstant.GeTuiParams.appId);
        appPushRequest.setAppKey(GeTuiConstant.GeTuiParams.appKey);
        appPushRequest.setMasterSecret(GeTuiConstant.GeTuiParams.MasterSecret);
        List<String> clientIds = new ArrayList<>();
/*        clientIds.add("59dc219038fde0484eebcbb6d5476f0c");*/
        //添加用户设备号
       for(UserDevice attribute : userDeviceList) {
            clientIds.add(attribute.getClientId());
            System.out.println("推送数据"+attribute.getClientId());
        }
        appPushRequest.setClientId(clientIds);
        //穿透模版
        AppNotificationTemplate appNotificationTemplate = new AppNotificationTemplate();
        appNotificationTemplate.setTitle("学校策略模式推送");
        appNotificationTemplate.setText("穿透内容测试");
        appNotificationTemplate.setTransmissionContent(json.get("strategyList").toString());
        appPushRequest.setAppNotificationTemplate(appNotificationTemplate);
        //群发穿透模版
        Map<String, Object> resultMap = GeTuiUtil.appBatchPush(appPushRequest);
        //更改策略为已推送状态
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
          /*  schoolStrategyControllerClient.updatePolicyPushStatus(jsonObject.getLong("id"),new Byte("2"));*/
        }

        System.out.println(resultMap.toString());
        System.out.println("Hello Quartz");
    }
}
