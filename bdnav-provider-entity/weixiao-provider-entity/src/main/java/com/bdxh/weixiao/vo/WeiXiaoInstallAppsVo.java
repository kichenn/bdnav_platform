package com.bdxh.weixiao.vo;

import java.util.Date;
import lombok.Data;
/**
 * @description:
 * @author: binzh
 * @create: 2019-05-21 11:35
 **/
@Data
public class WeiXiaoInstallAppsVo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 平台 1 android 2 ios
     */
    private Byte platform;

    /**
     * 账户id
     */
    private Long accountId;

    /**
     * 学校ID
     */
    private Long schoolId;

    /**
     * 学校编码
     */
    private String schoolCode;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 用户卡号
     */
    private String cardNumber;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用包名
     */
    private String appPackage;

    /**
     * 应用图标地址
     */
    private String iconUrl;

    /**
     * 应用图标名称
     */
    private String iconName;

    /**
     * 安装时间
     */
    private Date createDate;

    /**
     * 应用状态 1 正常 2 锁定
     */
    private Byte appStatus=1;
}