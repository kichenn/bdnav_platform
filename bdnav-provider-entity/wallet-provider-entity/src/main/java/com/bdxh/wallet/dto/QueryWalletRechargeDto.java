package com.bdxh.wallet.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author WanMing
 * @date 2019/7/15 11:16
 */
@Data
public class QueryWalletRechargeDto extends Query {

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;


    /**
     * 学号/工号/卡号
     */
    @ApiModelProperty("学号/工号/卡号")
    private String cardNumber;

    /**
     * 平台订单号
     */
    @ApiModelProperty("平台订单号")
    private Long orderNo;

    /**
     * 充值类型  1线上充值   2 线上代充  3 一体机自充  4窗口充值
     */
    @ApiModelProperty("充值类型  1线上充值   2 线上代充  3 一体机自充  4窗口充值")
    private String rechargeType;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private Date endTime;









}
