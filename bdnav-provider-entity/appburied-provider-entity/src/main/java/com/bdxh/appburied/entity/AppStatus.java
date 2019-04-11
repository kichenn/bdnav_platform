package com.bdxh.appburied.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_app_status")
public class AppStatus implements Serializable {

    private static final long serialVersionUID = -4069075052490137532L;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 平台 1 andriod 2 ios
     */
    private Byte platform;

    /**
     * 账户id
     */
    @Column(name = "account_id")
    private Long accountId;

    /**
     * 学校编码
     */
    @Column(name = "school_code")
    private String schoolCode;

    /**
     * 用户卡号
     */
    @Column(name = "card_number")
    private String cardNumber;

    /**
     * 应用包名标识
     */
    @Column(name = "app_package")
    private String appPackage;

    /**
     * 应用状态 1 正常 2 锁定
     */
    @Column(name = "app_status")
    private Byte appStatus;

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
     * 获取平台 1 andriod 2 ios
     *
     * @return platform - 平台 1 andriod 2 ios
     */
    public Byte getPlatform() {
        return platform;
    }

    /**
     * 设置平台 1 andriod 2 ios
     *
     * @param platform 平台 1 andriod 2 ios
     */
    public void setPlatform(Byte platform) {
        this.platform = platform;
    }

    /**
     * 获取账户id
     *
     * @return account_id - 账户id
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * 设置账户id
     *
     * @param accountId 账户id
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取学校编码
     *
     * @return school_code - 学校编码
     */
    public String getSchoolCode() {
        return schoolCode;
    }

    /**
     * 设置学校编码
     *
     * @param schoolCode 学校编码
     */
    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode == null ? null : schoolCode.trim();
    }

    /**
     * 获取用户卡号
     *
     * @return card_number - 用户卡号
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * 设置用户卡号
     *
     * @param cardNumber 用户卡号
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber == null ? null : cardNumber.trim();
    }

    /**
     * 获取应用包名标识
     *
     * @return app_package - 应用包名标识
     */
    public String getAppPackage() {
        return appPackage;
    }

    /**
     * 设置应用包名标识
     *
     * @param appPackage 应用包名标识
     */
    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage == null ? null : appPackage.trim();
    }

    /**
     * 获取应用状态 1 正常 2 锁定
     *
     * @return app_status - 应用状态 1 正常 2 锁定
     */
    public Byte getAppStatus() {
        return appStatus;
    }

    /**
     * 设置应用状态 1 正常 2 锁定
     *
     * @param appStatus 应用状态 1 正常 2 锁定
     */
    public void setAppStatus(Byte appStatus) {
        this.appStatus = appStatus;
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