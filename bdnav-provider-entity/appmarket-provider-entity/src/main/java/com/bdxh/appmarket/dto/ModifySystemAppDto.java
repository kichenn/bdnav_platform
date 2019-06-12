package com.bdxh.appmarket.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ModifySystemAppDto {


    @ApiModelProperty("主键标识")
    private Long id;

    /**
     * 系统应用名称
     */
    @ApiModelProperty("系统应用名称")
    private String systemApp;

    /**
     * 系统应用包名
     */
    @ApiModelProperty("系统应用包名")
    private String systemPackage;

    /**
     * 应用文件名称
     */
    @ApiModelProperty("应用文件名称")
    private String systemApkName;

    /**
     * 应用下载地址
     */
    @ApiModelProperty("应用下载地址")
    private String systemApkUrl;

    /**
     * 图片名称
     */
    @ApiModelProperty("图片名称")
    private String iconName;

    /**
     * 图片地址
     */
    @ApiModelProperty("图片地址")
    private String iconUrl;

    /**
     * 应用版本号
     */
    @ApiModelProperty("应用版本号")
    private String appVersion;

    /**
     * 应用简介
     */
    @ApiModelProperty("应用简介")
    private String appDesc;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date updateDate;

    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operator;

    /**
     * 操作人名称
     */
    @ApiModelProperty("操作人名称")
    private String operatorName;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;


}
