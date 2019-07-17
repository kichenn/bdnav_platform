package com.bdxh.wallet.dto;

import com.bdxh.wallet.enums.UserTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BusinessCardDto {

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;


    /**
     * 学号、工号
     */
    @ApiModelProperty("学号、工号")
    private String cardNumber;

    /**
     * 用户类型 1学生 2教师 3家长
     */
    @ApiModelProperty("用户类型 1学生 2教师 3家长")
    private UserTypeEnum userTypeEnum;

}
