package com.bdxh.task.controller.school.job;

import com.bdxh.account.feign.AccountLogControllerClient;
import com.bdxh.common.helper.getui.constant.GeTuiConstant;
import com.bdxh.common.helper.getui.entity.AppNotificationTemplate;
import com.bdxh.common.helper.getui.request.AppPushRequest;
import com.bdxh.common.helper.getui.utils.GeTuiUtil;
import com.bdxh.school.feign.SchoolStrategyControllerClient;
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
    private AccountLogControllerClient accountLogControllerClient;

    @Autowired
    private SchoolStrategyControllerClient schoolStrategyControllerClient;

    /**
     * 执行逻辑。
     *
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String result = jobExecutionContext.getMergedJobDataMap().getString("site");
        //打印当前的执行时间
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("result:" + result + "现在的时间是：" + sf.format(date));
        //具体的业务逻辑
      /*  List<AccountLog> AccountMongoList=accountLogControllerClient.findAccountLogBySchoolCodeAndGroupId()*/
        AppPushRequest appPushRequest= new AppPushRequest();
        appPushRequest.setAppId(GeTuiConstant.GeTuiParams.appId);
        appPushRequest.setAppKey(GeTuiConstant.GeTuiParams.appKey);
        appPushRequest.setMasterSecret(GeTuiConstant.GeTuiParams.MasterSecret);
        List<String> clientIds = new ArrayList<>();
      /*  for(AccountLog attribute : AccountMongoList) {
            clientIds.add(attribute.getClientId());
            System.out.println(attribute.getGroupId());
        }*/
        appPushRequest.setClientId(clientIds);
        //穿透模版
        AppNotificationTemplate appNotificationTemplate = new AppNotificationTemplate();
        appNotificationTemplate.setTitle("学校策略模式推送");
        //查询该学校下的所有策略
       /* List<SchoolStrategy> list=schoolStrategyControllerClient.getStrategyList(schoolCode).getResult();*/
        appNotificationTemplate.getTransmissionContent();
        appPushRequest.setAppNotificationTemplate(appNotificationTemplate);
        //群发穿透模版
        Map<String, Object> resultMap = GeTuiUtil.appBatchPush(appPushRequest);
        //更改策略状态
        System.out.println("Hello Quartz");
    }
}
