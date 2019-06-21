package com.bdxh.appburied.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.appburied.dto.*;
import com.bdxh.appburied.entity.AppStatus;
import com.bdxh.appburied.entity.ApplyLog;
import com.bdxh.common.helper.weixiao.authentication.AuthenticationUtils;
import com.bdxh.common.helper.weixiao.authentication.MessageUtils;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bdxh.appburied.service.ApplyLogService;
import sun.applet.Main;

import java.util.*;

/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@RestController
@RequestMapping("/applyLog")
@Slf4j
@Validated
@Api(value = "应用上报日志信息", tags = "应用上报日志信息交互API")
public class ApplyLogController {

    @Autowired
    private ApplyLogService applyLogService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @RequestMapping(value = "/addApplyLog", method = RequestMethod.POST)
    @ApiOperation(value = "增加APP上报日志信息", response = Boolean.class)
    public Object addApplyLog(@Validated @RequestBody AddApplyLogDto addApplyLogDto) {
        ApplyLog applyLog = new ApplyLog();
        BeanUtils.copyProperties(addApplyLogDto, applyLog);
        //设置状态值
        applyLog.setPlatform(addApplyLogDto.getInstallAppsPlatformEnum().getKey());
        applyLog.setModel(addApplyLogDto.getApplyLogModelEnum().getKey());
        applyLog.setOperatorStatus(addApplyLogDto.getApplyLogOperatorStatusEnum().getKey());
        //设置id
        applyLog.setId(snowflakeIdWorker.nextId());
        //设置微校查询通知参数
        HashMap<String, Object> map = new HashMap<>();
        map.put("app_key", addApplyLogDto.getAppKey());
        map.put("nonce", RandomStringUtils.randomAlphanumeric(32).toLowerCase().toUpperCase());
        map.put("school_code", addApplyLogDto.getSchoolCode());
        map.put("timestamp", System.currentTimeMillis() / 1000L);
        try {
            log.info("--------------------判断当前学校公众号有没有微信通知能力");
            String result = MessageUtils.ability(map, addApplyLogDto.getAppSecret());
            JSONObject jsonObject = JSONObject.parseObject(result);
            //如果该学校公众号有通知能力就进行微校通知
            if (jsonObject.get("code").equals(0)) {
                log.info("--------------------设置消息模板通知");
                //设置消息模板通知
                HashMap<String, Object> messageMap = new HashMap<>();
                messageMap.put("school_code", addApplyLogDto.getSchoolCode());
                JSONArray jsonArray = new JSONArray();
                jsonArray.add(addApplyLogDto.getOperatorCode());
                messageMap.put("cards", jsonArray.toString());
                messageMap.put("title", "畅玩申请");
                messageMap.put("content", addApplyLogDto.getReason());
                messageMap.put("sender", addApplyLogDto.getUserName());
                messageMap.put("app_key", addApplyLogDto.getAppKey());
                messageMap.put("timestamp", System.currentTimeMillis() / 1000L + "");
                messageMap.put("nonce", RandomStringUtils.randomAlphanumeric(32).toLowerCase().toUpperCase());
                //添加自定义参数，分别为提示文案和通知跳转链接，如不传则公众号模版消息会默认显示'你有一条通知待查看'，并跳转到微校通知详情页
                JSONArray messageJson = new JSONArray();
                messageJson.add("您的孩子给你发送了一条消息");
                messageJson.add("http://wx-front-prod.bdxht.com/bdnav-school-micro/dist/appControl/#/message?schoolCode=" + addApplyLogDto.getSchoolCode() + "&cardNumber=" + addApplyLogDto.getCardNumber());
                messageMap.put("customs", messageJson);
                String messageResult = MessageUtils.notice(messageMap, addApplyLogDto.getAppSecret());
                JSONObject jsonObject1=JSONObject.parseObject(messageResult);
                if(!jsonObject1.get("code").equals(0)){
                    log.info("--------------------设置消息模板通知失败 错误原因---------------------");
                }
                log.info("--------------------设置消息模板通知已经完成了---------------------");
            }
        } catch (Exception e) {
            return WrapMapper.error(e.getMessage());
        }
        Boolean result = applyLogService.save(applyLog) > 0;
        return WrapMapper.ok(result);
    }

