package com.bdxh.wallet.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    @ApiModelProperty(value = "卡号")
    private String cardNumber;

//    private String
}
