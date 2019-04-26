package com.bdxh.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-24 17:46
 **/
@Data
public class BaseDto implements Serializable {

    //学校app_key
    private String appKey;

    //学校app_secret
    private String appSecret;

    //学校类型 1 小学 2 初中 3 高中 4 中专 5 大专 6 高校
    private Byte schoolType;
}