    @RequestMapping(value = "/modifyApplyLog", method = RequestMethod.POST)
    @ApiOperation(value = "修改APP上报日志信息", response = Boolean.class)
    public Object modifyApplyLog(@Validated @RequestBody ModifyApplyLogDto modifyApplyLogDto) {
        ApplyLog applyLog = new ApplyLog();
        BeanUtils.copyProperties(modifyApplyLogDto, applyLog);
        //设置状态值
        if (modifyApplyLogDto.getInstallAppsPlatformEnum() != null) {
            applyLog.setPlatform(modifyApplyLogDto.getInstallAppsPlatformEnum().getKey());
        }
        if (modifyApplyLogDto.getApplyLogModelEnum() != null) {
            applyLog.setModel(modifyApplyLogDto.getApplyLogModelEnum().getKey());
        }
        if (modifyApplyLogDto.getApplyLogOperatorStatusEnum() != null) {
            applyLog.setOperatorStatus(modifyApplyLogDto.getApplyLogOperatorStatusEnum().getKey());
        }
        applyLog.setUpdateDate(new Date());
        return WrapMapper.ok(applyLogService.update(applyLog) > 0);
    }

    @RequestMapping(value = "/delApplyLogById", method = RequestMethod.POST)
    @ApiOperation(value = "删除APP上报日志信息", response = Boolean.class)
    public Object delApplyLogById(@Validated @RequestBody DelOrFindAppBuriedDto AddapplyLogDto) {
        ApplyLog applyLog = new ApplyLog();
        BeanUtils.copyProperties(AddapplyLogDto, applyLog);
        return WrapMapper.ok(applyLogService.delete(applyLog) > 0);
    }

    @RequestMapping(value = "/findApplyLogById", method = RequestMethod.POST)
    @ApiOperation(value = "根据id查询APP上报日志信息", response = AppStatus.class)
    public Object findApplyLogById(@Validated @RequestBody DelOrFindAppBuriedDto findApplyLogDto) {
        ApplyLog applyLog = new ApplyLog();
        BeanUtils.copyProperties(findApplyLogDto, applyLog);
        return WrapMapper.ok(applyLogService.select(applyLog));
    }

    @RequestMapping(value = "/findApplyLogInContionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "分页上报App状态日志查询", response = AppStatus.class)
    public Object findAppStatusInContionPaging(@Validated @RequestBody ApplyLogQueryDto applyLogQueryDto) {
        return WrapMapper.ok(applyLogService.findApplyLogInConationPaging(applyLogQueryDto));
    }

    @RequestMapping(value = "/familyFindApplyLogInfo", method = RequestMethod.GET)
    @ApiOperation(value = "家长查询自己孩子的App申请信息")
    public Object familyFindApplyLogInfo(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber) {
        try {
            return WrapMapper.ok(applyLogService.familyFindApplyLogInfo(schoolCode, cardNumber));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error();
        }
    }

    @RequestMapping(value = "/modifyVerifyApplyLog", method = RequestMethod.POST)
    @ApiOperation(value = "家长审批自己孩子的App申请信息")
    public Object modifyVerifyApplyLog(@RequestBody ModifyApplyLogDto modifyApplyLogDto) {
        try {
            applyLogService.modifyVerifyApplyLog(modifyApplyLogDto);
            return WrapMapper.ok("审批完成");
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error();
        }
    }


    @RequestMapping(value = "/checkMymessages", method = RequestMethod.GET)
    @ApiOperation(value = "查询当前用户的申请消息")
    public Object checkMymessages(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber) {
        try {
            return WrapMapper.ok(applyLogService.checkMymessages(schoolCode, cardNumber));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error();
        }
    }


}