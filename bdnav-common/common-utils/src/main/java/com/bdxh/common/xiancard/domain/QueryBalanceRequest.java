package com.bdxh.common.xiancard.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 西安一卡通余额请求类
 * @author: xuyuan
 * @create: 2019-01-11 10:06
 **/
@Data
public class QueryBalanceRequest implements Serializable {

    private static final long serialVersionUID = -6747383999947914955L;

    /**
     * 商户号
     */
    private String merchant;

    /**
     * 卡号
     */
    private String iccid;

    /**
     * 证件类型 1 学号 2 身份证
     */
    private String idtype;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 姓名
     */
    private String name;

    /**
     * 签名
     */
    private String mac;

}
