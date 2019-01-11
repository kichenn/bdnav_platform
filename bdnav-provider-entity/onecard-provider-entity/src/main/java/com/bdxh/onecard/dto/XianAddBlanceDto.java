package com.bdxh.onecard.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 西安一卡通余额查询dto
 * @author: xuyuan
 * @create: 2019-01-10 19:32
 **/
@Data
public class XianAddBlanceDto implements Serializable {

    private static final long serialVersionUID = 2867596624534794874L;

    /**
     * 学校编码
     */
    @NotEmpty(message = "学校编码不能为空")
    private String schoolCode;

    /**
     * 学号
     */
    @NotEmpty(message = "学号不能为空")
    private String cardNumber;

    /**
     * 姓名
     */
    @NotEmpty(message = "姓名不能为空")
    private String userName;

    /**
     * 订单号
     */
    @NotEmpty(message = "订单号不能为空")
    private String orderNo;

    /**
     * 充值金额
     */
    @NotNull(message = "充值金额")
    @DecimalMin(value = "0.01", message = "充值金额参数不正确")
    private BigDecimal money;

}
