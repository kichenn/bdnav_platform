package com.bdxh.user.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-15 16:09
 **/
@Data
@Table(name ="t_base_user_unqiue")
public class BaseUserUnqiue {
    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 手机号
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 学校code
     */
    @Column(name="school_code")
    private String schoolCode;
}