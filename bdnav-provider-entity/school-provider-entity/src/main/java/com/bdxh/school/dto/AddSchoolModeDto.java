package com.bdxh.school.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class AddSchoolModeDto {

    @ApiModelProperty("学校ID")
    private Long SchoolId;


    @ApiModelProperty("学校CODE")
    private String SchoolCode;

    @ApiModelProperty("模式名称")
    private String ModelName;

    @ApiModelProperty("适用平台 1安卓  2苹果")
    private String Platform;

    @ApiModelProperty("模式优先级")
    private Integer Priority;

    @ApiModelProperty("可用的应用")
    private String UsableApp;


    @ApiModelProperty("可用的设备")
    private String UsableDevice;


    @ApiModelProperty("操作人")
    private Long Operator;


    @ApiModelProperty("操作人姓名")
    private String OperatorName;


    @ApiModelProperty("备注")
    private String Remark;

}
