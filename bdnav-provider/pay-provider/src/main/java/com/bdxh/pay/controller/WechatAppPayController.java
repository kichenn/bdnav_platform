package com.bdxh.pay.controller;

import com.bdxh.common.base.constant.WechatPayConstants;
import com.bdxh.common.utils.*;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.wechatpay.app.domain.AppNoticeReturn;
import com.bdxh.common.wechatpay.app.domain.AppOrderRequest;
import com.bdxh.common.wechatpay.app.domain.AppOrderResponse;
import com.bdxh.pay.dto.WxPayAppOrderDto;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.stream.Collectors;

/**
 * @description: 微信APP支付控制器
 * @author: xuyuan
 * @create: 2019-01-14 11:19
 **/
@Controller
@RequestMapping("/wechatAppPay")
@Slf4j
@Validated
public class WechatAppPayController {

    /**
     * 微信APP支付统一下单接口
     *
     * @param wxPayAppOrderDto
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @RequestMapping("/order")
    @ResponseBody
    public Object wechatAppPayOrder(@Valid @RequestBody WxPayAppOrderDto wxPayAppOrderDto, BindingResult bindingResult) throws Exception {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        //调用微信下单接口
        AppOrderRequest appOrderRequest = new AppOrderRequest();
        //应用id
        appOrderRequest.setAppid(WechatPayConstants.APP.app_id);
        //商户号
        appOrderRequest.setMch_id(WechatPayConstants.APP.mch_id);
        //随机字符串，使用uuid
        appOrderRequest.setNonce_str(ObjectUtil.getUuid());
        //商品描述
        appOrderRequest.setBody(wxPayAppOrderDto.getBody());
        //订单号
        appOrderRequest.setOut_trade_no(wxPayAppOrderDto.getOrderNo());
        //总金额
        appOrderRequest.setTotal_fee(String.valueOf(wxPayAppOrderDto.getMoney().multiply(new BigDecimal(100)).longValue()));
        //终端ip
        appOrderRequest.setSpbill_create_ip(wxPayAppOrderDto.getIp());
        //通知地址
        appOrderRequest.setNotify_url(WechatPayConstants.APP.notice_url);
        //生成签名
        SortedMap<String, String> paramMap = BeanToMapUtil.objectToTreeMap(appOrderRequest);
        if (paramMap.containsKey("sign")) {
            paramMap.remove("sign");
        }
        String paramStr = BeanToMapUtil.mapToString(paramMap);
        String sign = MD5.md5(paramStr + "&key=" + WechatPayConstants.APP.app_key);
        //签名
        appOrderRequest.setSign(sign);
        //发送微信下单请求
        String requestStr = XmlUtils.toXML(appOrderRequest);
        //输出微信请求串
        log.info(requestStr);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Charset", "utf-8");
        headers.set("Content-type", "application/xml; charset=utf-8");
        HttpEntity<byte[]> httpEntity = new HttpEntity<>(requestStr.getBytes("utf-8"), headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(WechatPayConstants.APP.order_url, HttpMethod.POST, httpEntity, String.class);
        if (!(responseEntity.getStatusCode().value() == 200 && responseEntity.hasBody())) {
            return WrapMapper.error("微信下单接口调用失败");
        }
        String responseEntityStr = responseEntity.getBody();
        //输出微信返回结果
        log.info(responseEntityStr);
        if (StringUtils.isNotEmpty(responseEntityStr)) {
            AppOrderResponse appOrderResponse = XmlUtils.fromXML(responseEntityStr, AppOrderResponse.class);
            //下单成功
            if (StringUtils.equals("SUCCESS", appOrderResponse.getReturn_code()) && StringUtils.equals("SUCCESS", appOrderResponse.getResult_code())) {
                //验签
                SortedMap<String, String> responseMap = BeanToMapUtil.objectToTreeMap(appOrderResponse);
                if (responseMap.containsKey("sign")) {
                    responseMap.remove("sign");
                }
                String responseStr = BeanToMapUtil.mapToString(responseMap);
                String responseSign = MD5.md5(responseStr + "&key=" + WechatPayConstants.APP.app_key);
                if (!StringUtils.equalsIgnoreCase(responseSign, appOrderResponse.getSign())) {
                    return WrapMapper.error("微信返回数据验签失败");
                }
                //返回下单结果
                return WrapMapper.ok(appOrderResponse.getPrepay_id());
            } else {
                return WrapMapper.error("微信下单接口返回失败");
            }
        }
        return WrapMapper.error("订单接口异常");
    }

}
