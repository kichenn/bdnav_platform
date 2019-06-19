package com.bdxh.common.helper.weixiao.authentication;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.helper.weixiao.authentication.AESEncryption.WxEncryption;
import com.bdxh.common.helper.weixiao.authentication.constant.AuthenticationConstant;
import com.bdxh.common.utils.HttpClientUtils;
import com.bdxh.common.utils.MD5;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @description:
 * @author: binzh
 * @create: 2019-06-18 17:14
 **/
@Slf4j
public class MessageUtils {

    /**
     * 查询通知能力
     * @param map
     * @return
     * @throws Exception
     */
    public static String ability(HashMap<String,Object>map,String appSecret)throws Exception{
        String sing= WxEncryption.getSign(map,appSecret);
        map.put("signature",sing);
        log.info("数据签名之后：{}",sing);
        String result = HttpClientUtils.doPost(AuthenticationConstant.NOTICE_ABILITY_URL,map);
        JSONObject json=JSONObject.parseObject(result);
       log.info("查询通知能力返回的消息code：{},message：{}",json.getString("code"),json.getString("message"));
        return result;
    }

    /**
     * 消息通知
     * @param map
     * @return
     * @throws Exception
     */
    public static String notice(HashMap<String,Object>map,String appSecret)throws Exception{
        String sing= WxEncryption.getSign(map,appSecret);
        map.put("signature",sing);
        log.info("数据签名之后：{}",sing);
        String result = HttpClientUtils.doPost(AuthenticationConstant.NOTICE_SEND_URL,map);
        JSONObject json=JSONObject.parseObject(result);
        log.info("消息通知返回的消息code：{},message：{}",json.getString("code"),json.getString("message"));
        return result;
    }
}