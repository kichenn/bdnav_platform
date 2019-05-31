package com.bdxh.appmarket.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 增加应用dto
 * @author: xuyuan
 * @create: 2019-03-05 11:31
 **/
@Data
@ApiModel("增加应用dto")
public class AddAppDto implements Serializable {

    private static final long serialVersionUID = 6583889193391448618L;

    /**
     * 学校id
     */
    @ApiModelProperty("学校id")
    private Long schoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 平台 1 andriod 2 ios
     */
    @NotNull(message = "平台不能为空")
    @ApiModelProperty("平台")
    private Byte platform;

    /**
     * 分类id
     */
    @NotNull(message = "分类id不能为空")
    @ApiModelProperty("分类id")
    private Long categoryId;

    /**
     * 应用名称
     */
    @NotEmpty(message = "应用名称不能为空")
    @ApiModelProperty("应用名称")
    private String appName;

    /**
     * 应用包名
     */
    @NotEmpty(message = "应用包名不能为空")
    @ApiModelProperty("应用包名")
    private String appPackage;

    /**
     * 应用图标地址
     */
    @NotEmpty(message = "应用图标地址不能为空")
    @ApiModelProperty("应用图标地址")
    private String iconUrl;

    /**
     * 应用图标名称
     */
    @NotEmpty(message = "应用图标名称不能为空")
    @ApiModelProperty("应用图标名称")
    private String iconName;

    /**
     * 应用版本
     */
    @NotEmpty(message = "应用版本不能为空")
    @ApiModelProperty("应用版本")
    private String appVersion;

    /**
     * 应用描述
     */
    @ApiModelProperty("应用描述")
    private String appDesc;

    /**
     * 文件名称
     */
    @NotEmpty(message = "文件名称不能为空")
    @ApiModelProperty("文件名称")
    private String apkName;

    /**
     * 文件下载路径
     */
    @NotEmpty(message = "文件下载路径不能为空")
    @ApiModelProperty("文件下载路径")
    private String apkUrl;

    /**
     * 文件服务器名称
     */
    @NotEmpty(message = "文件服务器名称不能为空")
    @ApiModelProperty("文件服务器名称")
    private String apkUrlName;

    /**
     * 文件大小
     */
    @NotNull(message = "文件大小不能为空")
    @ApiModelProperty("文件大小")
    private Integer apkSize;

    /**
     *  状态 1 上架 2 下架
     */
    @ApiModelProperty("状态 1 上架 2 下架")
    private Byte status = 2;

    /**
     *  是否预置 1 是 2 否
     */
    @ApiModelProperty("是否预置 1 是 2 否")
    private Byte preset;

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
     * 操作姓名
     */
    @ApiModelProperty("操作姓名")
    private String operatorName;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 应用图片列表
     */
    @Valid
    @ApiModelProperty("应用图片列表")
    List<AddAppImageDto> addImageDtos;

}
