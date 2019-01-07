package com.bdxh.common.base.constant;

/**
 * @description: 微信支付常量
 * @author: xuyuan
 * @create: 2019-01-02 17:57
 **/
public class WechatPayConstants {

    public interface JS {
        /**
         * 商户号
         */
        String mch_id="1406768102";

        /**
         * 秘钥
         */
        String app_key="8HVBmi7D6VMgNHu0Oct3iMYT4aCz4Oc1";

        /**
         * 应用id
         */
        String app_id="wxb5d9031684f864e4";
        /**
         * 回调地址
         */
        String notice_url="http://wechatpay.dxht.com/wechatJsPay/notice/";

        /**
         * 支付类型
         */
         String trade_type="JSAPI";

        /**
         * 统一下单接口url
         */
        String order_url="https://api.mch.weixin.qq.com/pay/unifiedorder";

    }

    public interface APP {

        /**
         * 商户号
         */
        String mch_id="1515043731";

        /**
         * 秘钥
         */
        String app_key="sB5ms2Y3mNs5QsahJk58I61Dq2Ax12sU";

        /**
         * 应用id
         */
        String app_id="wxe99d4bf57f4ce53f";

        /**
         * 回调地址
         */
        String notice_url="http://wechatpay.dxht.com/wechatAppPay/notice";

        /**
         * 支付类型
         */
        String trade_type="APP";

        /**
         * 统一下单接口url
         */
        String order_url="https://api.mch.weixin.qq.com/pay/unifiedorder";

    }
}
