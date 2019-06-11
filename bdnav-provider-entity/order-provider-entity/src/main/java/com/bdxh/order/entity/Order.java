package com.bdxh.order.entity;

import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.lang.String;
import java.math.BigDecimal;
import java.lang.Integer;


/**
 * @Description: 实体类
 * @Date 2019-05-06 11:48:20
 */

@Data
@Table(name = "t_order")
public class Order {


    /**
     * 订单号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long orderNo;

    /**
     * 第三方订单号
     */
    @Column(name = "third_order_no")
    private String thirdOrderNo;

    /**
     * 学校主键
     */
    @Column(name = "school_id")
    private Long schoolId;

    /**
     * 学校编码
     */
    @Column(name = "school_code")
    private String schoolCode;

    /**
     * 学校名称
     */
    @Column(name = "school_name")
    private String schoolName;

    /**
     * 家长主键
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 学号
     */
    @Column(name = "card_number")
    private String cardNumber;

    /**
     * 用户姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户类型 1 学生 2 老师 3 家长
     */
    @Column(name = "user_type")
    private Byte userType;

    /**
     * 用户openid
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 产品主键
     */
    @Column(name = "product_id")
    private String productId;



    /**
     * 订单总金额
     */
    @Column(name = "total_money")
    private BigDecimal totalMoney;

    /**
     * 订单金额
     */
    @Column(name = "order_money")
    private BigDecimal orderMoney;

    /**
     * 支付金额
     */
    @Column(name = "pay_money")
    private BigDecimal payMoney;

    /**
     * 交易状态 1 进行中 2 已取消 3 已删除 4 交易成功
     */
    @Column(name = "trade_status")
    private Byte tradeStatus;

    /**
     * 支付状态 1 未支付 2 支付中 3 支付成功 4 支付失败
     */
    @Column(name = "pay_status")
    private Byte payStatus;

    /**
     * 业务状态 1 未处理 2 已处理
     */
    @Column(name = "business_status")
    private Byte businessStatus;

    /**
     * 业务类型 1 校园钱包充值  2管控服务
     */
    @Column(name = "business_type")
    private Byte businessType;

    /**
     * 支付渠道 1 微信支付
     */
    @Column(name = "pay_type")
    private Byte payType;

    /**
     * 订单类型  1 JSAPI支付
     */
    @Column(name = "trade_type")
    private Byte tradeType;

    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 支付结束时间
     */
    @Column(name = "pay_end_time")
    private Date payEndTime;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 修改时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 操作人
     */
    @Column(name = "operator")
    private Long operator;

    /**
     * 操作人姓名
     */
    @Column(name = "operator_name")
    private String operatorName;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;




}