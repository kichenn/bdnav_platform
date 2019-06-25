package com.bdxh.system.vo;

import lombok.Data;

/**
 * @author WanMing
 * @date 2019/6/24 10:07
 */
@Data
public class KingSoftResultVo {

    /**
     * 查询状态  success  :1 查询成功  2查询失败
     */
    private Byte success;

    /**
     * 查询成功 网址类型  phish : -1 表示未知;0 表示非钓鱼;1 表示钓鱼;2 表示网站高风险,有钓鱼嫌疑
     */
    private Byte phish;

    /**
     * 查询失败 状态码  errno
     */
    private Byte errno;


    /**
     * 查询失败 失败原因  msg
     */
    private String msg;
}
