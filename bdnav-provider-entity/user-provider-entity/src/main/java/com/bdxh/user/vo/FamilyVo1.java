package com.bdxh.user.vo;

import lombok.Data;

/**
* @Description: mq中家长实体vo
* @Author: Kang
* @Date: 2019/6/21 19:42
*/
@Data
public class FamilyVo1 {

    /**
     * id
     */
    private Long id;

    /**
     * 家长号
     */
    private String cardNumber;

    /**
     * 家长名称
     */
    private String name;
}
