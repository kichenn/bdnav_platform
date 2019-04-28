package com.bdxh.order.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: 订单查询dto
 * @author: xuyuan
 * @create: 2019-01-09 18:25
 **/
@Data
public class OrderQueryDto extends Query {

    /**
     * 学校编码
     */
    @NotEmpty(message = "学校编码不能为空")
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    @ApiModelProperty("用户id")
    private Long userId;

}
