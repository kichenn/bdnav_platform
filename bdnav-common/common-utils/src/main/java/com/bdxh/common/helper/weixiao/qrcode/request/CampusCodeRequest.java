package com.bdxh.common.helper.weixiao.qrcode.request;

import lombok.Data;

/**
 * @Description: 校园码解码入参
 * @Author: Kang
 * @Date: 2019/7/11 14:16
 */
@Data
public class CampusCodeRequest {

    /**
     * 应用分配的app_key
     */
    private String app_key;

    /**
     * 时间戳
     */
    private long timestamp;

    /**
     * 随机字符串
     */
    private String nonce;

    /**
     * 数字签名
     */
    private String signature;

    /**
     * 场景id
     */
    private int scene;

    /**
     * 设备号
     */
    private String device_no;

    /**
     * 地点
     */
    private String location;

    /**
     * 校园码
     */
    private String auth_code;

    /**
     * 学校编码
     */
    private String school_code;

}
