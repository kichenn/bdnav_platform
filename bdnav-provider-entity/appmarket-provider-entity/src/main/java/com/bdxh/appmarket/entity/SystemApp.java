package com.bdxh.appmarket.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统管控应用实体类
 */

@Data
@Table(name = "t_system_app")
public class SystemApp {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 系统应用名称
     */
    @Column(name = "system_app")
    private String systemApp;

    /**
     * 系统应用包名
     */
    @Column(name = "system_package")
    private String systemPackage;


    /**
     * 应用文件名称地址
     */
    @Column(name = "system_apk_name")
    private String systemApkName;

    /**
     * 应用下载地址
     */
    @Column(name = "system_apk_url")
    private String systemApkUrl;

    /**
     * 图片名称
     */
    @Column(name = "icon_name")
    private String iconName;

    /**
     * 图片地址
     */
    @Column(name = "icon_url")
    private String iconUrl;

    /**
     * 应用版本号
     */
    @Column(name = "app_version")
    private String appVersion;

    /**
     * 应用简介
     */
    @Column(name = "app_desc")
    private String appDesc;

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
    @Column(name = "operator")
    private Long operator;

    /**
     * 操作人名称
     */
    @Column(name = "operator_name")
    private String operatorName;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
}
