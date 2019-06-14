package com.bdxh.servicepermit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
* @Description:   创建试用 服务许可 dto
* @Author: Kang
* @Date: 2019/6/13 15:55
*/
@Data
public class AddNoTrialServiceUserDto {

    @ApiModelProperty("学校主键(不需要传递，后端自己获取)")
    private Long schoolId;

    @ApiModelProperty("学校编码(不需要传递，后端自己获取)")
    private String schoolCode;

    @ApiModelProperty("学校名称(不需要传递，后端自己获取)")
    private String schoolName;


    @ApiModelProperty("家长主键(不需要传递，后端自己获取)")
    private Long familyId;


    @ApiModelProperty("家长号(不需要传递，后端自己获取)")
    private String cardNumber;


    @ApiModelProperty("家长姓名(不需要传递，后端自己获取)")
    private String familyName;

    @ApiModelProperty("学生卡号")
    private String studentNumber;

    @ApiModelProperty("学生姓名")
    private String studentName;

    @ApiModelProperty("操作人")
    private Long operator;

    @ApiModelProperty("操作人姓名")
    private String operatorName;

    @ApiModelProperty("备注")
    private String remark;

}
