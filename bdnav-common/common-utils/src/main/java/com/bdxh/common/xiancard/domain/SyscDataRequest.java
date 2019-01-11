package com.bdxh.common.xiancard.domain;

import lombok.Data;
import java.io.Serializable;

/**
 * @description: 用户数据同步接口
 * @author: xuyuan
 * @create: 2019-01-11 14:04
 **/
@Data
public class SyscDataRequest implements Serializable {

    private static final long serialVersionUID = -944269431448819573L;

    /**
     * 商户号
     */
    private String merchant;

    /**
     * 学号
     */
    private String iccid;

    /**
     * 用户类型（1 学生 2 教职工 3 家长 6 其他 8 居民 9 校友 10 领导）
     */
    private String usertype;

    /**
     * 证件类型 1 学号 2 身份证
     */
    private String idtype;

    /**
     * 姓名
     */
    private String name;

    /**
     * 物理芯片号
     */
    private String physicalchipnumber;

    /**
     * 一卡通号
     */
    private String physicalcardnmber;

    /**
     * 签名
     */
    private String mac;

}
