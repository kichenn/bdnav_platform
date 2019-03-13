package com.bdxh.weixiao.configration.security.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 用户信息
 * @author: xuyuan
 * @create: 2019-03-13 15:27
 **/
@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 8541838882121685433L;

    private String schoolCode;

    private String name;

    private String phone;

    private String cardNumber;

}
