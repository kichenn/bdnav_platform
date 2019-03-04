package com.bdxh.common.helper.ali.sms.utils;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.bdxh.common.helper.ali.sms.constant.AliyunSmsConstants;
import com.bdxh.common.helper.ali.sms.enums.SmsTempletEnum;
import com.bdxh.common.helper.ali.sms.response.SmsResponseParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 功能描述:
 *
 * @auther: Kang
 * @date: 2018/12/3 11:57
 */
@Service
@Slf4j
public class SmsUtil {

//    @Autowired
//    private RedisUtil redisUtil;

    private long MSG_TIME;


    /**
     * @Description: 单发验证码
     * @Author: Kang
     * @Date: 2019/3/4 12:14
     */
    public SmsResponseParams sendMobileCaptcha(SmsTempletEnum smsTempletEnum, String phone) throws ClientException {

//        String key = smsTempletEnum.getPrefixCache() + phone;
        //如果redis还存在,则说明一分钟内发送过一次,不能重复发送
//        if (redisUtil.get(key) != null) {
//            throw new ClientException("短信已经发送，请勿重复点击。");
//        }
        Map<String, String> dataMap = new HashMap<String, String>();

        //生成验证码
        String captcha = getRandomNum(6);
        dataMap.put(smsTempletEnum.getSmsParamName(), captcha);

        //组装手机号
        List<String> phones = new ArrayList<>();
        phones.add(phone);

        SmsResponseParams result = sendMsgHelper(smsTempletEnum, phones, dataMap);
//        if (result) {
//            redisUtil.set(key, captcha, 60);
//        }
        return result;
    }


    /**
     * @Description: 群发验证码
     * @Author: Kang
     * @Date: 2019/3/4 14:04
     */
    public SmsResponseParams sendMobileCaptcha(SmsTempletEnum smsTempletEnum, List<String> phones) throws ClientException {
        //如果redis还存在,则说明一分钟内发送过一次,不能重复发送
//        if (redisUtil.get(key) != null) {
//            throw new ClientException("短信已经发送，请勿重复点击。");
//        }
        Map<String, String> dataMap = new HashMap<String, String>();

        //生成验证码
        String captcha = getRandomNum(6);
        dataMap.put(smsTempletEnum.getSmsParamName(), captcha);

        SmsResponseParams result = sendMsgHelper(smsTempletEnum, phones, dataMap);
//        if (result) {
//            redisUtil.set(key, captcha, 60);
//        }
        return result;
    }

    /**
     * @Description: 发送信息，  datas入参 与 SmsTempletEnum.smsParamName 的顺序一一对应，不可少
     * @Author: Kang
     * @Date: 2019/3/4 14:09
     */
    public SmsResponseParams sendMsg(SmsTempletEnum smsTempletEnum, List<String> phoneList, List<String> datas) throws Exception {
        String smsParamName = smsTempletEnum.getSmsParamName();
        List<String> params = null;
        Map<String, String> dataMap = new HashMap<>();
        if ((!StringUtils.isEmpty(smsTempletEnum.getSmsParamName())) && smsTempletEnum.getSmsParamName().contains(",")) {
            params = Arrays.asList(smsParamName.split(","));
        } else if ((!StringUtils.isEmpty(smsTempletEnum.getSmsParamName()))) {
            if (datas.size() > 1) {
                throw new Exception("入参个数不正确，请检查！！");
            }
            dataMap.put(smsParamName, datas.get(0));
        }
        if (CollectionUtils.isEmpty(params)) {
            throw new Exception("阿里请参未配置，请检查！！");
        } else if (datas.size() != params.size()) {
            throw new Exception("入参与阿里请参个数不匹配，请检查！！");
        }
        for (int i = 0; i < params.size(); i++) {
            dataMap.put(params.get(i), datas.get(i));
        }

        return sendMsgHelper(smsTempletEnum, phoneList, dataMap);
    }


    /**
     * @Description: 发送信息(单发 / 群发)
     * @Author: Kang
     * @Date: 2019/3/4 12:14
     */
    private SmsResponseParams sendMsgHelper(SmsTempletEnum smsTempletEnum, List<String> phoneList, Map<String, String> dataMap) throws ClientException {
        //添加参数
        SendSmsRequest request = addRequestParam(smsTempletEnum.getSignName(), smsTempletEnum.getTempletCode(), dataMap);

        String phoneStr = "";

        for (String phone : phoneList) {
            phoneStr += phone + ",";
        }
        // 必填:群发待发送手机号
        request.setPhoneNumbers(phoneStr.substring(0, phoneStr.lastIndexOf(",")));
        // hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = resultResponse(request);

        log.info("群发短信返参,code:{},msg:{}", sendSmsResponse.getCode(), sendSmsResponse.getMessage());

        return SmsResponseParams.Istance().setCode(dataMap).setPhones(phoneList).setResult((sendSmsResponse != null && "OK".equals(sendSmsResponse.getCode()))).setStatus(sendSmsResponse.getCode()).setRemark(sendSmsResponse.getMessage());
    }

    /**
     * 功能描述: 添加短信入参
     *
     * @auther: Kang
     * @date: 2018/8/7 10:28
     */
    private SendSmsRequest addRequestParam(String signName, String templateCode, Map<String, String> dataMap) {
        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(JSON.toJSONString(dataMap));
        return request;
    }

    /**
     * @Description: 发送短信
     * @Author: Kang
     * @Date: 2019/3/4 12:09
     */
    private SendSmsResponse resultResponse(SendSmsRequest request) throws ClientException {

        // 可自助调整超时时间
        //String ss = System.getProperty("sun.net.client.defaultConnectTimeout");
        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", AliyunSmsConstants.accessKeyId, AliyunSmsConstants.accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", AliyunSmsConstants.product, AliyunSmsConstants.domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }

    /**
     * @Description: 效验验证码
     * @Author: Kang
     * @Date: 2019/3/4 12:09
     */
    public boolean checkCaptcha(String captcha, String key) {
        if (StringUtils.isEmpty(captcha)) {
            log.info("请输入验证码");
            return false;
        }
        //String captchaStr = redisUtil.get(key).toString();
        String captchaStr = "";
        if (captchaStr == null) {
            log.info("验证码不存在或者已失效");
            return false;
        } else if (!captcha.equals(captchaStr)) {
            log.info("验证码不正确");
            return false;
        }
        return true;
    }

    /**
     * @Description: 生成验证码
     * @Author: Kang
     * @Date: 2019/3/4 12:09
     */
    private String getRandomNum(int len) {
        String str = "0123456789";
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        SmsUtil aliSmsService = new SmsUtil();
        try {
            SmsResponseParams srp = aliSmsService.sendMobileCaptcha(SmsTempletEnum.VERIFICATION_CODE, "13168718611");
            Map<String, String> stringStringMap = srp.getDataMap();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
