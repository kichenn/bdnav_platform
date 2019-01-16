package com.bdxh.common.base.constant;

/**
 * @description: rocketmq常量
 * @author: xuyuan
 * @create: 2019-01-15 11:05
 **/
public class RocketMqConstrants {

    /**
     * rocketmq事务回查redis前缀
     */
    public static final String TRANSACTION_REDIS_PREFIX="transaction:";

    public interface Topic {

        /**
         * 钱包充值微信回调topic
         */
        String wechatPayWalletNotice="wechatPayWalletNotice";

        /**
         * 钱包一卡通充值topic
         */
        String xiancardWalletRecharge="xiancardWalletRecharge";

    }

    /**
     *
     */
    public interface Tags {

        String wechatPayWalletNotice_APP="app";

        String wechatPayWalletNotice_JS="js";

        String xiancardWalletRecharge_add="add";

    }

}
