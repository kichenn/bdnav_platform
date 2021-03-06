package com.bdxh.pay.controller;

import com.bdxh.common.base.constant.WechatPayConstants;
import com.bdxh.common.utils.*;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.wechatpay.common.domain.OrderQueryRequest;
import com.bdxh.pay.vo.WechatOrderQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotEmpty;
import java.util.SortedMap;

/**
 * @Description: 微信支付公共控制器
 * @Author: Kang
 * @Date: 2019/6/20 16:18
 */
@Controller
@RequestMapping("/wechatCommonPay")
@Slf4j
@Validated
public class WechatCommonController {

    /**
     * 微信JS支付订单查询接口
     *
     * @param orderNo
     */
    @RequestMapping("/query")
    @ResponseBody
    public Object wechatAppPayOrderQuery(@RequestParam(name = "orderNo") @NotEmpty(message = "订单号不能为空") String orderNo) throws Exception {
        //准备参数
        OrderQueryRequest orderQueryRequest = new OrderQueryRequest();
        orderQueryRequest.setAppid(WechatPayConstants.APP.app_id);
        orderQueryRequest.setMch_id(WechatPayConstants.APP.mch_id);
        orderQueryRequest.setNonce_str(ObjectUtil.getUuid());
        //我方订单
        orderQueryRequest.setOut_trade_no(orderNo);
        //微信方订单
//        orderQueryRequest.setTransaction_id(orderNo);
        //生成签名
        SortedMap<String, String> paramMap = BeanToMapUtil.objectToTreeMap(orderQueryRequest);
        if (paramMap.containsKey("sign")) {
            paramMap.remove("sign");
        }
        String paramStr = BeanToMapUtil.mapToString(paramMap);
        String sign = MD5.md5(paramStr + "&key=" + WechatPayConstants.JS.APP_KEY);
        orderQueryRequest.setSign(sign);
        //发送微信下单请求
        String requestStr = XmlUtils.toXML(orderQueryRequest);
        //输出微信请求串
        log.info("微信入参:{}", requestStr);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Charset", "utf-8");
        headers.set("Content-type", "application/xml; charset=utf-8");
        HttpEntity<byte[]> httpEntity = new HttpEntity<>(requestStr.getBytes("utf-8"), headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(WechatPayConstants.COMMON.order_query_url, HttpMethod.POST, httpEntity, String.class);
        if (!(responseEntity.getStatusCode().value() == 200 && responseEntity.hasBody())) {
            return WrapMapper.error("微信订单查询接口调用失败");
        }
        String responseEntityStr = responseEntity.getBody();
        //输出微信返回结果 requestStr.getBytes("utf-8"), headers
        log.info("微信返参:{}", new String(responseEntityStr.getBytes("iso-8859-1"), "utf-8"));
        if (StringUtils.isNotEmpty(responseEntityStr)) {
            SortedMap<String, String> resultMap = WXPayUtil.xmlToMap(responseEntityStr);
            if (StringUtils.equals("SUCCESS", resultMap.get("return_code")) && StringUtils.equals("SUCCESS", resultMap.get("result_code"))) {
                //返回下单结果
                WechatOrderQueryVo wechatOrderQueryVo = new WechatOrderQueryVo();
                wechatOrderQueryVo.setOrderNo(orderNo);
                wechatOrderQueryVo.setThirdOrderNo(resultMap.get("transaction_id"));
                wechatOrderQueryVo.setPayResult(resultMap.get("trade_state"));
                wechatOrderQueryVo.setTimeEnd(resultMap.get("time_end"));
                wechatOrderQueryVo.setSign(resultMap.get("sign"));
                wechatOrderQueryVo.setAppId(resultMap.get("appid"));
                wechatOrderQueryVo.setNonceStr(resultMap.get("nonce_str"));
                return WrapMapper.ok(wechatOrderQueryVo);
            } else {
                return WrapMapper.error("微信订单查询接口返回失败");
            }
        }
        return WrapMapper.error();
    }

}
