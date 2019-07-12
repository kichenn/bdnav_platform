package com.bdxh.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.math.BigDecimal;

@Data
public class AddPhysicalCard {


    /**
     * 学校主键
     */
    @ApiModelProperty("主键")
    private Long schoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 用户主键
     */
    @ApiModelProperty("用户主键")
    private Long userId;

    /**
     * 学号、工号
     */
    @ApiModelProperty("学号、工号")
    private String cardNumber;

    /**
     * 钱包账户主键
     */
    @ApiModelProperty("钱包账户主键")
    private Long walletAccountId;

    /**
     * 卡物理号
     */
    @ApiModelProperty("卡物理号")
    private String physicalNumber;

    /**
     * 物理卡芯片号
     */
    @ApiModelProperty("物理卡芯片号")
    private String physicalChipNumber;

    /**
     * 押金
     */
    @ApiModelProperty("押金")
    private BigDecimal deposit;

    /**
     * 物理卡状态 1 正常 2 挂失 3注销
     */
    @ApiModelProperty("物理卡状态 1 正常 2 挂失 3注销")
    private Byte status;

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
