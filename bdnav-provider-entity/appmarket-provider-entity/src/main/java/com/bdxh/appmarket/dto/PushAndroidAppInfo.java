package com.bdxh.appmarket.dto;

import lombok.Data;


/**
 * @description:
 * @author: binzh
 * @create: 2019-05-20 18:15
 **/
@Data
public class PushAndroidAppInfo {



    /**
     * 学校id
     */
    private Long schoolId;

    /**
     * 学校编码
     */
    private String schoolCode;

    /**
     * 学号
     */
    private String cardNumber;

    /**
     * 平台 1.android 2.ios
     */
    private Byte platform;

    /**
     * 用户姓名
     */
    private String userName;


    /**
     * 应用名称
     */
    private String appName;
    /**
     * 应用图标地址
     */
    private String iconUrl;

    /**
     * 应用图标名称
     */
    private String iconName;
    /**
     * 应用版本号
     */
    private String appVersion;

    /**
     * apk文件名称
     */
    private String apkName;

    /**
     * apk文件下载地址
     */
    private String apkUrl;

    /**
     * 文件服务器名称
     */
    private String apkUrlName;

    /**
     * apk文件大小
     */
    private Long apkSize;

    /**
     * 应用描述
     */
    private String appDesc;


}