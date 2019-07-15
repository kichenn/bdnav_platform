package com.bdxh.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: 钱包充值DTO
 * @Author: Kang
 * @Date: 2019/7/15 10:31
 */
@Data
public class AddWalletPayDto {

    @ApiModelProperty(value = "学校id")
    private Long schoolId;

    @ApiModelProperty(value = "学生id")
    private Long userId;

    @ApiModelProperty(value = "充值类型 1线上充值   2 线上代充  3 一体机自充  4窗口充值")
    private Byte rechargeType;

    @ApiModelProperty(value = "充值金额")
    private BigDecimal rechargeAmount;

    @ApiModelProperty(value = "ip地址")
    private String ip;

    @ApiModelProperty(value = "线下充值的设备编号，线上充值该值为空")
    private String deviceNumber;

    @ApiModelProperty(value = "如果为家长代充则存在，否则为空")
    private Long familyId;

    @ApiModelProperty(value = "操作人id")
    private Long operator;

    @ApiModelProperty(value = "操作人姓名")
    private String operatorName;

    @ApiModelProperty(value = "备注")
    private String remark;
}
