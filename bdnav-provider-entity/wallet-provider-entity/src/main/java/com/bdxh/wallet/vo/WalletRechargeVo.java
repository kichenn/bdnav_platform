package com.bdxh.wallet.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**充值记录的展示vo
 * @author WanMing
 * @date 2019/7/15 11:29
 */
@Data
public class WalletRechargeVo {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 学校名称
     */
    @ApiModelProperty("学校名称")
    private String schoolName;

    /**
     * 用户主键
     */
    @ApiModelProperty("用户主键")
    private Long userId;

    /**
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    private String userName;

    /**
     * 用户类型
     */
    @ApiModelProperty("用户类型")
    private Byte userType;

    /**
     * 学号/工号/卡号
     */
    @ApiModelProperty("学号/工号/卡号")
    private String cardNumber;

    /**
     * 实体卡号
     */
    @ApiModelProperty("实体卡号")
    private String physicalNumber;

    /**
     * 平台订单号
     */
    @ApiModelProperty("平台订单号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderNo;

    /**
     * 充值：第三方支付订单号/线下充值订单号
     */
    @ApiModelProperty("充值：第三方支付订单号/线下充值订单号")
    private String outOrderNo;

    /**
     * 充值类型  1线上充值   2 线上代充  3 一体机自充  4窗口充值
     */
    @ApiModelProperty("充值类型  1线上充值   2 线上代充  3 一体机自充  4窗口充值")
    private Byte rechargeType;

    /**
     * 窗口充值时,窗口名称
     */
    @ApiModelProperty("窗口名称")
    private String windowName;

    /**
     * 状态 1 未支付 2 支付中 3 支付成功 4 支付失败
     */
    @ApiModelProperty("状态 1 未支付 2 支付中 3 支付成功 4 支付失败")
    private Byte rechargeStatus;

    /**
     * 充值金额
     */
    @ApiModelProperty("充值金额")
    private BigDecimal rechargeAmount;

    /**
     * 支付时间
     */
    @ApiModelProperty("支付时间")
    private Date rechargeTime;

    /**
     * 线下充值时的设备编号
     */
    @ApiModelProperty("线下充值时的设备编号")
    private String deviceNumber;

    /**
     * 父母为子女充值（父母代充时不为空）
     */
    @ApiModelProperty("父母为子女充值（父母代充时不为空）")
    private Long familyId;

    /**
     * 家长的卡号（父母代充时不为空）
     */
    @ApiModelProperty("家长的卡号（父母代充时不为空）")
    private String familyNumber;

    /**
     * 父母姓名（父母代充时不为空）
     */
    @ApiModelProperty("父母姓名（父母代充时不为空）")
    private String familyName;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;



}
