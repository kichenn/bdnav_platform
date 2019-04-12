package com.bdxh.appburied.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Description: shardingJDBC分库分表策略  父字段
 * @Author: Kang
 * @Date: 2019/4/12 10:03
 */
@Data
public class AppBuriedParentDto {

    @NotEmpty(message = "学校编码不能为空")
    @ApiModelProperty("学校编码")
    private String schoolCode;

    @NotEmpty(message = "用户学号不能为空")
    @ApiModelProperty("用户学号")
    private String cardNumber;

}