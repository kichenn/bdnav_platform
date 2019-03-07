package com.bdxh.appmarket.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 增加应用版本dto
 * @author: xuyuan
 * @create: 2019-03-05 18:40
 **/
@Data
@ApiModel("增加应用版本dto")
public class AddAppVersionDto implements Serializable {

    private static final long serialVersionUID = 4578861875051593134L;

    /**
     * 应用id
     */
    @ApiModelProperty("应用id")
    private Long appId;

    /**
     * 应用版本号
     */
    @ApiModelProperty("应用版本号")
    private String appCode;

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
     * apk文件大小
     */
    @ApiModelProperty("apk文件大小")
    private Long apkSize;

    /**
     * apk描述
     */
    @ApiModelProperty("apk描述")
    private String apkDesc;

    /**
     * apk状态 1 上架 2 下架
     */
    @ApiModelProperty("apk状态 1 上架 2 下架")
    private Byte apkStatus = 2;

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
     * 操作人姓名
     */
    @ApiModelProperty("操作人姓名")
    private String operatorName;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}
