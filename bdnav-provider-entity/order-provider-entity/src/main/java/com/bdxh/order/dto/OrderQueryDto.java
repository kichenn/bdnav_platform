package com.bdxh.order.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @description: 订单查询dto
 * @author: xuyuan
 * @create: 2019-01-09 18:25
 **/
@Data
@ApiModel("查询订单dto")
public class OrderQueryDto extends Query {


    /**
     * 订单号
     */
    @ApiModelProperty("订单号")
    private Long orderNo;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 学号
     */
    @ApiModelProperty("学号")
    private String cardNumber;


}
