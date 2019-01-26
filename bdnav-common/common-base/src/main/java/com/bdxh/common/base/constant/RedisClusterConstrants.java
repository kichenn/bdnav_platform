package com.bdxh.common.base.constant;

/**
 * @description: redis常量配置
 * @author: xuyuan
 * @create: 2019-01-15 19:03
 **/
public class RedisClusterConstrants {

    /**
     * String类型key前缀
     */
    public interface KeyPrefix {

        String wechatpay_wallet_app_notice="wechatpaywalletappnotice:";

        String wechatpay_wallet_js_notice="wechatpaywalletjsnotice:";

        String wechatpay_wallet_query_wechart_result="wechatpaywalletquerywechartresult:";

        String wechatpay_wallet_query_xiancard_result="wechatpaywalletqueryxiancardresult:";

        String kailu_wallet_sub_xiancard_acceptseq="kailuwalletsubxiancardacceptseq:";

        String kailu_wallet_sub_xiancard_fail_update="kailuwalletsubxiancardfailupdate:";
        
    }

}
