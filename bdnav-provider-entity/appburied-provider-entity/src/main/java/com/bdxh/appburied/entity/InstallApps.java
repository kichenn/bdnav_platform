package com.bdxh.appburied.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_install_apps")
public class InstallApps implements Serializable {

    private static final long serialVersionUID = -1661246205053216281L;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 平台 1 android 2 ios
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
     * 学校名称
     */
    @Column(name = "school_name")
    private String schoolName;

    /**
     * 用户卡号
     */
    @Column(name = "card_number")
    private String cardNumber;

    /**
     * 用户姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 应用名称
     */
    @Column(name = "app_name")
    private String appName;

    /**
     * 应用包名
     */
    @Column(name = "app_package")
    private String appPackage;

    /**
     * 应用图标地址
     */
    @Column(name = "icon_url")
    private String iconUrl;

    /**
     * 应用图标名称
     */
    @Column(name = "icon_name")
    private String iconName;

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
     * 获取平台 1 android 2 ios
     *
     * @return platform - 平台 1 android 2 ios
     */
    public Byte getPlatform() {
        return platform;
    }

    /**
     * 设置平台 1 android 2 ios
     *
     * @param platform 平台 1 android 2 ios
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
     * 获取学校名称
     *
     * @return school_name - 学校名称
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * 设置学校名称
     *
     * @param schoolName 学校名称
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
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
     * 获取用户姓名
     *
     * @return user_name - 用户姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户姓名
     *
     * @param userName 用户姓名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取应用名称
     *
     * @return app_name - 应用名称
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置应用名称
     *
     * @param appName 应用名称
     */
    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    /**
     * 获取应用包名
     *
     * @return app_package - 应用包名
     */
    public String getAppPackage() {
        return appPackage;
    }

    /**
     * 设置应用包名
     *
     * @param appPackage 应用包名
     */
    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage == null ? null : appPackage.trim();
    }

    /**
     * 获取应用图标地址
     *
     * @return icon_url - 应用图标地址
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * 设置应用图标地址
     *
     * @param iconUrl 应用图标地址
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl == null ? null : iconUrl.trim();
    }

    /**
     * 获取应用图标名称
     *
     * @return icon_name - 应用图标名称
     */
    public String getIconName() {
        return iconName;
    }

    /**
     * 设置应用图标名称
     *
     * @param iconName 应用图标名称
     */
    public void setIconName(String iconName) {
        this.iconName = iconName == null ? null : iconName.trim();
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