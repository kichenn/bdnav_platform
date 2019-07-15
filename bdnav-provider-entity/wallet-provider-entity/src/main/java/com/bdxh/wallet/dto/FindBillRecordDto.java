package com.bdxh.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description: 账单记录dto
 * @Author: Kang
 * @Date: 2019/7/12 15:06
 */
@Data
public class FindBillRecordDto {

    @ApiModelProperty(value = "学校code")
    private String schoolCode;

    @ApiModelProperty(value = "学号")
    private String cardNumber;

    @NotNull(message = "账单类型不能为空")
    @ApiModelProperty(value = "账单类型（'0.全部 1 餐饮美食 2生活日用  3读书学习 4医疗保健 5其他分类）")
    private Integer billType;

    @ApiModelProperty(value = "筛选时间")
    private String screenDate;
}
