package com.bdxh.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 查询充值记录dto
 * @Author: Kang
 * @Date: 2019/7/12 15:06
 */
@Data
public class FindRechargeRecordDto {

    @ApiModelProperty(value="学校code")
    private String schoolCode;

    @ApiModelProperty(value="学号")
    private String cardNumber;

    @ApiModelProperty(value="充值类型")
    private String rechargeType;

    @ApiModelProperty(value="筛选时间")
    private String rechargeDate;
}
