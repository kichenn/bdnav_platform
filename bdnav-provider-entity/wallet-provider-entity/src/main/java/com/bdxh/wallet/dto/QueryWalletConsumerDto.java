package com.bdxh.wallet.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/** 查询消费记录的dto
 * @author WanMing
 * @date 2019/7/12 17:03
 */
@Data
public class QueryWalletConsumerDto extends Query {

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
     * 物理卡号（刷卡消费是不为空）
     */
    @ApiModelProperty("物理卡号（刷卡消费是不为空）")
    private String physicalNumber;


    /**
     * 平台订单号
     */
    @ApiModelProperty("平台订单号")
    private String orderNo;

    /**
     * 外部设备生成的订单号
     */
    @ApiModelProperty("外部设备生成的订单号")
    private String outOrderNo;


    /**
     * 支付状态 1 未扣款  2扣款成功  3 扣款中 4 扣款失败
     */
    @ApiModelProperty("支付状态 1 未扣款  2扣款成功  3 扣款中 4 扣款失败")
    private Byte consumerStatus;



}
