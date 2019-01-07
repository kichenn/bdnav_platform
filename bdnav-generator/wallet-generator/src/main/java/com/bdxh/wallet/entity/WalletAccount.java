package com.bdxh.wallet.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "wallet_account")
public class WalletAccount {
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
     * 钱包账户
     */
    private Long account;

    /**
     * 账户总余额
     */
    private BigDecimal money;

    /**
     * 可用余额
     */
    @Column(name = "avalible_money")
    private BigDecimal avalibleMoney;

    /**
     * 冻结余额
     */
    @Column(name = "freeze_money")
    private BigDecimal freezeMoney;

    /**
     * 状态 1 正常 2 异常
     */
    private Byte valid;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

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
     * 获取钱包账户
     *
     * @return account - 钱包账户
     */
    public Long getAccount() {
        return account;
    }

    /**
     * 设置钱包账户
     *
     * @param account 钱包账户
     */
    public void setAccount(Long account) {
        this.account = account;
    }

    /**
     * 获取账户总余额
     *
     * @return money - 账户总余额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置账户总余额
     *
     * @param money 账户总余额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取可用余额
     *
     * @return avalible_money - 可用余额
     */
    public BigDecimal getAvalibleMoney() {
        return avalibleMoney;
    }

    /**
     * 设置可用余额
     *
     * @param avalibleMoney 可用余额
     */
    public void setAvalibleMoney(BigDecimal avalibleMoney) {
        this.avalibleMoney = avalibleMoney;
    }

    /**
     * 获取冻结余额
     *
     * @return freeze_money - 冻结余额
     */
    public BigDecimal getFreezeMoney() {
        return freezeMoney;
    }

    /**
     * 设置冻结余额
     *
     * @param freezeMoney 冻结余额
     */
    public void setFreezeMoney(BigDecimal freezeMoney) {
        this.freezeMoney = freezeMoney;
    }

    /**
     * 获取状态 1 正常 2 异常
     *
     * @return valid - 状态 1 正常 2 异常
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * 设置状态 1 正常 2 异常
     *
     * @param valid 状态 1 正常 2 异常
     */
    public void setValid(Byte valid) {
        this.valid = valid;
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
}