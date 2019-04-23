package com.bdxh.appburied.dto;

import com.bdxh.appburied.enums.AppStatusEnum;
import com.bdxh.appburied.enums.InstallAppsPlatformEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Description: 增加app状态DTO
 * @Author: Kang
 * @Date: 2019/4/12 11:59
 */
@Data
public class AddAppStatusDto {

    @NotNull(message = "应用平台不能为空")
    @ApiModelProperty("平台 1 android 2 ios")
    private InstallAppsPlatformEnum installAppsPlatformEnum;

    @ApiModelProperty("账户id")
    private Long accountId;

    @NotEmpty(message = "学校ID不能为空")
    @ApiModelProperty("学校ID")
    private Long schoolId;

    @NotEmpty(message = "学校编码不能为空")
    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("学校名称")
    private String schoolName;


    @ApiModelProperty("学生名称")
    private String userName;


    @NotEmpty(message = "学校编码不能为空")
    @ApiModelProperty("用户学号")
    private String cardNumber;

    @ApiModelProperty("应用包名标识")
    private String appPackage;

    @NotNull(message = "应用状态不能为空")
    @ApiModelProperty("应用状态 1 正常 2 锁定")
    private AppStatusEnum appStatusEnum;

//    @ApiModelProperty("创建时间")
//    private Date createDate;
//
//    @ApiModelProperty("修改时间")
//    private Date updateDate;

}