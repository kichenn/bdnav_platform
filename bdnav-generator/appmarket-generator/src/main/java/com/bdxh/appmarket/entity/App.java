package com.bdxh.appmarket.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_app")
public class App {
    /**
     * 主键
     */
    @Id
    private Long id;

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
     * 应用图标
     */
    @Column(name = "app_icon")
    private String appIcon;

    /**
     * 应用描述
     */
    @Column(name = "app_desc")
    private String appDesc;

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
     * 获取应用图标
     *
     * @return app_icon - 应用图标
     */
    public String getAppIcon() {
        return appIcon;
    }

    /**
     * 设置应用图标
     *
     * @param appIcon 应用图标
     */
    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon == null ? null : appIcon.trim();
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