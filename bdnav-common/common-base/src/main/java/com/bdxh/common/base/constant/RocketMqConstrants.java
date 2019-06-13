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
    public static final String TRANSACTION_REDIS_PREFIX = "rocketmqtransaction:";

    /**
     * rocketmq topics
     */
    public interface Topic {

        /**
         * 钱包充值微信回调topic
         */
        String wechatPayWalletNotice = "wechatPayWalletNotice";

        /**
         * 钱包一卡通充值topic
         */
        String xiancardWalletRecharge = "xiancardWalletRecharge";

        /**
         * 凯路订单支付topic
         */
        String xiancardWalletKailu = "xiancardWalletKailu";

        /**
         * 凯路订单支付更新topic
         */
        String xiancardWalletKailuUpdate = "xiancardWalletKailuUpdate";


        /**
         * school服务，院系或部门组织架构更新 topic
         */
        String schoolOrganizationTopic = "schoolOrganizationTopic";

        /**
         * 北斗星航对接麦圈，远景达 topic
         */
        String bdxhTopic="bdxh";

        /**
         * user服务，学生老师或者信息修改进行其他模块的数据维护 topic
         */
        String userOrganizationTopic="userOrganizationTopic";

        /**
         * 测试
         */
        String TestTopic="testTopicUser";
    }

    /**
     * rocketmq tags
     */
    public interface Tags {

        /**
         * 钱包充值微信回调tag app的
         */
        String wechatPayWalletNotice_app = "appmarket";

        /**
         * 钱包充值微信回调tag js的
         */
        String wechatPayWalletNotice_js = "js";

        /**
         * 钱包充值微信回调tag 查询
         */
        String wechatPayWalletNotice_query = "query";

        /**
         * 钱包一卡通充值tag 新增
         */
        String xiancardWalletRecharge_add = "add";

        /**
         * 凯路订单支付tag
         */
        String xiancardWalletKailu_consumer = "consumer";

        /**
         * 凯路订单更新tag
         */
        String xiancardWalletKailuUpdate_update = "update";

        /**
         * school服务，院系组织架构更新 tag
         */
        String schoolOrganizationTag_class = "schoolOrganizationTag_class";

        /**
         * school服务，部门组织架构更新 tag
         */
        String schoolOrganizationTag_dept = "schoolOrganizationTag_dept";

        /**
         * school服务，学校更新 tag
         */
        String schoolOrganizationTag_school="schoolOrganizationTag_school";

        /**
         * school服务，系统用户信息更新 tag
         */
        String schoolUserInfoTag_schoolUser="schoolOrganizationTag_schoolUser";

        /**
         * school服务，系统用户信息角色关系 tag
         */
        String schoolUserInfoTag_schoolUserRole="schoolUserInfoTag_schoolUserRole";

        /**
         * school服务，系统角色信息 tag
         */
        String schoolUserInfoTag_schoolRole="schoolUserInfoTag_schoolRole";

        /**
         * user服务，学生信息更新同步 tag
         */
        String userInfoTag_student="userInfoTag_student";

        /**
         * user服务，基础用户信息更新同步 tag
         */
        String userInfoTag_baseUser="userInfoTag_baseUser";

        /**
         * user服务，老师信息更新同步 tag
         */
        String userInfoTag_teacher="userInfoTag_teacher";

        /**
         * user服务，老师部门关系信息更新同步 tag
         */
        String userInfoTag_teacherDept="userInfoTag_teacherDept";

    }

}
