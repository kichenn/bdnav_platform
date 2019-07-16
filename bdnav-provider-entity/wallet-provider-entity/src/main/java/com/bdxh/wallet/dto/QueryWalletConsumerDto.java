package com.bdxh.wallet.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;


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
     * 业务类型：1刷卡 2 刷码  3扫码  4人脸识别
     */
    @ApiModelProperty("业务类型：1刷卡 2 刷码  3扫码  4人脸识别")
    private Byte consumerType;


    /**
     * 支付状态 1 未扣款  2扣款成功  3 扣款中 4 扣款失败
     */
    @ApiModelProperty("支付状态 1 未扣款  2扣款成功  3 扣款中 4 扣款失败")
    private Byte consumerStatus;

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
