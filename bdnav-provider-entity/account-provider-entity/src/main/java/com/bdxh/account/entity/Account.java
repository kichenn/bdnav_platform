package com.bdxh.account.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_account")
public class Account implements Serializable {

    private static final long serialVersionUID = 2302357156943235557L;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 学校id
     */
    @Column(name = "school_id")
    private Long schoolId;

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
     * 用户类型 1 学生 2 老师 3 家长
     */
    @Column(name = "user_type")
    private Byte userType;

    /**
     * 用户信息id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 用户姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户手机号
     */
    @Column(name = "user_phone")
    private String userPhone;

    /**
     * 用户学号
     */
    @Column(name = "card_number")
    private String cardNumber;

    /**
     * 用户登录名
     */
    @Column(name = "login_name")
    private String loginName;

    /**
     * 修改登录名 1 未修改 2 已修改
     */
    @Column(name = "login_name_update")
    private Byte loginNameUpdate;

    /**
     * 密码
     */
    @Column(name ="password")
    private String password;

    /**
     * 账户是否过期 1 正常 2 过期
     */
    @Column(name = "account_expired")
    private Byte accountExpired;

    /**
     * 账户是否锁定 1 正常 2 锁定
     */
    @Column(name = "account_locked")
    private Byte accountLocked;

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
     * 获取学校id
     *
     * @return id - 主键
     */
    public Long getSchoolId() {
        return schoolId;
    }

    /**
     * 设置学校id
     *
     * @param schoolId 主键
     */
    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
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
     * 获取用户类型 1 学生 2 老师 3 家长
     *
     * @return user_type - 用户类型 1 学生 2 老师 3 家长
     */
    public Byte getUserType() {
        return userType;
    }

    /**
     * 设置用户类型 1 学生 2 老师 3 家长
     *
     * @param userType 用户类型 1 学生 2 老师 3 家长
     */
    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    /**
     * 获取用户信息id
     *
     * @return user_id - 用户信息id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户信息id
     *
     * @param userId 用户信息id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
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
     * 获取用户手机号
     *
     * @return user_phone - 用户手机号
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * 设置用户手机号
     *
     * @param userPhone 用户手机号
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    /**
     * 获取用户学号
     *
     * @return card_number - 用户学号
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * 设置用户学号
     *
     * @param cardNumber 用户学号
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber == null ? null : cardNumber.trim();
    }

    /**
     * 获取用户登录名
     *
     * @return login_name - 用户登录名
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置用户登录名
     *
     * @param loginName 用户登录名
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    /**
     * 获取修改登录名 1 未修改 2 已修改
     *
     * @return login_name_update - 修改登录名 1 未修改 2 已修改
     */
    public Byte getLoginNameUpdate() {
        return loginNameUpdate;
    }

    /**
     * 设置修改登录名 1 未修改 2 已修改
     *
     * @param loginNameUpdate 修改登录名 1 未修改 2 已修改
     */
    public void setLoginNameUpdate(Byte loginNameUpdate) {
        this.loginNameUpdate = loginNameUpdate;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取账户是否过期 1 正常 2 过期
     *
     * @return account_expired - 账户是否过期 1 正常 2 过期
     */
    public Byte getAccountExpired() {
        return accountExpired;
    }

    /**
     * 设置账户是否过期 1 正常 2 过期
     *
     * @param accountExpired 账户是否过期 1 正常 2 过期
     */
    public void setAccountExpired(Byte accountExpired) {
        this.accountExpired = accountExpired;
    }

    /**
     * 获取账户是否锁定 1 正常 2 锁定
     *
     * @return account_locked - 账户是否锁定 1 正常 2 锁定
     */
    public Byte getAccountLocked() {
        return accountLocked;
    }

    /**
     * 设置账户是否锁定 1 正常 2 锁定
     *
     * @param accountLocked 账户是否锁定 1 正常 2 锁定
     */
    public void setAccountLocked(Byte accountLocked) {
        this.accountLocked = accountLocked;
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