package com.bdxh.pay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.WechatPayConstants;
import com.bdxh.common.base.constant.WxAuthorizedConstants;
import com.bdxh.common.utils.*;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.wechatpay.js.domain.JSNoticeReturn;
import com.bdxh.common.wechatpay.js.domain.JsOrderRequest;
import com.bdxh.common.wechatpay.js.domain.JsOrderResponse;
import com.bdxh.pay.dto.WxPayJsOrderDto;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
 * @description: 微信JS支付控制器
 * @author: xuyuan
 * @create: 2019-01-14 11:20
 **/
@Controller
@RequestMapping("/wechatJsPay")
@Slf4j
public class WechatJsPayController {

    /**
     * JS统一下单接口
     * @param wxPayJsOrderDto
     * @return
     * @throws Exception
     */
    @RequestMapping("/order")
    @ResponseBody
    public Object wechatJsPayOrder(@Valid WxPayJsOrderDto wxPayJsOrderDto, BindingResult bindingResult) throws Exception {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        JsOrderRequest jsOrderRequest = new JsOrderRequest();
        //公众账号id
        jsOrderRequest.setAppid(WechatPayConstants.JS.app_id);
        //商户号
        jsOrderRequest.setMch_id(WechatPayConstants.JS.mch_id);
        //随机32位字符串
        jsOrderRequest.setNonce_str(ObjectUtil.generateNonceStr());
        //商品描述
        jsOrderRequest.setBody(wxPayJsOrderDto.getProductDetail());
        //订单号
        jsOrderRequest.setOut_trade_no(wxPayJsOrderDto.getOrderNo());
        //金额
        jsOrderRequest.setTotal_fee(String.valueOf(wxPayJsOrderDto.getMoney().multiply(new BigDecimal(100)).longValue()));
        //终端ip
        jsOrderRequest.setSpbill_create_ip(wxPayJsOrderDto.getIp());
        //此路径是微信服务器调用支付结果通知路
        jsOrderRequest.setNotify_url(WechatPayConstants.JS.notice_url);
        //支付场景JSAPI
        jsOrderRequest.setTrade_type(WechatPayConstants.JS.trade_type);
        //微信唯一标识
        jsOrderRequest.setOpenid(wxPayJsOrderDto.getOpenid());
        //生成签名
        SortedMap<String, String> paramMap = BeanToMapUtil.objectToTreeMap(jsOrderRequest);
        if (paramMap.containsKey("sign")) {
            paramMap.remove("sign");
        }
        String paramStr = BeanToMapUtil.mapToString(paramMap);
        String sign = MD5.md5(paramStr + "&key=" + WechatPayConstants.JS.app_key);
        jsOrderRequest.setSign(sign);
        //发送微信下单请求
        String requestStr = XmlUtils.toXML(jsOrderRequest);
        //输出微信请求串
        log.info(requestStr);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Charset", "utf-8");
        headers.set("Content-type", "application/xml; charset=utf-8");
        HttpEntity<byte[]> httpEntity = new HttpEntity<>(requestStr.getBytes("utf-8"),headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(WechatPayConstants.JS.order_url, HttpMethod.POST, httpEntity, String.class);
        if (!(responseEntity.getStatusCode().value() == 200 && responseEntity.hasBody())) {
            return WrapMapper.error("微信下单接口调用失败");
        }
        String responseEntityStr =responseEntity.getBody();
        if (StringUtils.isNotEmpty(responseEntityStr)) {
            JsOrderResponse jsOrderResponse = XmlUtils.fromXML(responseEntityStr, JsOrderResponse.class);
            //下单成功
            if (StringUtils.equals("SUCCESS", jsOrderResponse.getReturn_code()) && StringUtils.equals("SUCCESS", jsOrderResponse.getResult_code())) {
                //验签
                SortedMap<String, String> responseMap = BeanToMapUtil.objectToTreeMap(jsOrderResponse);
                if (responseMap.containsKey("sign")) {
                    responseMap.remove("sign");
                }
                String responseStr = BeanToMapUtil.mapToString(responseMap);
                String responseSign = MD5.md5(responseStr + "&key=" + WechatPayConstants.JS.app_key);
                if (!StringUtils.equalsIgnoreCase(responseSign, jsOrderResponse.getSign())) {
                    return WrapMapper.error("微信返回数据验签失败");
                }
                //返回下单结果
                return WrapMapper.ok(jsOrderResponse.getPrepay_id());
            } else {
                return WrapMapper.error("微信下单接口返回失败");
            }
        }
        return WrapMapper.error("支付订单接口异常");
    }

    /**
     * 用户充值JS回调接口
     * @param request
     * @param response
     */
    @RequestMapping("/notice")
    public void wechatJsPayNotice(HttpServletRequest request, HttpServletResponse response) {
        try {
            int len = request.getContentLength();
            ServletInputStream inputStream = request.getInputStream();
            byte[] buffer = new byte[len];
            inputStream.read(buffer, 0, len);
            String appNoticeResponseStr=new String(buffer,"utf-8");
            Preconditions.checkArgument(StringUtils.isNotEmpty(appNoticeResponseStr),"回调内容为空");
            SortedMap<String, String> resultMap = WXPayUtil.xmlToMap(appNoticeResponseStr);
            //验签
            String resultSign=resultMap.get("sign");
            if (resultMap.containsKey("sign")) {
                resultMap.remove("sign");
            }
            String responseStr = BeanToMapUtil.mapToString((resultMap));
            String responseSign = MD5.md5(responseStr + "&key=" + WechatPayConstants.JS.app_key);
            Preconditions.checkArgument(StringUtils.equalsIgnoreCase(responseSign, resultSign),"微信返回数据验签失败");
            //做幂等性处理
            //发送至mq做异步处理
            //返回微信结果
            JSNoticeReturn jsNoticeReturn=new JSNoticeReturn();
            jsNoticeReturn.setReturn_code("SUCCESS");
            jsNoticeReturn.setReturn_msg("ok");
            String returnXml = XmlUtils.toXML(jsNoticeReturn);
            response.getOutputStream().write(returnXml.getBytes("utf-8"));
        }catch (Exception e){
            JSNoticeReturn jsNoticeReturn=new JSNoticeReturn();
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

    /**
     * 微信授权接口
     * @param code
     * @return
     */
    @RequestMapping("/auth")
    @ResponseBody
    public Object auth(@RequestParam("code") String code) {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(code),"code不能为空");
            String url = WxAuthorizedConstants.Letter.urlList+"?appid="+ WxAuthorizedConstants.Letter.appid+"&secret="+WxAuthorizedConstants.Letter.secret+"&code=" + code + "&grant_type="+WxAuthorizedConstants.Letter.grant_type;
            RestTemplate restTemplate = new RestTemplate();
            String auth = restTemplate.getForObject(url, String.class);
            Preconditions.checkArgument(StringUtils.isNotEmpty(auth),"拉取授权信息异常");
            JSONObject jsonObject = JSON.parseObject(auth);
            Preconditions.checkNotNull(jsonObject,"拉取授权信息异常");
            String openid = jsonObject.getString("openid");
            Preconditions.checkArgument(StringUtils.isNotEmpty(openid),"拉取授权信息异常");
            return WrapMapper.ok(openid);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
