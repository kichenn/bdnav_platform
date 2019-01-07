package com.bdxh.web.wechatpay.controller;

import com.bdxh.common.base.constant.WechatPayConstants;
import com.bdxh.common.base.enums.WxPayStatusEnum;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.MD5;
import com.bdxh.common.utils.ObjectUtil;
import com.bdxh.common.utils.XmlUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.common.wechatpay.app.domain.AppNoticeResponse;
import com.bdxh.common.wechatpay.app.domain.AppNoticeReturn;
import com.bdxh.common.wechatpay.app.domain.AppOrderRequest;
import com.bdxh.common.wechatpay.app.domain.AppOrderResponse;
import com.bdxh.wallet.feign.WalletControllerClient;
import com.bdxh.web.wechatpay.dto.WxPayAppOrderDto;
import com.bdxh.web.wechatpay.vo.WxPayAppOrderVo;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.stream.Collectors;

/**
 * @description: 微信APP支付控制器
 * @author: xuyuan
 * @create: 2019-01-02 18:37
 **/
@Controller
@RequestMapping("/wechatAppPay")
@Slf4j
public class WechatAppPayController {

    @Autowired
    private WalletControllerClient walletControllerClient;

    /**
     * 用户充值app下单接口
     * @param wxPayAppOrderDto
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @RequestMapping("/order")
    @ResponseBody
    public Object wechatAppPayOrder(@Valid WxPayAppOrderDto wxPayAppOrderDto, BindingResult bindingResult) throws Exception {
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        //生成下单记录
        Wrapper wrapper = walletControllerClient.addRechargeLog(wxPayAppOrderDto.getUserId(), wxPayAppOrderDto.getMoney(), WechatPayConstants.APP.trade_type,WxPayStatusEnum.NO_PAY.getCode());
        if (wrapper==null||wrapper.getCode()!=200){
            return WrapMapper.error("生成订单失败");
        }
        //调用微信下单接口
        AppOrderRequest appOrderRequest=new AppOrderRequest();
        //应用id
        appOrderRequest.setAppid(WechatPayConstants.APP.app_id);
        //商户号
        appOrderRequest.setMch_id(WechatPayConstants.APP.mch_id);
        //随机字符串，使用uuid
        appOrderRequest.setNonce_str(ObjectUtil.getUuid());
        //商品描述
        appOrderRequest.setBody(wxPayAppOrderDto.getProductDetail());
        //订单号
        appOrderRequest.setOut_trade_no(String.valueOf(wrapper.getResult()));
        //总金额
        appOrderRequest.setTotal_fee(String.valueOf(wxPayAppOrderDto.getMoney().multiply(new BigDecimal(100)).longValue()));
        //终端ip
        appOrderRequest.setSpbill_create_ip(wxPayAppOrderDto.getIp());
        //通知地址
        appOrderRequest.setNotify_url(WechatPayConstants.APP.notice_url);
        //生成签名
        SortedMap<String, String> paramMap = BeanToMapUtil.objectToTreeMap(appOrderRequest);
        if (paramMap.containsKey("sign")){
            paramMap.remove("sign");
        }
        String paramStr = BeanToMapUtil.mapToString(paramMap);
        String sign = MD5.md5(paramStr + "&key=" + WechatPayConstants.APP.app_key);
        //签名
        appOrderRequest.setSign(sign);
        //发送微信下单请求
        String requestStr = XmlUtils.toXML(appOrderRequest);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Charset", "utf-8");
        headers.set("Content-type", "application/xml; charset=utf-8");
        HttpEntity<String> httpEntity=new HttpEntity<>(requestStr,headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(WechatPayConstants.APP.order_url, HttpMethod.POST, httpEntity, String.class);
        if (!(responseEntity.getStatusCode().value()==200&&responseEntity.hasBody())){
            return WrapMapper.error("微信下单接口调用失败");
        }
        String responseEntityStr = responseEntity.getBody();
        if (StringUtils.isNotEmpty(responseEntityStr)){
            AppOrderResponse appOrderResponse = XmlUtils.fromXML(responseEntityStr, AppOrderResponse.class);
            //下单成功
            if (StringUtils.equals("SUCCESS",appOrderResponse.getReturn_code())&&StringUtils.equals("SUCCESS",appOrderResponse.getResult_code())){
                //验签
                SortedMap<String, String> responseMap = BeanToMapUtil.objectToTreeMap(appOrderResponse);
                if (responseMap.containsKey("sign")){
                    responseMap.remove("sign");
                }
                String responseStr = BeanToMapUtil.mapToString(responseMap);
                String responseSign = MD5.md5(responseStr + "&key=" + WechatPayConstants.APP.app_key);
                if(!StringUtils.equalsIgnoreCase(responseSign,appOrderResponse.getSign())){
                    return WrapMapper.error("微信返回数据验签失败");
                }
                //返回下单结果
                WxPayAppOrderVo wxPayAppOrderVo=new WxPayAppOrderVo();
                wxPayAppOrderVo.setTradeType(appOrderResponse.getTrade_type());
                wxPayAppOrderVo.setPrepayId(appOrderResponse.getPrepay_id());
                return WrapMapper.ok(wxPayAppOrderVo);
            }else{
                return WrapMapper.error("微信下单接口返回失败");
            }
        }
        return WrapMapper.error("订单接口异常");
    }


    /**
     * 用户充值app回调接口
     * @param appNoticeResponse
     * @param response
     */
    @RequestMapping("/notice")
    public void wechatAppPayNotice(@RequestBody AppNoticeResponse appNoticeResponse, HttpServletResponse response) {
        try {
            Preconditions.checkNotNull(appNoticeResponse,"回调内容为空");
            Preconditions.checkArgument(StringUtils.equals("SUCCESS",appNoticeResponse.getReturn_code()),"回调状态不正确");
            //获取业务结果
            String resultCode=appNoticeResponse.getResult_code();
            Preconditions.checkArgument(StringUtils.equals("SUCCESS",resultCode)||StringUtils.equals("FAIL",resultCode),"业务状态不正确");
            //验证签名
            SortedMap<String, String> returnMap = BeanToMapUtil.objectToTreeMap(appNoticeResponse);
            if (returnMap.containsKey("sign")){
                returnMap.remove("sign");
            }
            String returnStr = BeanToMapUtil.mapToString(returnMap);
            String sign = MD5.md5(returnStr + "&key=" + WechatPayConstants.APP.app_key);
            Preconditions.checkArgument(StringUtils.equalsIgnoreCase(sign,appNoticeResponse.getSign()),"验签不通过");
            //处理业务结果
            Byte status=null;
            if (StringUtils.equals("SUCCESS",resultCode)){
               status= WxPayStatusEnum.PAY_SUCCESS.getCode();
            }
            if (StringUtils.equals("FAIL",resultCode)){
                status=WxPayStatusEnum.PAY_FAIL.getCode();
            }
            //更新业务表
            Long orderNo = Long.valueOf(appNoticeResponse.getOut_trade_no());
            Wrapper wrapper = walletControllerClient.changeRechargeLog(orderNo, status, appNoticeResponse.getTransaction_id());
            Preconditions.checkArgument(wrapper.getCode()==200,"更新支付结果失败");
            //返回微信结果
            AppNoticeReturn appNoticeReturn=new AppNoticeReturn();
            appNoticeReturn.setReturn_code("SUCCESS");
            appNoticeReturn.setReturn_msg("ok");
            String returnXml = XmlUtils.toXML(appNoticeReturn);
            response.getOutputStream().write(returnXml.getBytes("utf-8"));
        }catch (Exception e){
            AppNoticeReturn appNoticeReturn=new AppNoticeReturn();
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

}
