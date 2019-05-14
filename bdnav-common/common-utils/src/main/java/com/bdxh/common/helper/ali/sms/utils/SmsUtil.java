package com.bdxh.common.helper.ali.sms.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.bdxh.common.helper.ali.sms.constant.AliyunSmsConstants;
import com.bdxh.common.helper.ali.sms.enums.SmsTempletEnum;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 功能描述:
 *
 * @auther: Kang
 * @date: 2019/3/4 15:57
 */
@Slf4j
public class SmsUtil {

    /**
     * 发送短信工具类
     *
     * @param smsTempletEnum 模板枚举类 包含参数名称
     * @param phones         手机号 多个逗号分隔
     * @param params         参数值 多个逗号分隔
     * @return
     * @throws ClientException
     */
    public static boolean sendMsgHelper(SmsTempletEnum smsTempletEnum, String phones, String params) {
        boolean isSuccess = false;
        try {
            Preconditions.checkNotNull(smsTempletEnum, "短信模板不能为空");
            Preconditions.checkArgument(StringUtils.isNotEmpty(phones), "手机号不能为空");
            JSONObject jsonObject = new JSONObject();
            String smsParamName = smsTempletEnum.getSmsParamName();
            if (StringUtils.isNotEmpty(smsParamName)) {
                String[] smsParamNames = StringUtils.split(smsParamName, ",");
                Preconditions.checkArgument(StringUtils.isNotEmpty(params), "参数有误");
                String[] smsParams = StringUtils.split(params, ",");
                Preconditions.checkArgument(smsParamNames.length == smsParams.length, "参数个数不正确");
                for (int i = 0; i < smsParamNames.length; i++) {
                    jsonObject.put(smsParamNames[i], smsParams[i]);
                }
            }
            //添加短信参数
            SendSmsRequest request = addRequestParam(smsTempletEnum.getSignName(), smsTempletEnum.getTempletCode(), phones, jsonObject.toJSONString());
            // 发送短信 此处可能会抛出异常，注意catch
            resultResponse(request);
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("手机号：{}发送短信失败，参数：{}", phones, params);
        }
        return isSuccess;
    }

    /**
     * 功能描述: 添加短信入参
     *
     * @auther: Kang
     * @date: 2019/03/04 14:28
     */
    private static SendSmsRequest addRequestParam(String signName, String templateCode, String phones, String params) {
        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        // 必填:群发待发送手机号
        request.setPhoneNumbers(phones);
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(params);
        return request;
    }

    /**
     * @Description: 发送短信
     * @Author: Kang
     * @Date: 2019/3/4 12:09
     */
    private static SendSmsResponse resultResponse(SendSmsRequest request) throws ClientException {
        // 可自助调整超时时间
        //String ss = System.getProperty("sun.net.client.defaultConnectTimeout");
        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-shenzhen", AliyunSmsConstants.accessKeyId, AliyunSmsConstants.accessKeySecret);
        DefaultProfile.addEndpoint("cn-shenzhen", "cn-shenzhen", AliyunSmsConstants.product, AliyunSmsConstants.domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        // hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return sendSmsResponse;
    }

    public static void main(String[] args) {
        sendMsgHelper(SmsTempletEnum.VERIFICATION_CODE, "13168718111", "1111");
    }
}
