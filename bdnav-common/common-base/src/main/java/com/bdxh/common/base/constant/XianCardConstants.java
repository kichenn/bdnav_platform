package com.bdxh.common.base.constant;

/**
 * @description: 西安一卡通常量
 * @author: xuyuan
 * @create: 2019-01-10 18:49
 **/
public class XianCardConstants {

    /**
     * 充值结果uri
     */
    public static final String SYSC_DATA_URI="/ocs/air/weixiao/receive/stu/sync";

    /**
     * 一卡通余额查询接口uri
     */
    public static final String QUERY_BALANCE_URI="/ocs/air/id/check";

    /**
     * 一卡通充值接口uri
     */
    public static final String RECHARGE_URI="/ocs/air/recharge";

    /**
     * 充值结果uri
     */
    public static final String RECHARGE_RESULT_URI="/ocs/air/recharge/check";

    /**
     * 一卡通充值接口uri
     */
    public static final String QUERY_CONSUME_URI="/ocs/air/weixiao/receive/trade/info";

    public enum IdentityType {

        CARD_XUEHAO("1"),CARD_SFZ("2");

        private String code;

        IdentityType(String code){
            this.code=code;
        }

        public String getCode() {
            return code;
        }

    }

}
