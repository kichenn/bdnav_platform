package com.bdxh.appmarket.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_app")
public class App implements Serializable {

    private static final long serialVersionUID = 474899716964951260L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * 平台 1 andriod 2 ios
     */
    private Byte platform;

    /**
     * 分类id
     */
    @Column(name = "category_id")
    private Long categoryId;

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
     * 应用版本
     */
    @Column(name = "app_version")
    private String appVersion;

    /**
     * 应用描述
     */
    @Column(name = "app_desc")
    private String appDesc;

    /**
     * 文件名称
     */
    @Column(name = "apk_name")
    private String apkName;

    /**
     * 文件下载路径
     */
    @Column(name = "apk_url")
    private String apkUrl;

    /**
     * 文件服务器名称
     */
    @Column(name = "apk_url_name")
    private String apkUrlName;

    /**
     * 文件大小
     */
    @Column(name = "apk_size")
    private Integer apkSize;

    /**
     *  状态 1 上架 2 下架
     */
    private Byte status;

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
     * 操作人
     */
    private Long operator;

    /**
     * 操作姓名
     */
    @Column(name = "operator_name")
    private String operatorName;

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
     * 获取学校id
     *
     * @return school_id - 学校id
     */
    public Long getSchoolId() {
        return schoolId;
    }

    /**
     * 设置学校id
     *
     * @param schoolId 学校id
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
     * 获取分类id
     *
     * @return category_id - 分类id
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * 设置分类id
     *
     * @param categoryId 分类id
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
     * 获取应用版本
     *
     * @return app_version - 应用版本
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * 设置应用版本
     *
     * @param appVersion 应用版本
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion == null ? null : appVersion.trim();
    }

    /**
     * 获取应用描述
     *
     * @return app_desc - 应用描述
     */
    public String getAppDesc() {
        return appDesc;
    }

    /**
     * 设置应用描述
     *
     * @param appDesc 应用描述
     */
    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc == null ? null : appDesc.trim();
    }

    /**
     * 获取文件名称
     *
     * @return apk_name - 文件名称
     */
    public String getApkName() {
        return apkName;
    }

    /**
     * 设置文件名称
     *
     * @param apkName 文件名称
     */
    public void setApkName(String apkName) {
        this.apkName = apkName == null ? null : apkName.trim();
    }

    /**
     * 获取文件下载路径
     *
     * @return apk_url - 文件下载路径
     */
    public String getApkUrl() {
        return apkUrl;
    }

    /**
     * 设置文件下载路径
     *
     * @param apkUrl 文件下载路径
     */
    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl == null ? null : apkUrl.trim();
    }

    /**
     * 获取文件服务器名称
     *
     * @return apk_url_name - 文件服务器名称
     */
    public String getApkUrlName() {
        return apkUrlName;
    }

    /**
     * 设置文件服务器名称
     *
     * @param apkUrlName 文件服务器名称
     */
    public void setApkUrlName(String apkUrlName) {
        this.apkUrlName = apkUrlName == null ? null : apkUrlName.trim();
    }

    /**
     * 获取文件大小
     *
     * @return apk_size - 文件大小
     */
    public Integer getApkSize() {
        return apkSize;
    }

    /**
     * 设置文件大小
     *
     * @param apkSize 文件大小
     */
    public void setApkSize(Integer apkSize) {
        this.apkSize = apkSize;
    }

    /**
     * 获取 状态 1 上架 2 下架
     *
     * @return status -  状态 1 上架 2 下架
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置 状态 1 上架 2 下架
     *
     * @param status  状态 1 上架 2 下架
     */
    public void setStatus(Byte status) {
        this.status = status;
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
     * 获取操作人
     *
     * @return operator - 操作人
     */
    public Long getOperator() {
        return operator;
    }

    /**
     * 设置操作人
     *
     * @param operator 操作人
     */
    public void setOperator(Long operator) {
        this.operator = operator;
    }

    /**
     * 获取操作姓名
     *
     * @return operator_name - 操作姓名
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 设置操作姓名
     *
     * @param operatorName 操作姓名
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
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