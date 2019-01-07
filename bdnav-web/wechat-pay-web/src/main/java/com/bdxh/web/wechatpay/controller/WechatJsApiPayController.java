package com.bdxh.web.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.WechatPayConstants;
import com.bdxh.common.base.constant.WxAuthorizedConstants;
import com.bdxh.common.base.enums.WxPayStatusEnum;
import com.bdxh.common.utils.*;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.common.wechatpay.js.JSentity.JSNoticeResponse;
import com.bdxh.common.wechatpay.js.JSentity.JSNoticeReturn;
import com.bdxh.common.wechatpay.js.JSentity.JsOrderRequest;
import com.bdxh.common.wechatpay.js.JSentity.JsOrderResponse;
import com.bdxh.wallet.feign.WalletControllerClient;
import com.bdxh.web.wechat.dto.WxPayJsOrderDto;
import com.bdxh.web.wechat.vo.WxPayJsOrderVo;
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
import java.io.IOException;
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
public class WechatJsApiPayController {

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
    public Object wechatJsPayOrder(WxPayJsOrderDto wxPayJsOrderDto, BindingResult bindingResult) throws Exception {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        //下单
        Wrapper wrapper = walletControllerClient.addRechargeLog(wxPayJsOrderDto.getUserId(), wxPayJsOrderDto.getMoney(), WechatPayConstants.JS.trade_type,WxPayStatusEnum.NO_PAY.getCode());
        if (wrapper == null || wrapper.getCode() != 200) {
            return WrapMapper.error("生成订单失败");
        }
        try {
            JsOrderRequest jsOrderRequest = new JsOrderRequest();
            //公众账号id
            jsOrderRequest.setAppid(WechatPayConstants.JS.app_id);
            //商品描述
            jsOrderRequest.setDetail(wxPayJsOrderDto.getProductDetail());
            //商户号
            jsOrderRequest.setMch_id(WechatPayConstants.JS.mch_id);
            //随机32位字符串
            jsOrderRequest.setNonce_str(ObjectUtil.generateNonceStr());
            // 此路径是微信服务器调用支付结果通知路
            jsOrderRequest.setNotify_url(WechatPayConstants.JS.notice_url);
            // 订单号
            jsOrderRequest.setOut_trade_no(WxPayUtil.getOrderNo());
            //终端ip
            jsOrderRequest.setSpbill_create_ip(wxPayJsOrderDto.getIp());
            //金额
            String sumfigure = WxPayUtil.getMoney(String.valueOf(wxPayJsOrderDto.getMoney()));
            jsOrderRequest.setFee_type(sumfigure);
            //支付场景
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
            System.out.println(jsOrderRequest.toString());//打印统一下单拼接参数是否齐全

            //发送微信下单请求
            String requestStr = XmlUtils.toXML(jsOrderRequest);
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept-Charset", "utf-8");
            headers.set("Content-type", "application/xml; charset=utf-8");
            HttpEntity<String> httpEntity = new HttpEntity<>(requestStr,headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(WechatPayConstants.JS.order_url, HttpMethod.POST, httpEntity, String.class);
            System.out.println(responseEntity.toString());//打印统一下单接口
            if (!(responseEntity.getStatusCode().value() == 200 && responseEntity.hasBody())) {
                return WrapMapper.error("微信下单接口调用失败");
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
                    String responseSign = MD5.md5(responseStr + "&key=" + WechatPayConstants.JS.app_key);
                    if (!StringUtils.equals(responseSign, jsOrderResponse.getSign())) {
                        return WrapMapper.error("微信返回数据验签失败");
                    }
                    //返回下单结果返给前台(获取JsApi支付所需的参数)
                    WxPayJsOrderVo wxPayJsOrderVo = new WxPayJsOrderVo();
                    wxPayJsOrderVo.setAppId(jsOrderResponse.getAppid());
                    String timeStamp = String.valueOf(new Date().getTime()).substring(0, 10);
                    wxPayJsOrderVo.setTimeStamp(timeStamp);
                    wxPayJsOrderVo.setNonceStr(jsOrderResponse.getNonce_str());
                    wxPayJsOrderVo.setPackages("prepay_id=" + jsOrderResponse.getPrepay_id());
                    String singlist = MD5.md5(wxPayJsOrderVo + "&key=" + WechatPayConstants.JS.app_key);
                    wxPayJsOrderVo.setSignType(singlist);
                    System.out.println(wxPayJsOrderVo.toString());//打印jsapi前台数据
                    return WrapMapper.ok(wxPayJsOrderVo);
                } else {
                    return WrapMapper.error("微信下单接口返回失败");
                }
            }
            return WrapMapper.error("支付订单接口异常");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return WrapMapper.error("支付订单接口异常");

    }


    //微信支付成功后回调方法
    @RequestMapping("/notice")
    public void wechatJsPayNotice(@RequestBody JSNoticeResponse jsNoticeResponse, HttpServletResponse response) {
        try {
            Preconditions.checkNotNull(jsNoticeResponse,"回调内容为空");
            Preconditions.checkArgument(StringUtils.equals("SUCCESS",jsNoticeResponse.getReturn_code()),"回调状态不正确");
            //获取业务结果
            String resultCode=jsNoticeResponse.getResult_code();
            Preconditions.checkArgument(StringUtils.equals("SUCCESS",resultCode)||StringUtils.equals("FAIL",resultCode),"业务状态不正确");
            //验证签名
            SortedMap<String, String> returnMap = BeanToMapUtil.objectToTreeMap(jsNoticeResponse);
            if (returnMap.containsKey("sign")){
                returnMap.remove("sign");
            }
            String returnStr = BeanToMapUtil.mapToString(returnMap);
            String sign = MD5.md5(returnStr + "&key=" + WechatPayConstants.APP.app_key);
            Preconditions.checkArgument(StringUtils.equals(sign,jsNoticeResponse.getSign()),"验签不通过");
            //处理业务结果
            Byte status=null;
            if (StringUtils.equals("SUCCESS",resultCode)){
                status= WxPayStatusEnum.PAY_SUCCESS.getCode();
            }
            if (StringUtils.equals("FAIL",resultCode)){
                status=WxPayStatusEnum.PAY_FAIL.getCode();
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


    //JSAPI支付拉取微信进行授权
    @RequestMapping("/auth")
    public Object auto(@RequestParam("code") String code) {

        if (StringUtils.isNotEmpty(code)) {

            String url = WxAuthorizedConstants.Letter.urlList+"?appid="+ WxAuthorizedConstants.Letter.appid+"&secret="+WxAuthorizedConstants.Letter.secret+"&code=" + code + "&grant_type="+WxAuthorizedConstants.Letter.grant_type;
            System.out.println(url.toString());//打印授权接口
            RestTemplate restTemplate = new RestTemplate();
            String getAuth = restTemplate.getForObject(url, String.class);
            System.out.println(getAuth.toString());//打印授权接口
            JSONObject jsonList = JSON.parseObject(getAuth);
            if (jsonList != null) {
                try {
                    String openid = jsonList.getString("openid");
                    if (StringUtils.isNotEmpty(openid)) {
                        return WrapMapper.ok(openid);
                        /* model.addAttribute("openid", openid);*/
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

        return WrapMapper.error("拉取授权信息异常");
    }





}
