package com.bdxh.task.controller.strategy;

import com.bdxh.account.dto.AccountLogsMongo;
import com.bdxh.common.helper.getui.constant.GeTuiConstant;
import com.bdxh.common.helper.getui.entity.AppLinkTemplate;
import com.bdxh.common.helper.getui.entity.AppNotificationTemplate;
import com.bdxh.common.helper.getui.request.AppPushRequest;
import com.bdxh.common.helper.getui.utils.GeTuiUtil;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.school.dto.QuerySchoolStrategy;
import com.bdxh.school.entity.SchoolStrategy;
import com.bdxh.school.feign.SchoolStrategyControllerClient;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
@Slf4j
@Configurable
@EnableScheduling
public class StrategyToPush {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
     private SchoolStrategyControllerClient schoolStrategyControllerClient;


     public void pushStrategy(String schoolCode,Long groupId){
         Query query = new Query(Criteria.where("school_code").is(schoolCode)
                 .and("group_id").is(groupId));
         List<AccountLogsMongo> AccountMongoList=mongoTemplate.find(query, AccountLogsMongo.class);
         AppPushRequest appPushRequest= new AppPushRequest();
         appPushRequest.setAppId(GeTuiConstant.GeTuiParams.appId);
         appPushRequest.setAppKey(GeTuiConstant.GeTuiParams.appKey);
         appPushRequest.setMasterSecret(GeTuiConstant.GeTuiParams.MasterSecret);
         List<String> clientIds = new ArrayList<>();
         for(AccountLogsMongo attribute : AccountMongoList) {
             clientIds.add(attribute.getClientId());
             System.out.println(attribute.getGroupId());
         }
         appPushRequest.setClientId(clientIds);
         //穿透模版
         AppNotificationTemplate appNotificationTemplate = new AppNotificationTemplate();
         appNotificationTemplate.setTitle("学校策略模式推送");
         //查询该学校下的所有策略
         List<SchoolStrategy> list=schoolStrategyControllerClient.getStrategyList(schoolCode).getResult();
         appNotificationTemplate.getTransmissionContent();
         appPushRequest.setAppNotificationTemplate(appNotificationTemplate);
         //群发穿透模版
       Map<String, Object> resultMap = GeTuiUtil.appBatchPush(appPushRequest);
       }



}
