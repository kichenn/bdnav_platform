package com.bdxh.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "sys_control_config")
public class ControlConfig {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 平台 1 andriod 2 ios
     */
    @Column(name = "platform")
    private Byte platform;

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
     * 类型 1是隐藏  2是黑盒
     */
    @Column(name = "app_type")
    private Byte appType;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 更新时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 操作人
     */
    @Column(name = "operator")
    private Long operator;

    /**
     * 操作人姓名
     */
    @Column(name = "operator_name")
    private String operatorName;



}
