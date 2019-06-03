package com.bdxh.common.base.constant;

/**
 * @Description: 微信支付常量
 * @Author: Kang
 * @Date: 2019/5/31 17:49
 */
public class WechatPayConstants {

    public interface JS {

        /**
         * 商户号
         */
        String MCH_ID = "1406768102";

        /**
         * 秘钥 key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
         */
        String APP_KEY = "8HVBmi7D6VMgNHu0Oct3iMYT4aCz4Oc1";

        /**
         * 应用id
         */
        String APP_ID = "wxb5d9031684f864e4";

        /**
         * 订单回调地址
         */
        String NOTICE_URL = "http://af418fe0.ngrok.io/wechatNotice/walletJsRecharge";//"http://zuulproxy.bdxht.com/pay/wechatNotice/walletJsRecharge";

        /**
         * 支付类型
         */
        String THRADE_TYPE = "JSAPI";

        /**
         * 统一下单接口url
         */
        String ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    }

    public interface APP {

        /**
         * 商户号
         */
        String mch_id = "1515043731";

        /**
         * 秘钥
         */
        String app_key = "sB5ms2Y3mNs5QsahJk58I61Dq2Ax12sU";

        /**
         * 应用id
         */
        String app_id = "wxe99d4bf57f4ce53f";

        /**
         * 回调地址
         */
        String notice_url = "http://zuulproxy.bdxht.com/pay/wechatNotice/walletAppRecharge";

        /**
         * 支付类型
         */
        String trade_type = "APP";

        /**
         * 统一下单接口url
         */
        String order_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    }

    public interface COMMON {

        /**
         * 订单查询接口url
         */
        String order_query_url = "https://api.mch.weixin.qq.com/pay/orderquery";

    }
}
