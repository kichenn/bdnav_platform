package com.bdxh.appmarket.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class appDownloadlinkVo {

    /**
     * 应用名称
     */
    @ApiModelProperty("应用名称")
    private String appName;

    /**
     * 应用包名
     */
    @ApiModelProperty("应用包名")
    private String appPackage;

    /**
     * 应用图标地址
     */
    @ApiModelProperty("图片图标地址")
    private String iconUrl;


    /**
     * apk文件下载地址
     */
    @ApiModelProperty("apk文件下载地址")
    private String apkUrl;




}
