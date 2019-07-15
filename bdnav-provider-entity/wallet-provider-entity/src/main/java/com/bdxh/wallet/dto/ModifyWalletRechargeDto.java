package com.bdxh.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**充值的dto
 * @author WanMing
 * @date 2019/7/15 11:10
 */
@Data
public class ModifyWalletRechargeDto {


    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 学校主键
     */
    @ApiModelProperty("学校主键")
    private Long schoolId;

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
     * 学号/工号/卡号
     */
    @ApiModelProperty("学号/工号/卡号")
    private String cardNumber;

    /**
     * 平台订单号
     */
    @ApiModelProperty("平台订单号")
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
    private String rechargeType;

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
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operator;

    /**
     * 操作人姓名
     */
    @ApiModelProperty("操作人姓名")
    private String operatorName;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
