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
}