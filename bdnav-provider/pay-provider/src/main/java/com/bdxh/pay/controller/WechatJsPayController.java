package com.bdxh.pay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.WechatPayConstants;
import com.bdxh.common.base.constant.WxAuthorizedConstants;
import com.bdxh.common.utils.*;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.common.wechatpay.js.domain.JSNoticeReturn;
import com.bdxh.common.wechatpay.js.domain.JsOrderPayResponse;
import com.bdxh.common.wechatpay.js.domain.JsOrderRequest;
import com.bdxh.common.wechatpay.js.domain.JsOrderResponse;
import com.bdxh.order.enums.OrderPayStatusEnum;
import com.bdxh.order.enums.OrderTradeStatusEnum;
import com.bdxh.pay.configration.exception.CreateOrderException;
import com.bdxh.pay.configration.exception.ResultOrderException;
import com.bdxh.pay.configration.exception.SignException;
import com.bdxh.pay.dto.WxPayJsOrderDto;
import com.bdxh.pay.vo.WechatOrderQueryVo;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Collectors;

/**
 * @Description: 微信JS支付控制器
 * @Author: Kang
 * @Date: 2019/5/31 17:29
 */
@RestController
@RequestMapping("/wechatJsPay")
@Api(value = "JSAPI支付", tags = "JSAPI支付交互API")
@Validated
@Slf4j
public class WechatJsPayController {

    @Autowired
    private WechatCommonController wechatCommonController;

    /**
     * JS统一下单接口
     *
     * @param wxPayJsOrderDto
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    @ApiOperation(value = "JS统一下单接口", response = String.class)
    public Object wechatJsPayOrder(@Validated @RequestBody WxPayJsOrderDto wxPayJsOrderDto) throws Exception {
        JsOrderRequest jsOrderRequest = new JsOrderRequest();
        //公众账号id
        jsOrderRequest.setAppid(WechatPayConstants.JS.APP_ID);
        //商户号
        jsOrderRequest.setMch_id(WechatPayConstants.JS.MCH_ID);
        //随机32位字符串
        jsOrderRequest.setNonce_str(ObjectUtil.generateNonceStr());
        //商品描述
        jsOrderRequest.setBody(wxPayJsOrderDto.getBody());
        //订单号
        jsOrderRequest.setOut_trade_no(wxPayJsOrderDto.getOrderNo());
        //金额
        jsOrderRequest.setTotal_fee(String.valueOf(wxPayJsOrderDto.getMoney().multiply(new BigDecimal(100)).longValue()));
        //终端ip
        jsOrderRequest.setSpbill_create_ip(wxPayJsOrderDto.getIp());
        //此路径是微信服务器调用支付结果通知路
        if (wxPayJsOrderDto.getPayType().equals(1)) {
            //家长购买
            jsOrderRequest.setNotify_url(WechatPayConstants.JS.NOTICE_URL);
        } else if (wxPayJsOrderDto.getPayType().equals(2)) {
            //钱包充值
            jsOrderRequest.setNotify_url(WechatPayConstants.JS.NOTICE_WALLET_URL);
        }
        //支付场景JSAPI
        jsOrderRequest.setTrade_type(WechatPayConstants.JS.THRADE_TYPE);
        //微信唯一标识
        jsOrderRequest.setOpenid(wxPayJsOrderDto.getOpenid());
        //生成签名
        SortedMap<String, String> paramMap = BeanToMapUtil.objectToTreeMap(jsOrderRequest);
        if (paramMap.containsKey("sign")) {
            paramMap.remove("sign");
        }
        String paramStr = BeanToMapUtil.mapToString(paramMap);
        String sign = MD5.md5(paramStr + "&key=" + WechatPayConstants.JS.APP_KEY);
        jsOrderRequest.setSign(sign);
        //发送微信下单请求
        String requestStr = XmlUtils.toXML(jsOrderRequest);
        //输出微信请求串
        log.info(requestStr);
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept-Charset", "utf-8");
            headers.set("Content-type", "application/xml; charset=utf-8");
            HttpEntity<byte[]> httpEntity = new HttpEntity<>(requestStr.getBytes("utf-8"), headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(WechatPayConstants.JS.ORDER_URL, HttpMethod.POST, httpEntity, String.class);
            if (!(responseEntity.getStatusCode().value() == 200 && responseEntity.hasBody())) {
                throw new CreateOrderException();
            }
            String responseEntityStr = responseEntity.getBody();
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
                    String responseSign = MD5.md5(responseStr + "&key=" + WechatPayConstants.JS.APP_KEY);
                    if (!StringUtils.equalsIgnoreCase(responseSign, jsOrderResponse.getSign())) {
                        throw new SignException();
                    }
                    log.info("返参:" + JSONObject.toJSONString(jsOrderResponse));
                    //返回下单结果
                    return WrapMapper.ok(JSONObject.toJSONString(jsOrderResponse));
                } else {
                    throw new ResultOrderException();
                }
            }
            return WrapMapper.error("支付订单接口异常");
        } catch (CreateOrderException e) {
            return WrapMapper.error("微信下单接口调用失败");
        } catch (SignException e) {
            return WrapMapper.error("微信返回数据验签失败");
        } catch (ResultOrderException e) {
            return WrapMapper.error("微信下单接口返回失败");
        }
    }

    /**
     * JS继续支付（开始未完成支付）
     *
     * @param orderNo 我方订单,prepayId 预订单
     * @return
     */
    @RequestMapping(value = "/continueOrder", method = RequestMethod.POST)
    @ApiOperation(value = "JS继续支付（开始未完成支付）", response = String.class)
    public Object continueOrder(@RequestParam("orderNo") String orderNo, @RequestParam("prepayId") String prepayId) {
        try {
            Wrapper result = (Wrapper) wechatCommonController.wechatAppPayOrderQuery(orderNo);
            WechatOrderQueryVo wechatOrderQueryVo = result.getCode() == Wrapper.SUCCESS_CODE ? (WechatOrderQueryVo) result.getResult() : null;
            Preconditions.checkArgument(wechatOrderQueryVo != null, "查询三方订单失败，请稍后再试");
            switch (wechatOrderQueryVo.getPayResult()) {
                case "SUCCESS":
                    //支付成功
                    return WrapMapper.error("支付已经成功，请稍后再试");
                case "NOTPAY":
                    //未支付
                    JsOrderPayResponse jsOrderPayResponse = new JsOrderPayResponse();
                    jsOrderPayResponse.setAppId(wechatOrderQueryVo.getAppId());
                    jsOrderPayResponse.setTimeStamp(System.currentTimeMillis() / 1000L + "");
                    jsOrderPayResponse.setNonceStr(wechatOrderQueryVo.getNonceStr());
                    jsOrderPayResponse.setPackages("prepay_id=" + prepayId);
                    SortedMap<String, String> paramMap = BeanToMapUtil.objectToTreeMap(jsOrderPayResponse);
                    if (paramMap.containsKey("packages")) {
                        paramMap.put("package", paramMap.get("packages"));
                        paramMap.remove("packages");
                    }
                    if (paramMap.containsKey("paySign")) {
                        paramMap.remove("paySign");
                    }
                    String paramStr = BeanToMapUtil.mapToString(paramMap);
                    String sign = MD5.md5(paramStr + "&key=" + WechatPayConstants.JS.APP_KEY);
                    jsOrderPayResponse.setPaySign(sign);
                    return WrapMapper.ok(jsOrderPayResponse);
                case "CLOSED":
                    //已关闭
                    return WrapMapper.error("订单已关闭，请检查");
                case "USERPAYING":
                    //支付中
                    return WrapMapper.error("正在支付中.....");
                case "PAYERROR":
                    //支付失败;
                    return WrapMapper.error("支付失败，请检查");
                case "REFUND":
                    //已转入退款
                    return WrapMapper.error("该订单已转入退款");
            }
        } catch (Exception e) {
            e.printStackTrace();
            WrapMapper.error(e.getMessage());
        }
        return WrapMapper.error();
    }

