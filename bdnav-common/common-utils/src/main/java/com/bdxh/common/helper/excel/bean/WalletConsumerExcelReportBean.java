package com.bdxh.common.helper.excel.bean;

import com.bdxh.common.helper.excel.annotation.ExcelField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Wallet consumer excel Bean
 * @author WanMing
 * @date 2019/7/17 16:03
 */
@Data
public class WalletConsumerExcelReportBean {

    /**
     * 学校编码
     */
    @ExcelField(title = "学校编码", order = 1)
    private String schoolCode;

    /**
     * 学校名称
     */
    @ExcelField(title = "学校名称", order = 1)
    private String schoolName;


    /**
     * 学生姓名
     */
    @ExcelField(title = "学生姓名", order = 1)
    private String userName;

    /**
     * 学号/工号/卡号
     */
    @ExcelField(title = "卡号", order = 1)
    private String cardNumber;

    /**
     * 物理卡号（刷卡消费是不为空）
     */
    @ExcelField(title = "物理卡号", order = 1)
    private String physicalNumber;


    /**
     * 收费部门名称
     */
    @ExcelField(title = "收费部门", order = 1)
    private String chargeDeptName;

    /**
     * 消费机的设备编码
     */
    @ExcelField(title = "设备编码", order = 1)
    private String deviceNumber;

    /**
     * 消费时的设备名称
     */
    @ExcelField(title = "设备名称", order = 1)
    private String deviceName;

    /**
     * 平台订单号
     */
    @ExcelField(title = "平台订单号", order = 1)
    private String orderNo;


    /**
     * 消费金额
     */
    @ExcelField(title = "消费金额", order = 1)
    private BigDecimal consumerAmount;

    /**
     * 消费支付时间
     */
    @ExcelField(title = "支付时间", order = 1)
    private String consumerTime;


    /**
     * 支付状态 1 未扣款  2扣款成功  3 扣款中 4 扣款失败
     */
    @ExcelField(title = "支付状态", order = 1)
    private String consumerStatus;

    /**
     * 创建时间
     */
    @ExcelField(title = "创建时间", order = 1)
    private String createDate;

}
