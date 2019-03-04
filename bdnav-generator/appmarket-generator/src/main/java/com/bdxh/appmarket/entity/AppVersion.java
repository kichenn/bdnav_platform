package com.bdxh.appmarket.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_app_version")
public class AppVersion {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 应用id
     */
    @Column(name = "app_id")
    private Long appId;

    /**
     * 应用版本号
     */
    @Column(name = "app_code")
    private String appCode;

    /**
     * apk文件名称
     */
    @Column(name = "apk_name")
    private String apkName;

    /**
     * apk文件下载地址
     */
    @Column(name = "apk_url")
    private String apkUrl;

    /**
     * apk文件大小
     */
    @Column(name = "apk_size")
    private Long apkSize;

    /**
     * apk描述
     */
    @Column(name = "apk_desc")
    private String apkDesc;

    /**
     * apk状态 1 上架 2 下架
     */
    @Column(name = "apk_status")
    private Byte apkStatus;

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
     * 操作人姓名
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
     * 获取应用id
     *
     * @return app_id - 应用id
     */
    public Long getAppId() {
        return appId;
    }

    /**
     * 设置应用id
     *
     * @param appId 应用id
     */
    public void setAppId(Long appId) {
        this.appId = appId;
    }

    /**
     * 获取应用版本号
     *
     * @return app_code - 应用版本号
     */
    public String getAppCode() {
        return appCode;
    }

    /**
     * 设置应用版本号
     *
     * @param appCode 应用版本号
     */
    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    /**
     * 获取apk文件名称
     *
     * @return apk_name - apk文件名称
     */
    public String getApkName() {
        return apkName;
    }

    /**
     * 设置apk文件名称
     *
     * @param apkName apk文件名称
     */
    public void setApkName(String apkName) {
        this.apkName = apkName == null ? null : apkName.trim();
    }

    /**
     * 获取apk文件下载地址
     *
     * @return apk_url - apk文件下载地址
     */
    public String getApkUrl() {
        return apkUrl;
    }

    /**
     * 设置apk文件下载地址
     *
     * @param apkUrl apk文件下载地址
     */
    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl == null ? null : apkUrl.trim();
    }

    /**
     * 获取apk文件大小
     *
     * @return apk_size - apk文件大小
     */
    public Long getApkSize() {
        return apkSize;
    }

    /**
     * 设置apk文件大小
     *
     * @param apkSize apk文件大小
     */
    public void setApkSize(Long apkSize) {
        this.apkSize = apkSize;
    }

    /**
     * 获取apk描述
     *
     * @return apk_desc - apk描述
     */
    public String getApkDesc() {
        return apkDesc;
    }

    /**
     * 设置apk描述
     *
     * @param apkDesc apk描述
     */
    public void setApkDesc(String apkDesc) {
        this.apkDesc = apkDesc == null ? null : apkDesc.trim();
    }

    /**
     * 获取apk状态 1 上架 2 下架
     *
     * @return apk_status - apk状态 1 上架 2 下架
     */
    public Byte getApkStatus() {
        return apkStatus;
    }

    /**
     * 设置apk状态 1 上架 2 下架
     *
     * @param apkStatus apk状态 1 上架 2 下架
     */
    public void setApkStatus(Byte apkStatus) {
        this.apkStatus = apkStatus;
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