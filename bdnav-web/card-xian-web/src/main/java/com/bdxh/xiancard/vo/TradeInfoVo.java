package com.bdxh.xiancard.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeInfoVo {
    /**
     * 交易时间
     */
    private String tradetime;
    /**
     * 交易金额
     */
    private String amout;
    /**
     * 用户账户
     */
    private BigDecimal userAmount;
    /**
     * 交易Pos终端
     */
    private String posno;
    /**
     * 交易POS终端描述信息
     */
    private String posname;
    /**
     * 流水账号
     */
    private String tradeseq;
    /**
     * 交易类型
     */
    private Integer type;

}
