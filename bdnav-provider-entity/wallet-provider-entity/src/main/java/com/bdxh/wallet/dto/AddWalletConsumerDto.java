package com.bdxh.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**添加消费记录的dto
 * @author WanMing
 * @date 2019/7/12 15:07
 */
@Data
public class AddWalletConsumerDto {


    /**
     * 学校主键
     */
    @ApiModelProperty("学校主键")
    private Long schoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 学校名称
     */
    @ApiModelProperty("学校名称")
    private String schoolName;

    /**
     * 用户主键
     */
    @ApiModelProperty("用户主键")
    private Long userId;

    /**
     * 学生姓名
     */
    @ApiModelProperty("学生姓名")
    private String userName;

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
     * 收费部门id
     */
    @ApiModelProperty("收费部门id")
    private Long chargeDeptId;



    /**
     * 消费时的设备编码
     */
    @ApiModelProperty("消费时的设备编码")
    private String deviceNumber;

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
     * 消费金额
     */
    @ApiModelProperty("消费金额")
    private BigDecimal consumerAmount;

    /**
     * 消费支付时间
     */
    @ApiModelProperty("消费支付时间")
    private Date consumerTime;

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
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}
