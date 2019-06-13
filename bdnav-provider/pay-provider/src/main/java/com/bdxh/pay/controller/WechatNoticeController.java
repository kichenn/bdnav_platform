package com.bdxh.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.RedisClusterConstrants;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.base.constant.WechatPayConstants;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.MD5;
import com.bdxh.common.utils.WXPayUtil;
import com.bdxh.common.utils.XmlUtils;
import com.bdxh.common.wechatpay.app.domain.AppNoticeReturn;
import com.bdxh.common.wechatpay.js.domain.JSNoticeReturn;
import com.bdxh.pay.configration.redis.RedisUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

/**
 * @description: 微信支付回调控制器
 * @author: xuyuan
 * @create: 2019-01-15 19:12
 **/
@Controller
@RequestMapping("/wechatNotice")
@Slf4j
public class WechatNoticeController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    /**
     * 微信APP支付回调接口
     *
     * @param request
     * @param response
     */
    @RequestMapping("/walletAppRecharge")
    public void wechatAppPayNotice(HttpServletRequest request, HttpServletResponse response) {
        try {
            int len = request.getContentLength();
            ServletInputStream inputStream = request.getInputStream();
            byte[] buffer = new byte[len];
            inputStream.read(buffer, 0, len);
            String appNoticeResponseStr = new String(buffer, "utf-8");
            Preconditions.checkArgument(StringUtils.isNotEmpty(appNoticeResponseStr), "回调内容为空");
            SortedMap<String, String> resultMap = WXPayUtil.xmlToMap(appNoticeResponseStr);
            //验签
            String resultSign = resultMap.get("sign");
            if (resultMap.containsKey("sign")) {
                resultMap.remove("sign");
            }
            String responseStr = BeanToMapUtil.mapToString((resultMap));
            String responseSign = MD5.md5(responseStr + "&key=" + WechatPayConstants.APP.app_key);
            Preconditions.checkArgument(StringUtils.equalsIgnoreCase(responseSign, resultSign), "微信返回数据验签失败");
            String orderNo = resultMap.get("out_trade_no");
            String resultCode = resultMap.get("result_code");
            String thirdOrderNo = resultMap.get("transaction_id");
            Preconditions.checkArgument(StringUtils.equalsIgnoreCase(resultCode, "SUCCESS") || StringUtils.equalsIgnoreCase(resultCode, "FAIL"), "微信返回结果不正确");
            //做幂等性处理,多次通知不再处理
            String notice = redisUtil.get(RedisClusterConstrants.KeyPrefix.wechatpay_wallet_app_notice + orderNo);
            if (!StringUtils.equals(notice, "1")) {
                //发送至mq做异步处理
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("orderNo", orderNo);
                jsonObject.put("resultCode", resultCode);
                jsonObject.put("thirdOrderNo", thirdOrderNo);
                Message message = new Message(RocketMqConstrants.Topic.wechatPayWalletNotice, RocketMqConstrants.Tags.wechatPayWalletNotice_app, jsonObject.toJSONString().getBytes(Charset.forName("utf-8")));
                defaultMQProducer.send(message);
            }
            //返回微信结果
            AppNoticeReturn appNoticeReturn = new AppNoticeReturn();
            appNoticeReturn.setReturn_code("SUCCESS");
            appNoticeReturn.setReturn_msg("ok");
            String returnXml = XmlUtils.toXML(appNoticeReturn);
            //60分钟
            redisUtil.setWithExpireTime(RedisClusterConstrants.KeyPrefix.wechatpay_wallet_app_notice + orderNo, "1", 60 * 60);
            response.getOutputStream().write(returnXml.getBytes("utf-8"));
        } catch (Exception e) {
            AppNoticeReturn appNoticeReturn = new AppNoticeReturn();
            appNoticeReturn.setReturn_code("FAIL");
            appNoticeReturn.setReturn_msg("no");
            String returnXml = XmlUtils.toXML(appNoticeReturn);
            try {
                response.getOutputStream().write(returnXml.getBytes("utf-8"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 用户支付JS回调接口
     *
     * @param request
     * @param response
     */
    @RequestMapping("/walletJsRecharge")
    public void wechatJsPayNotice(HttpServletRequest request, HttpServletResponse response) {
        try {
            int len = request.getContentLength();
            ServletInputStream inputStream = request.getInputStream();
            byte[] buffer = new byte[len];
            inputStream.read(buffer, 0, len);
            String appNoticeResponseStr = new String(buffer, "utf-8");
            Preconditions.checkArgument(StringUtils.isNotEmpty(appNoticeResponseStr), "回调内容为空");
            SortedMap<String, String> resultMap = WXPayUtil.xmlToMap(appNoticeResponseStr);
            //验签
            String resultSign = resultMap.get("sign");
            if (resultMap.containsKey("sign")) {
                resultMap.remove("sign");
            }
            String responseStr = BeanToMapUtil.mapToString((resultMap));
            String responseSign = MD5.md5(responseStr + "&key=" + WechatPayConstants.JS.APP_KEY);
            Preconditions.checkArgument(StringUtils.equalsIgnoreCase(responseSign, resultSign), "微信返回数据验签失败");
            String orderNo = resultMap.get("out_trade_no");
            String resultCode = resultMap.get("result_code");
            String thirdOrderNo = resultMap.get("transaction_id");
            Preconditions.checkArgument(StringUtils.equalsIgnoreCase(resultCode, "SUCCESS") || StringUtils.equalsIgnoreCase(resultCode, "FAIL"), "微信返回结果不正确");
            //做幂等性处理,多次通知不再处理
            String notice = redisUtil.get(RedisClusterConstrants.KeyPrefix.wechatpay_wallet_js_notice + orderNo);
            if (!StringUtils.equals(notice, "1")) {
                //发送至mq做异步处理
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("orderNo", orderNo);
                jsonObject.put("resultCode", resultCode);
                jsonObject.put("thirdOrderNo", thirdOrderNo);
                Message message = new Message(RocketMqConstrants.Topic.wechatPayWalletNotice, RocketMqConstrants.Tags.wechatPayWalletNotice_js, jsonObject.toJSONString().getBytes(Charset.forName("utf-8")));
                defaultMQProducer.send(message);
            }
            //返回微信结果
            JSNoticeReturn jsNoticeReturn = new JSNoticeReturn();
            jsNoticeReturn.setReturn_code("SUCCESS");
            jsNoticeReturn.setReturn_msg("ok");
            String returnXml = XmlUtils.toXML(jsNoticeReturn);
            //60分钟
            redisUtil.setWithExpireTime(RedisClusterConstrants.KeyPrefix.wechatpay_wallet_js_notice + orderNo, "1", 60 * 60);
            response.getOutputStream().write(returnXml.getBytes("utf-8"));
        } catch (Exception e) {
            JSNoticeReturn jsNoticeReturn = new JSNoticeReturn();
            jsNoticeReturn.setReturn_code("FAIL");
            jsNoticeReturn.setReturn_msg("no");
            String returnXml = XmlUtils.toXML(jsNoticeReturn);
            try {
                response.getOutputStream().write(returnXml.getBytes("utf-8"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

}
