package com.bdxh.account.entity;

import javax.persistence.Table;

import lombok.Data;

import javax.persistence.*;
import java.lang.String;
import java.lang.Long;

/**
 * @Description: 实体类
 * @Author Kang
 * @Date 2019-05-15 10:48:22
 */
@Data
@Table(name = "bdxh_account_unqiue")
public class AccountUnqiue {

    /**
     *
     */
    @Id
    private Long id;

    /**
     * 手机号
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 登录名
     */
    @Column(name = "login_name")
    private String loginName;

    /**
     * 学校code
     */
    @Column(name = "school_code")
    private String schoolCode;

    /**
     * 账户信息
     */
    @Column(name = "card_number")
    private String cardNumber;
}