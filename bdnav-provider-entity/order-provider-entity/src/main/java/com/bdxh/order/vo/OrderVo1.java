package com.bdxh.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 支付成功后订单编号，查询订单部分信息
 * @Author: Kang
 * @Date: 2019/6/21 12:22
 */
@Data
public class OrderVo1 {


    /**
     * 我方订单编号
     */
    private Long orderNo;

    /**
     * 微信方订单号
     */
    private String thirdOrderNo;

    /**
     * 学校id
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
     * 学号
     */
    private String cardNumber;

    /**
     * 用户id
     */
    private Long userId;
}
