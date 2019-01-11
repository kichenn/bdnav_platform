package com.bdxh.onecard.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 西安一卡通数据同步接口
 * @author: xuyuan
 * @create: 2019-01-11 14:17
 **/
@Data
public class XianSyscDataDto implements Serializable {

    private static final long serialVersionUID = 4865538494719409496L;

    /**
     * 学校编码
     */
    private String schoolCode;

    /**
     * 用户类型（1 学生 2 教职工 3 家长 6 其他 8 居民 9 校友 10 领导）
     */
    private String userType;

    /**
     * 姓名
     */
    private String name;

    /**
     * 证件类型 1 学号 2 身份证
     */
    private String cardType;

    /**
     * 学号
     */
    private String cardNumber;

}
