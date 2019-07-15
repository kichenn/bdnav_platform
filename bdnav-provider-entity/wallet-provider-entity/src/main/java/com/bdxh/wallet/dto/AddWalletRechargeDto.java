package com.bdxh.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 增加充值记录 Dto
 * @Author: Kang
 * @Date: 2019/7/15 11:31
 */
@Data
public class AddWalletRechargeDto {

    @NotNull(message = "学校id不能为空")
    @ApiModelProperty(value = "学校id")
    private Long schoolId;

    @NotEmpty(message = "学校编码不能为空")
    @ApiModelProperty(value = "学校编码")
    private String schoolCode;

    @NotEmpty(message = "学校名称不能为空")
    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    @NotNull(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @NotEmpty(message = "用户名称不能为空")
    @ApiModelProperty(value = "用户名称")
    private String userName;

    @NotEmpty(message = "学号不能为空")
    @ApiModelProperty(value = "学号")
    private String cardNumber;

    @NotNull(message = "订单号我方不能为空")
    @ApiModelProperty(value = "订单号我方")
    private Long orderNo;

    @NotEmpty(message = "第三方订单号不能为空")
    @ApiModelProperty(value = "第三方订单号")
    private String outOrderNo;

    @NotNull(message = "充值类型不能为空")
    @ApiModelProperty(value = "充值类型 1线上充值   2 线上代充  3 一体机自充  4窗口充值")
    private Byte rechargeType;

    @NotNull(message = "充值状态不能为空")
    @ApiModelProperty(value = "充值状态  1 未支付 2 支付中 3 支付成功 4 支付失败")
    private Byte rechargeStatus;

    @NotNull(message = "充值金额不能为空")
    @ApiModelProperty(value = "充值金额")
    private BigDecimal rechargeAmount;

    @NotNull(message = "支付时间不能为空")
    @ApiModelProperty(value = "支付时间")
    private Date rechargeTime;

    @ApiModelProperty(value = "线下充值时的设备编号（线上充值为空）")
    private String deviceNumber;

    @ApiModelProperty(value = "家长id（代充时才存在，其他情况为空）")
    private Long familyId;

    @ApiModelProperty(value = "家长卡号（代充时才存在，其他情况为空）")
    private String familyNumber;

    @ApiModelProperty(value = "家长名称（代充时才存在，其他情况为空）")
    private String familyName;

    @ApiModelProperty(value = "操作人id")
    private Long operator;

    @ApiModelProperty(value = "操作人名称")
    private String operatorName;

    @ApiModelProperty(value = "备注")
    private String remark;
}
