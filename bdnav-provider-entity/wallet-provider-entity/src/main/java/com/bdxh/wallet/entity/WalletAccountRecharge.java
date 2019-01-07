package com.bdxh.wallet.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "wallet_account_recharge")
public class WalletAccountRecharge implements Serializable {

    private static final long serialVersionUID = -8334989632860893608L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 钱包账户id
     */
    @Column(name = "account_id")
    private Long accountId;

    /**
     * 订单号
     */
    @Column(name = "order_no")
    private Long orderNo;

    /**
     * 订单类型
     */
    @Column(name = "order_type")
    private String orderType;

    /**
     * 第三方支付订单号
     */
    @Column(name = "third_order_no")
    private String thirdOrderNo;

    /**
     * 一卡通流水号，对账关键字段
     */
    private String acceptseq;

    /**
     * 状态 1 未支付 2 支付中 3 支付成功 4 支付失败 5 未充值 6 充值中 7充值成功 8 充值失败
     */
    private Byte status;

    /**
     * 充值金额
     */
    @Column(name = "recharge_money")
    private BigDecimal rechargeMoney;

    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 修改时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取钱包账户id
     *
     * @return account_id - 钱包账户id
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * 设置钱包账户id
     *
     * @param accountId 钱包账户id
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取订单号
     *
     * @return order_no - 订单号
     */
    public Long getOrderNo() {
        return orderNo;
    }

    /**
     * 设置订单号
     *
     * @param orderNo 订单号
     */
    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取订单类型
     *
     * @return order_no - 订单类型
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * 设置订单类型
     *
     * @param orderType 订单类型
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * 获取第三方支付订单号
     *
     * @return third_order_no - 第三方支付订单号
     */
    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    /**
     * 设置第三方支付订单号
     *
     * @param thirdOrderNo 第三方支付订单号
     */
    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo == null ? null : thirdOrderNo.trim();
    }

    /**
     * 获取一卡通流水号，对账关键字段
     *
     * @return acceptseq - 一卡通流水号，对账关键字段
     */
    public String getAcceptseq() {
        return acceptseq;
    }

    /**
     * 设置一卡通流水号，对账关键字段
     *
     * @param acceptseq 一卡通流水号，对账关键字段
     */
    public void setAcceptseq(String acceptseq) {
        this.acceptseq = acceptseq == null ? null : acceptseq.trim();
    }

    /**
     * 获取状态 1 未支付 2 支付中 3 支付成功 4 支付失败 5 未充值 6 充值中 7充值成功 8 充值失败
     *
     * @return status - 状态 1 未支付 2 支付中 3 支付成功 4 支付失败 5 未充值 6 充值中 7充值成功 8 充值失败
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态 1 未支付 2 支付中 3 支付成功 4 支付失败 5 未充值 6 充值中 7充值成功 8 充值失败
     *
     * @param status 状态 1 未支付 2 支付中 3 支付成功 4 支付失败 5 未充值 6 充值中 7充值成功 8 充值失败
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取充值金额
     *
     * @return recharge_money - 充值金额
     */
    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }

    /**
     * 设置充值金额
     *
     * @param rechargeMoney 充值金额
     */
    public void setRechargeMoney(BigDecimal rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    /**
     * 获取支付时间
     *
     * @return pay_time - 支付时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 设置支付时间
     *
     * @param payTime 支付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取修改时间
     *
     * @return update_date - 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置修改时间
     *
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}