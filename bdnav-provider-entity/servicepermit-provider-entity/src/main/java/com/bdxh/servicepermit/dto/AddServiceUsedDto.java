package com.bdxh.servicepermit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AddServiceUsedDto {

    @ApiModelProperty("服务id")
    private Long ServiceId;


    @ApiModelProperty("订单主键")
    private Long OrderNo;

    @ApiModelProperty("产品主键")
    private Long ProductId;

    @ApiModelProperty("学校主键")
    private Long SchoolId;

    @ApiModelProperty("学校编码")
    private String SchoolCode;

    @ApiModelProperty("学校名称")
    private String SchoolName;


    @ApiModelProperty("家长主键")
    private Long FamilyId;


    @ApiModelProperty("家长号")
    private Long CardNumber;


    @ApiModelProperty("家长姓名")
    private String FamilyName;


    @ApiModelProperty("开始使用时间")
    private Date StartTime;


    @ApiModelProperty("结束时间")
    private Date EndTime;

    @ApiModelProperty("状态 1 正常使用 2已过期")
    private Integer Status;


    @ApiModelProperty("类型 1是试用  2是正式使用")
    private Integer Type;


    @ApiModelProperty("创建日期")
    private Date CreateDate;


    @ApiModelProperty("修改日期")
    private Date UpdateDate;


    @ApiModelProperty("操作人")
    private Long Operator;

    @ApiModelProperty("操作人姓名")
    private String OperatorName;

    @ApiModelProperty("备注")
    private String Remark;

}
