package com.bdxh.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 创建钱包账户
 * @Author: Kang
 * @Date: 2019/7/22 11:53
 */
@Data
public class CreateWalletDto {

    @ApiModelProperty(value = "schoolId")
    private String schoolId;

    @ApiModelProperty(value = "学校编码")
    private String schoolCode;

    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "卡号")
    private String cardNumber;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户类型")
    private Byte userType;

    @ApiModelProperty(value = "组织id")
    private String orgId;

}
