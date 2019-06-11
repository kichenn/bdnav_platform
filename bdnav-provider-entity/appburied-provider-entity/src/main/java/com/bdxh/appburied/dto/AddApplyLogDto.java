package com.bdxh.appburied.dto;

import com.bdxh.appburied.enums.ApplyLogModelEnum;
import com.bdxh.appburied.enums.ApplyLogOperatorStatusEnum;
import com.bdxh.appburied.enums.InstallAppsPlatformEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * @Description: 增加APP应用上报日志DTO
 * @Author: Kang
 * @Date: 2019/4/12 15:38
 */
@Data
public class AddApplyLogDto {

    @NotNull(message = "应用平台不能为空")
    @ApiModelProperty(" *平台 1 android 2 ios")
    private InstallAppsPlatformEnum installAppsPlatformEnum;

    @ApiModelProperty("*账户id")
    private Long accountId;

    @ApiModelProperty("*推送标识")
    private String clientId;

    @NotNull(message = "APP应用日志模式不能为空")
    @ApiModelProperty("模式 1 单个应用解锁 2 全部解锁 ")
    private ApplyLogModelEnum applyLogModelEnum;


    @ApiModelProperty("*学校ID")
    private Long schoolId;

    @NotEmpty(message = "学校编码不能为空")
    @ApiModelProperty("*学校编码")
    private String schoolCode;

    @ApiModelProperty("*学校名称")
    private String schoolName;

    @NotEmpty(message = "用户学号不能为空")
    @ApiModelProperty("*用户学号")
    private String cardNumber;

    /**
     * 是否读取：1：已读、2：未读
     */
    @ApiModelProperty("是否读取：1：已读、2：未读")
    private Byte isRead =2;

    @ApiModelProperty("*申请理由")
    private String reason;

    @ApiModelProperty("*用户姓名")
    private String userName;

    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("应用包名")
    private String appPackage;

    @ApiModelProperty("操作人编码(审核人)")
    private String operatorCode;

    @ApiModelProperty("操作人姓名(审核人)")
    private String operatorName;


    @ApiModelProperty("操作状态 1 待审核 2 审核拒绝 3 审核通过(新增时只能为之待审核) ")
    private ApplyLogOperatorStatusEnum applyLogOperatorStatusEnum=ApplyLogOperatorStatusEnum.WAIT;

    @ApiModelProperty("开始时间")
    private Date startDate;

    @ApiModelProperty("结束时间")
    private Date endDate;

    @ApiModelProperty("备注")
    private String remark;


}