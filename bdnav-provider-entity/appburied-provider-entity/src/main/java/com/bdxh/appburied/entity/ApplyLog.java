package com.bdxh.appburied.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_apply_log")
public class ApplyLog implements Serializable {

    private static final long serialVersionUID = 3075934844966343355L;

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
     * 推送标识
     */
    @Column(name = "push_sign")
    private String pushSign;

    /**
     * 模式 1 单个应用解锁 2 全部解锁 
     */
    private Byte model;

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
     * 操作人编码
     */
    @Column(name = "operator_code")
    private String operatorCode;

    /**
     * 操作人姓名
     */
    @Column(name = "operator_name")
    private String operatorName;

    /**
     * 操作状态 1 待审核 2审核拒绝 3 审核通过 
     */
    @Column(name = "operator_status")
    private Byte operatorStatus;

    /**
     * 开始时间
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * 结束时间
     */
    @Column(name = "end_date")
    private Date endDate;

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
     * 备注
     */
    private String remark;

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
     * 获取推送标识
     *
     * @return push_sign - 推送标识
     */
    public String getPushSign() {
        return pushSign;
    }

    /**
     * 设置推送标识
     *
     * @param pushSign 推送标识
     */
    public void setPushSign(String pushSign) {
        this.pushSign = pushSign == null ? null : pushSign.trim();
    }

    /**
     * 获取模式 1 单个应用解锁 2 全部解锁 
     *
     * @return model - 模式 1 单个应用解锁 2 全部解锁 
     */
    public Byte getModel() {
        return model;
    }

    /**
     * 设置模式 1 单个应用解锁 2 全部解锁 
     *
     * @param model 模式 1 单个应用解锁 2 全部解锁 
     */
    public void setModel(Byte model) {
        this.model = model;
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
     * 获取操作人编码
     *
     * @return operator_code - 操作人编码
     */
    public String getOperatorCode() {
        return operatorCode;
    }

    /**
     * 设置操作人编码
     *
     * @param operatorCode 操作人编码
     */
    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode == null ? null : operatorCode.trim();
    }

    /**
     * 获取操作人姓名
     *
     * @return operator_name - 操作人姓名
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 设置操作人姓名
     *
     * @param operatorName 操作人姓名
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    /**
     * 获取操作状态 1 待审核 2审核拒绝 3 审核通过 
     *
     * @return operator_status - 操作状态 1 待审核 2审核拒绝 3 审核通过 
     */
    public Byte getOperatorStatus() {
        return operatorStatus;
    }

    /**
     * 设置操作状态 1 待审核 2审核拒绝 3 审核通过 
     *
     * @param operatorStatus 操作状态 1 待审核 2审核拒绝 3 审核通过 
     */
    public void setOperatorStatus(Byte operatorStatus) {
        this.operatorStatus = operatorStatus;
    }

    /**
     * 获取开始时间
     *
     * @return start_date - 开始时间
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 设置开始时间
     *
     * @param startDate 开始时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取结束时间
     *
     * @return end_date - 结束时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置结束时间
     *
     * @param endDate 结束时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

}