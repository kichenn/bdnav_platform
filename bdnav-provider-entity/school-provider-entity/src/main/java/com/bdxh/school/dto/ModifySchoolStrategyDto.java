package com.bdxh.school.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ModifySchoolStrategyDto {

    @ApiModelProperty("主键")
    private Long Id;


    @ApiModelProperty("策略名称")
    private String Name;


    @ApiModelProperty("学校ID")
    private Long SchoolId;


    @ApiModelProperty("学校编码")
    private String SchoolCode;


    @ApiModelProperty("部门ID")
    private Long GroupId;


    @ApiModelProperty("是否递归权限 1 是 2 否")
    private Integer RecursionPermission;


    @ApiModelProperty("递归权限ids")
    private String RecursionPermissionIds;


    @ApiModelProperty("适用平台 1安卓  2苹果")
    private String Platform;


    @ApiModelProperty("模式主键")
    private Long ModeId;


    @ApiModelProperty("开始日期")
    private Date StartDate;


    @ApiModelProperty("结束日期")
    private Date EndDate;


    @ApiModelProperty("周时间段")
    private String DayMark;


    @ApiModelProperty("日时间段")
    private String TimeMark;


    @ApiModelProperty("排除日期")
    private String ExclusionDays;


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
