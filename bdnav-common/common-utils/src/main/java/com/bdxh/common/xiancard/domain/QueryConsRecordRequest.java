package com.bdxh.common.xiancard.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 西安一卡通消费记录查询
 * @author: xuyuan
 * @create: 2019-01-11 10:11
 **/
@Data
public class QueryConsRecordRequest implements Serializable {

    private static final long serialVersionUID = 2908899859241847027L;

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
     * 姓名
     */
    private String name;

    /**
     * 开始时间
     */
    private String starttime;

    /**
     * 结束时间
     */
    private String entime;

    /**
     * 签名
     */
    private String mac;

}
