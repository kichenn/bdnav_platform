package com.bdxh.school.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ModifySchoolModeDto {

    @ApiModelProperty("主键")
    private Long Id;


    @ApiModelProperty("学校ID")
    private Long SchoolId;


    @ApiModelProperty("学校CODE")
    private String SchoolCode;

    @ApiModelProperty("模式名称")
    private String Name;


    @ApiModelProperty("模式优先级")
    private Integer Priority;

    @ApiModelProperty("可用的应用")
    private String UsableApp;


    @ApiModelProperty("可用的设备")
    private String UsableDevice;


    @ApiModelProperty("创建时间")
    private Date CreateDate;


    @ApiModelProperty("修改时间")
    private Date UpdateDate;


    @ApiModelProperty("操作人")
    private Long Operator;


    @ApiModelProperty("操作人姓名")
    private String OperatorName;


    @ApiModelProperty("备注")
    private String Remark;

}
