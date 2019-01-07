package com.bdxh.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.AliyunSmsConstants;
import com.google.common.base.Preconditions;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 阿里短信发送工具类
 * @author: xuyuan
 * @create: 2018-12-17 14:57
 **/
public class AliyunSmsUtil {

    public static void sendTaobao(String busType, Map<String, String> param) throws ApiException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(busType), "短信类型不能为空");
        AliyunSmsConstants.SmsTempletEnum smsTempletEnum = AliyunSmsConstants.SmsTempletEnum.getEnumByBusType(busType);
        Preconditions.checkNotNull(smsTempletEnum,"短信类型未配置");
        TaobaoClient client = new DefaultTaobaoClient(AliyunSmsConstants.taobaoSms.domain, AliyunSmsConstants.taobaoSms.accessKeyId,
                AliyunSmsConstants.taobaoSms.accessKeySecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        String extend = param.get("extend");
        if (StringUtils.isNotEmpty(extend)) {
            req.setExtend(extend);
        } else {
            req.setExtend("123456");
        }
        String smsType = param.get("smsType");
        if (StringUtils.isNotEmpty(smsType)) {
            req.setSmsType(smsType);
        } else {
            req.setSmsType("normal");
        }
        req.setRecNum(param.get("phone"));
        //组装参数
        JSONObject jsonObject=new JSONObject();
        String smsParamName = smsTempletEnum.getSmsParamName();
        if (StringUtils.isNotEmpty(smsParamName)){
            String[] smsParamNameArray = smsParamName.split(",");
            for(int i=0;i<smsParamNameArray.length;i++){
                jsonObject.put(smsParamNameArray[i],param.get(smsParamNameArray[i]));
            }
        }
        String smsParamString=jsonObject.toString();
        //设置短信内容
        req.setSmsParamString(smsParamString);
        //设置模板code
        req.setSmsTemplateCode(smsTempletEnum.getTempletCode());
        //设置签名
        req.setSmsFreeSignName(smsTempletEnum.getSignName());
        //发送短信
        client.execute(req);
    }

    public static void main1(String[] args) throws ApiException {
        Map<String,String> param=new HashMap<>();
        param.put("phone","17688937892");
        param.put("code","123456");
        AliyunSmsUtil.sendTaobao(AliyunSmsConstants.SmsTempletEnum.TEMPLATE_博学派.getBusType(),param);
    }

    public static void main(String[] args) throws ApiException {
        Map<String,String> param=new HashMap<>();
        param.put("phone","17688937892");
        param.put("code","123456");
        param.put("product","微校钱包");
        AliyunSmsUtil.sendTaobao(AliyunSmsConstants.SmsTempletEnum.TEMPLATE_身份验证.getBusType(),param);
    }

}