    /**
     * 微信授权接口
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    @ApiOperation(value = "根据微信code返回授权信息", response = String.class)
    public Object auth(@RequestParam("code") String code) {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(code), "code不能为空");
            String url = WxAuthorizedConstants.Letter.urlList + "?appid=" + WxAuthorizedConstants.Letter.appid + "&secret=" + WxAuthorizedConstants.Letter.secret + "&code=" + code + "&grant_type=" + WxAuthorizedConstants.Letter.grant_type;
            RestTemplate restTemplate = new RestTemplate();
            String auth = restTemplate.getForObject(url, String.class);
            Preconditions.checkArgument(StringUtils.isNotEmpty(auth), "拉取授权信息异常");
            JSONObject jsonObject = JSON.parseObject(auth);
            log.info("授权返回信息:" + jsonObject);
            Preconditions.checkNotNull(jsonObject, "拉取授权信息异常");
            String openid = jsonObject.getString("openid");
            Preconditions.checkArgument(StringUtils.isNotEmpty(openid), "拉取授权信息异常");
            return WrapMapper.ok(openid);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * @Description: redirectUri回调地址
     * @Author: Kang
     * @Date: 2019/6/17 19:12
     */
    @RequestMapping(value = "/getWechatUrl", method = RequestMethod.GET)
    @ApiOperation(value = "返回微信支付授权地址信息", response = String.class)
    public Object getWechatUrl(@RequestParam("redirectUri") String redirectUri) {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=@appid@&redirect_uri=@redirect_uri@&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
        try {
            url = url.replace("@appid@", WxAuthorizedConstants.Letter.appid)
                    .replace("@redirect_uri@", URLEncoder.encode(redirectUri, "utf-8"));
            log.info("微信支付授权信息地址:" + url);
            return WrapMapper.ok(url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
