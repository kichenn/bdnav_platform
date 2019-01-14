package com.bdxh.web.wechatpay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.WechatPayConstants;
import com.bdxh.common.base.constant.WxAuthorizedConstants;
import com.bdxh.common.base.enums.WxPayCardStatusEnum;
import com.bdxh.common.utils.*;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.common.wechatpay.js.domain.JSNoticeResponse;
import com.bdxh.common.wechatpay.js.domain.JSNoticeReturn;
import com.bdxh.common.wechatpay.js.domain.JsOrderRequest;
import com.bdxh.common.wechatpay.js.domain.JsOrderResponse;
import com.bdxh.wallet.feign.WalletControllerClient;
import com.bdxh.web.wechatpay.dto.WxPayJsOkDto;
import com.bdxh.web.wechatpay.dto.WxPayJsOrderDto;
import com.bdxh.web.wechatpay.vo.WxPayJsOrderVo;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.SortedMap;
import java.util.stream.Collectors;

/**
 * @description: 微信JSAPI支付控制器
 * @author: LiTao
 * @create: 2019-01-02 18:37
 **/
@Controller
@RequestMapping("/wechatJsPay")
@Slf4j
public class WechatJsPayController {

    @Autowired
    private WalletControllerClient walletControllerClient;

    /**
     * JSAPI统一下单接口
     *
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
        //下单
        Wrapper wrapper = walletControllerClient.addRechargeLog(wxPayJsOrderDto.getSchoolCode(),wxPayJsOrderDto.getUserId(), wxPayJsOrderDto.getUserName(), wxPayJsOrderDto.getCardNumber(),
                wxPayJsOrderDto.getMoney(), WechatPayConstants.JS.trade_type, WxPayCardStatusEnum.NO_PAY.getCode());
        if (wrapper == null || wrapper.getCode() != 200) {
            return WrapMapper.error("生成订单失败");
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
        jsOrderRequest.setOut_trade_no(String.valueOf(wrapper.getResult()));
        //金额
        jsOrderRequest.setTotal_fee(String.valueOf(wxPayJsOrderDto .getMoney().multiply(new BigDecimal(100)).longValue()));
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
                //返回下单结果返给前台(获取JsApi支付所需的参数)
                WxPayJsOrderVo wxPayJsOrderVo = new WxPayJsOrderVo();
                wxPayJsOrderVo.setAppId(jsOrderResponse.getAppid());
                String timeStamp = String.valueOf(new Date().getTime()).substring(0, 10);
                wxPayJsOrderVo.setTimeStamp(timeStamp);
                wxPayJsOrderVo.setNonceStr(jsOrderResponse.getNonce_str());
                wxPayJsOrderVo.setPackages("prepay_id=" + jsOrderResponse.getPrepay_id());
                SortedMap<String, String> sortedMap = BeanToMapUtil.objectToTreeMap(wxPayJsOrderVo);
                String sing = MD5.md5(BeanToMapUtil.mapToString(sortedMap)+"&key="+WechatPayConstants.JS.app_key);
                wxPayJsOrderVo.setPaySign(sing);
                return WrapMapper.ok(wxPayJsOrderVo);
            } else {
                return WrapMapper.error("微信下单接口返回失败");
            }
        }
        return WrapMapper.error("支付订单接口异常");
    }

    /**
     * 用户支付完成请求接口
     * @param wxPayJsOkDto
     * @param bindingResult
     * @return
     */
    @RequestMapping("/ok")
    @ResponseBody
    public Object WechatJsPayOk(@Valid WxPayJsOkDto wxPayJsOkDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        //更新订单状态
        Byte status=wxPayJsOkDto.getStatus();
        Wrapper wrapper=null;
        if(status.intValue()==1){
            wrapper=walletControllerClient.updatePaying(wxPayJsOkDto.getOrderNo(), WxPayCardStatusEnum.PAYING.getCode());
        }else if (status.intValue()==2){
            wrapper=walletControllerClient.updatePaying(wxPayJsOkDto.getOrderNo(), WxPayCardStatusEnum.PAY_FAIL.getCode());
        }
        if (wrapper!=null&&wrapper.getCode()==200){
            return WrapMapper.ok("更新支付中状态成功");
        }
        return WrapMapper.error("更新支付中状态失败");
    }

    /**
     * 用户充值js回调接口
     * @param jsNoticeResponse
     * @param response
     */
    @RequestMapping("/notice")
    public void wechatJsPayNotice(@RequestBody JSNoticeResponse jsNoticeResponse, HttpServletResponse response) {
        try {
            Preconditions.checkNotNull(jsNoticeResponse,"回调内容为空");
            Preconditions.checkArgument(StringUtils.equals("SUCCESS",jsNoticeResponse.getReturn_code()),"回调状态为失败");
            //获取业务结果
            String resultCode=jsNoticeResponse.getResult_code();
            Preconditions.checkArgument(StringUtils.equals("SUCCESS",resultCode)||StringUtils.equals("FAIL",resultCode),"业务状态不正确");
             //验证签名
            SortedMap<String, String> returnMap = BeanToMapUtil.objectToTreeMap(jsNoticeResponse);
            if (returnMap.containsKey("sign")){
                returnMap.remove("sign");
            }
            String returnStr = BeanToMapUtil.mapToString(returnMap);
            String sign = MD5.md5(returnStr + "&key=" + WechatPayConstants.JS.app_key);
            Preconditions.checkArgument(StringUtils.equals(sign,jsNoticeResponse.getSign()),"验签不通过");
            //处理业务结果
            Byte status=null;
            if (StringUtils.equals("SUCCESS",resultCode)){
                status= WxPayCardStatusEnum.PAY_SUCCESS.getCode();
            }
            if (StringUtils.equals("FAIL",resultCode)){
                status= WxPayCardStatusEnum.PAY_FAIL.getCode();
            }
            //更新业务表
            Long orderNo = Long.valueOf(jsNoticeResponse.getOut_trade_no());
            Wrapper wrapper = walletControllerClient.changeRechargeLog(orderNo, status, jsNoticeResponse.getTransaction_id());
            Preconditions.checkArgument(wrapper.getCode()==200,"更新支付结果失败");
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
    public Object auto(@RequestParam("code") String code) {
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
