package com.bdxh.common.helper.excel.bean;

import com.bdxh.common.helper.excel.annotation.ExcelField;
import lombok.Data;

import java.math.BigDecimal;

/**Wallet recharge excel Bean
 * @author WanMing
 * @date 2019/7/22 15:17
 */
@Data
public class WalletRechargeExcelReportBean {



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
     * 用户姓名
     */
    @ExcelField(title = "用户姓名", order = 1)
    private String userName;

    /**
     * 用户类型
     */
    @ExcelField(title = "用户类型", order = 1)
    private String userType;

    /**
     * 学号/工号/卡号
     */
    @ExcelField(title = "卡号", order = 1)
    private String cardNumber;

    /**
     * 物理卡号
     */
    @ExcelField(title = "物理卡号", order = 1)
    private String physicalNumber;

    /**
     * 订单号
     */
    @ExcelField(title = "订单号", order = 1)
    private Long orderNo;



    /**
     * 充值类型  1线上充值   2 线上代充  3 一体机自充  4窗口充值
     */
    @ExcelField(title = "充值类型", order = 1)
    private String rechargeType;

    /**
     * 窗口充值时,窗口名称
     */
    @ExcelField(title = "窗口名称", order = 1)
    private String windowName;

    /**
     * 状态 1 未支付 2 支付中 3 支付成功 4 支付失败
     */
    @ExcelField(title = "支付状态", order = 1)
    private String rechargeStatus;

    /**
     * 充值金额
     */
    @ExcelField(title = "充值金额", order = 1)
    private BigDecimal rechargeAmount;

    /**
     * 支付时间
     */
    @ExcelField(title = "支付时间", order = 1)
    private String rechargeTime;

    /**
     * 家长的卡号（父母代充时不为空）
     */
    @ExcelField(title = "家长的卡号", order = 1)
    private String familyNumber;

    /**
     * 父母姓名（父母代充时不为空）
     */
    @ExcelField(title = "父母姓名", order = 1)
    private String familyName;

    /**
     * 创建时间
     */
    @ExcelField(title = "学校编码", order = 1)
    private String createDate;


}
