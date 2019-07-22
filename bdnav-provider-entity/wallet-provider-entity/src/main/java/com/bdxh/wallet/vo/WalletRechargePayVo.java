package com.bdxh.wallet.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 钱包充值时，查询钱包信息dto
 */
@Data
public class WalletRechargePayVo {

    /**
     * 学校编码不能为空
     */
    private String schoolCode;

    /**
     * 学号不能为空
     */
    private String cardNumber;

    /**
     * 充值金额
     */
    private BigDecimal rechargeAmount;
}
