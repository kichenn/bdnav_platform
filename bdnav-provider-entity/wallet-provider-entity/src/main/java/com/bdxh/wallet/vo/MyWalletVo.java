package com.bdxh.wallet.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: 我的钱包返参
 * @Author: Kang
 * @Date: 2019/7/12 10:35
 */
@Data
public class MyWalletVo {

    @ApiModelProperty(value = "钱包id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "学校code")
    private String schoolCode;

    @ApiModelProperty(value ="用户id")
    private String userId;

    @ApiModelProperty(value = "卡号")
    private String cardNumber;

    @ApiModelProperty(value = "实体卡号")
    private String physicalNumber;

    @ApiModelProperty(value = "账户余额")
    private BigDecimal amount;

    @ApiModelProperty(value = "账户状态 1.正常 2.异常")
    private Byte status;

    @ApiModelProperty(value = "身份：1为家长，2为学生，3为教职工，4为校友")
    private String identityType;
}
