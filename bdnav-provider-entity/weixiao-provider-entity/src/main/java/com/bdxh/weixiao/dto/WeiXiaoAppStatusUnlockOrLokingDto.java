package com.bdxh.weixiao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-21 11:52
 **/
@Data
public class WeiXiaoAppStatusUnlockOrLokingDto {

    /**
     * 平台 1 andriod 2 ios
     */
    @NotNull(message = "平台标识不能为空")
    @ApiModelProperty(value = "平台 1 andriod 2 ios",name = "平台 1 andriod 2 ios")
    private Byte platform;

    /**
     * 账户id
     */
    @NotNull(message = "账户id不能为空")
    @ApiModelProperty(value ="账户id",name = "账户id")
    private Long accountId;

    /**
     * 学校ID
     */
    @NotNull(message = "学校ID不能为空")
    @ApiModelProperty(value ="学校ID",name = "学校ID")
    private Long schoolId;

    /**
     * 学校编码
     */
    @NotNull(message = "学校编码不能为空")
    @ApiModelProperty(value ="学校编码",name = "学校编码")
    private String schoolCode;

    /**
     * 学校名称
     */
    @NotNull(message = "学校名称不能为空")
    @ApiModelProperty(value ="学校名称",name = "学校名称")
    private String schoolName;

    /**
     * 学生名称
     */
    @NotNull(message = "学生名称不能为空")
    @ApiModelProperty(value ="学生名称",name = "学生名称")
    private String userName;


    /**
     * 学生卡号
     */
    @NotNull(message = "学生卡号不能为空")
    @ApiModelProperty(value ="学生卡号",name = "学生卡号")
    private String cardNumber;

    /**
     * 应用包名标识
     */
    @NotNull(message = "应用包名标识不能为空")
    @ApiModelProperty(value ="应用包名标识",name = "应用包名标识")
    private String appPackage;

    /**
     * 应用状态 1 正常 2 锁定
     */
    @NotNull(message = "应用状态不能为空")
    @ApiModelProperty(value ="应用状态 1 正常 2 锁定",name = "应用状态 1 正常 2 锁定")
    private Byte appStatus;

    /**
     * 客户端Ids
     */
    @ApiModelProperty(value ="客户端Ids",name = "客户端Ids")
    private List<String> clientId;
}