package com.bdxh.school.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class QuerySchoolStrategy extends Query {


    @ApiModelProperty("主键")
    private Long Id;


    @ApiModelProperty("策略名称")
    private String PolicyName;


    @ApiModelProperty("学校ID")
    private Long SchoolId;


    @ApiModelProperty("学校编码")
    private String SchoolCode;


    @ApiModelProperty("学校名称")
    private String SchoolName;


    @ApiModelProperty("部门ID")
    private Long GroupId;


    @ApiModelProperty("是否递归权限 1 是 2 否")
    private Integer RecursionPermission;


    @ApiModelProperty("递归权限ids")
    private String RecursionPermissionIds;


    @ApiModelProperty("模式主键")
    private Long ModelId;


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

/*    @ApiModelProperty("学校code")
    private String getSchoolCode;

    @ApiModelProperty("学校名称")
    private String getSchoolName;*/

    @ApiModelProperty("部门名称")
    private String GroupName;

    @ApiModelProperty("模式名称")
    private String ModelName;


}
