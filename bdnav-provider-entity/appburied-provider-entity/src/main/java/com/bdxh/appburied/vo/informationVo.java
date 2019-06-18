package com.bdxh.appburied.vo;

import com.bdxh.appburied.enums.ApplyLogOperatorStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.Date;


@Data
public class informationVo {

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

    @ApiModelProperty("操作状态 1 待审核 2 审核拒绝 3 审核通过(新增时只能为之待审核) ")
    private ApplyLogOperatorStatusEnum applyLogOperatorStatusEnum;

    @ApiModelProperty("开始时间")
    private Date startDate;

    @ApiModelProperty("结束时间")
    private Date endDate;


    @ApiModelProperty("申请畅玩时长")
    private Integer duration;


    @ApiModelProperty("审核意见")
    private String review;


    @ApiModelProperty("申请理由")
    private String reason;


    @ApiModelProperty("是否读取：1：已读、2：未读")
    private Byte isRead ;

}
