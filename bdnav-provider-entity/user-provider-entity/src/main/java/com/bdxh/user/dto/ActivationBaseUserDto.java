package com.bdxh.user.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-08 16:12
 **/
@Data
public class ActivationBaseUserDto extends WeiXiaoDto implements Serializable {


    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;


    /**
     * 学号
     */
    @ApiModelProperty("学号")
    private String cardNumber;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号")
    private String phone;
}