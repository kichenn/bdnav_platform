package com.bdxh.appburied.dto;

import com.bdxh.appburied.enums.ApplyLogModelEnum;
import com.bdxh.appburied.enums.ApplyLogOperatorStatusEnum;
import com.bdxh.appburied.enums.InstallAppsPlatformEnum;
import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;


/**
 * @Description: 查询APP应用上报日志DTO
 * @Author: Kang
 * @Date: 2019/4/12 16:36
 */
@Data
public class ApplyLogQueryDto extends Query {


    @ApiModelProperty("平台 1 android 2 ios")
    private InstallAppsPlatformEnum installAppsPlatformEnum;


    @ApiModelProperty("模式 1 单个应用解锁 2 全部解锁 ")
    private ApplyLogModelEnum applyLogModelEnum;


    @ApiModelProperty("学校ID")
    private Long schoolId;


    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("学校名称")
    private String schoolName;

    @ApiModelProperty("用户学号")
    private String cardNumber;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("操作人编码(审核人)")
    private String operatorCode;

    @ApiModelProperty("操作人姓名(审核人)")
    private String operatorName;


    @ApiModelProperty("操作状态 1 待审核 2 审核拒绝 3 审核通过(新增时只能为之待审核) ")
    private ApplyLogOperatorStatusEnum applyLogOperatorStatusEnum ;
}