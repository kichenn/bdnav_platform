package com.bdxh.common.utils;

import org.springframework.util.DigestUtils;
import sun.misc.BASE64Encoder;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

/**
 *  金山网络云盾url检测
 * @author WanMing
 * @date 2019/6/21 15:32
 */

public class UrlSecurityValidator {

    /**
     * 目前每个 appkey 的限制为:
     * 每分钟访问上限为 1000 次,每天访问上限为 10 万次
     */
    private static final String APP_KEY = "k-33356";

    /**
     * 用于授权验证测试 secret
     */
    private static final String SECRET = "a176201e188a0969cd7b7fa2ef3c8d14";

    /**
     * 验证安全的基础 url
     */
    private static final String BASE_URL = "http://open.pc120.com/phish/?";

    /**
     * 校验url的安全性
     *
     * @param url 需要校验的url
     * @return
     */
    public static String checkUrlSecurity(String url) {
        //参数编码
        String q = safeUrlBase64Encode(url);
        //获取时间戳
        String timestamp = getTimestamp();
        //获取签名
        String sign = getSign(q,timestamp);
        //最终验证的url
        String apiUrl = getApiUrl(q, sign, timestamp);
        String resultStr = null;
        try {
             resultStr = HttpClientUtils.doGet(apiUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;

    }


    /**
     * 对参数进行base64编码，为了确保编码后的结果不会打断URI，我们采用urlsafe base64编码，
     * 它和普通base64编码的差别在于：将普通base64编码结果中的加号(+)替换成减号(-)，将斜杠(/)替换成下划线(_)
     * @Author: WanMing
     * @Date: 2019/6/21 15:50
     */
    private static String safeUrlBase64Encode(String url) {
        return new BASE64Encoder().encode(url.getBytes(StandardCharsets.UTF_8))
                .replace('+', '-').replace('/', '_');
    }


    /**
     * Timestamp为从格林威治时间1970年01月01日00时00分00秒起至现在的总秒数，本文当要求精确到毫秒。
     * 一个有效的timestamp必须满足两个条件：唯一，即一个timestamp只能使用一次；必须是5分钟内的timestamp
     * @return
     */
    private static String getTimestamp(){
        return new BigDecimal(System.currentTimeMillis()).divide(new BigDecimal(1000)
                , 3, BigDecimal.ROUND_DOWN).toString();
    }




    /**
     * 计算签名(sign)：首先需要生成签名字符基串(下文称此字符串为signature_base_string)。具体生成算法是，
     * 将HTTP查询参数包括GET和POST中的参数按照参数名进行文本排序，然后参数名和参数值用等号(=)连接，
     * 各个参数用&连接；将API的URI路径和上述排序之后的参数字符串用问号(?)连接之后形成signature_base_string。
     * 接下来把signature_base_string + secret组合成一个字符串，计算出此字符串的md5码作为签名sign
     *
     * @Author: WanMing
     * @Date: 2019/6/21 16:06
     */
    private static String getSign(String q,String timestamp){
        StringBuilder builder = new StringBuilder();
        builder.append("/phish/?").append("appkey="+APP_KEY).append("&q="+q)
                .append("&timestamp="+timestamp);
        //md5加密
        return DigestUtils.md5DigestAsHex((builder.toString() + SECRET).getBytes());
    }

    /**
     * url组合
     * @param q 参数
     * @param sign 签名
     * @param timestamp 时间戳
     * @return
     */
    private static String getApiUrl(String q,String sign,String timestamp){
        return new StringBuilder().append(BASE_URL).append("q="+q)
                .append("&appkey="+APP_KEY).append("&timestamp="+timestamp)
                .append("&sign="+sign).toString();
    }




    public static void main(String[] args) {
        String s = UrlSecurityValidator.checkUrlSecurity("14.215.178.36");
        System.out.println(s);
    }
}
