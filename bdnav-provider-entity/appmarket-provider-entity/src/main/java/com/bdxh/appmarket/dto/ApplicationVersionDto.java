package com.bdxh.appmarket.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class ApplicationVersionDto {

    @ApiModelProperty("应用id")
    private Long id;

    /**
     * 学校id
     */
    @ApiModelProperty("应用id")
    private Long schoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty("应用id")
    private String schoolCode;

    /**
     * 平台 1 andriod 2 ios
     */
    @ApiModelProperty("应用id")
    private Byte platform;

    /**
     * 分类id
     */
    @ApiModelProperty("应用id")
    private Long categoryId;

    /**
     * 应用名称
     */
    @ApiModelProperty("应用id")
    private String appName;

    /**
     * 应用包名
     */
    @ApiModelProperty("应用id")
    private String appPackage;

    /**
     * 应用图标地址
     */
    @ApiModelProperty("应用id")
    private String iconUrl;

    /**
     * 应用图标名称
     */
    @ApiModelProperty("应用id")
    private String iconName;

    /**
     * 应用版本
     */
    @ApiModelProperty("应用id")
    private String appVersion;

    /**
     * 应用描述
     */
    @ApiModelProperty("应用id")
    private String appDesc;

    /**
     *  状态 1 上架 2 下架
     */
    @ApiModelProperty("应用id")
    private Byte status;

    /**
     *  是否预置 1 是 2 否
     */
    @ApiModelProperty("应用id")
    private Byte preset;


    /**
     * 创建时间
     */
    @ApiModelProperty("应用id")
    private Date createDate;

    /**
     * 修改时间
     */
    @ApiModelProperty("应用id")
    private Date updateDate;

    /**
     * 操作人
     */
    @ApiModelProperty("应用id")
    private Long operator;

    /**
     * 操作姓名
     */
    @ApiModelProperty("应用id")
    private String operatorName;

    /**
     * 备注
     */
    @ApiModelProperty("应用id")
    private String remark;

    /**
     * 应用id
     */
    @Column(name = "app_id")
    private Long appId;

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
     * 文件服务器名称
     */
    @Column(name = "apk_url_name")
    private String apkUrlName;

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



}
