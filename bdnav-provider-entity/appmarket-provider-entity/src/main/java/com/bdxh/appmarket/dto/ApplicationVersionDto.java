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
  /*  @ApiModelProperty("学校id")
    private Long schoolId;*/

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 平台 1 andriod 2 ios
     */
/*    @ApiModelProperty("平台 1 andriod 2 ios")
    private Byte platform;*/

    /**
     * 分类id
     */
/*    @ApiModelProperty("分类id")
    private Long categoryId;*/

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
     * 应用图标名称
     */
    @ApiModelProperty("图标名称")
    private String iconName;

    /**
     * 应用描述
     */
  /*  @ApiModelProperty("应用描述")
    private String appDesc;
*/
    /**
     *  状态 1 上架 2 下架
     */
    @ApiModelProperty("状态 1 上架 2 下架")
    private Byte status;

    /**
     *  是否预置 1 是 2 否
     */
  /*  @ApiModelProperty("是否预置 1 是 2 否")
    private Byte preset;*/


    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;


    /**
     * apk文件名称
     */
    @ApiModelProperty("apk文件名称")
    private String apkName;

    /**
     * apk文件下载地址
     */
    @ApiModelProperty("apk文件下载地址")
    private String apkUrl;

    /**
     * 文件服务器名称
     */
    @ApiModelProperty("文件服务器名称")
    private String apkUrlName;



}
