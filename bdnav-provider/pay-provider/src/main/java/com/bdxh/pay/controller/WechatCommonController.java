package com.bdxh.pay.controller;

import com.bdxh.common.base.constant.WechatPayConstants;
import com.bdxh.common.utils.*;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.wechatpay.common.domain.OrderQueryRequest;
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
import org.springframework.web.client.RestTemplate;
import javax.validation.constraints.NotEmpty;
import java.util.SortedMap;

/**
 * @description: 微信支付公共控制器
 * @author: xuyuan
 * @create: 2019-01-14 14:47
 **/
@Controller
@RequestMapping("/wechatCommonPay")
@Slf4j
@Validated
public class WechatCommonController {

    /**
     * 微信APP支付订单查询接口
     *
     * @param orderNo
     */
    @RequestMapping("/query")
    public Object wechatAppPayOrderQuery(@RequestParam(name = "orderNo") @NotEmpty(message = "订单号不能为空") String orderNo) throws Exception{
        //准备参数
        OrderQueryRequest orderQueryRequest = new OrderQueryRequest();
        orderQueryRequest.setAppid(WechatPayConstants.APP.app_id);
        orderQueryRequest.setMch_id(WechatPayConstants.APP.mch_id);
        orderQueryRequest.setNonce_str(ObjectUtil.getUuid());
        orderQueryRequest.setOut_trade_no(orderNo);
        //生成签名
        SortedMap<String, String> paramMap = BeanToMapUtil.objectToTreeMap(orderQueryRequest);
        if (paramMap.containsKey("sign")) {
            paramMap.remove("sign");
        }
        String paramStr = BeanToMapUtil.mapToString(paramMap);
        String sign = MD5.md5(paramStr + "&key=" + WechatPayConstants.APP.app_key);
        orderQueryRequest.setSign(sign);
        //发送微信下单请求
        String requestStr = XmlUtils.toXML(orderQueryRequest);
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
        if (StringUtils.isNotEmpty(responseEntityStr)) {
            SortedMap<String, String> resultMap = WXPayUtil.xmlToMap(responseEntityStr);
            //查询
            if (StringUtils.equals("SUCCESS", resultMap.get("return_code")) && StringUtils.equals("SUCCESS", resultMap.get("result_code"))) {
                //验签
                String resultSign=resultMap.get("sign");
                if (resultMap.containsKey("sign")) {
                    resultMap.remove("sign");
                }
                String responseStr = BeanToMapUtil.mapToString((resultMap));
                String responseSign = MD5.md5(responseStr + "&key=" + WechatPayConstants.APP.app_key);
                if (!StringUtils.equalsIgnoreCase(responseSign, resultSign)) {
                    return WrapMapper.error("微信返回数据验签失败");
                }
                //返回下单结果
                return WrapMapper.ok(resultMap.get("result_code"));
            } else {
                return WrapMapper.error("微信下单接口返回失败");
            }
        }
        return WrapMapper.error();
    }

}
