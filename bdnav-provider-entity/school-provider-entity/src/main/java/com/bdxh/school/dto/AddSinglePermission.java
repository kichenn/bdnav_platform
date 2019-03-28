package com.bdxh.school.dto;

import com.bdxh.school.enums.SingleUserTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Description: 新增门禁单信息
 * @Author: Kang
 * @Date: 2019/3/27 17:55
 */
@Data
public class AddSinglePermission {

    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @NotNull(message = "用户类型不能为空")
    @ApiModelProperty("用户类型 1 学生 2 老师 3 家长")
    private SingleUserTypeEnum singleUserTypeEnum;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("卡号")
    private String cardNumber;

    @ApiModelProperty("设备id")
    private Long deviceId;

    @ApiModelProperty("设备编码")
    private String deviceCode;

    @ApiModelProperty("允许开始时间")
    private Date accessBegin;

    @ApiModelProperty("允许结束时间")
    private Date accessEnd;

    @ApiModelProperty("周时间段")
    private String accessDayMark;

    @ApiModelProperty("日时间段")
    private String accessTimeMark;

    @ApiModelProperty("操作人（前端不需要传递，后端自己获取）")
    private Long operator;

    @ApiModelProperty("操作人姓名（前端不需要传递，后端自己获取）")
    private String operatorName;

    @ApiModelProperty("备注")
    private String remark;


}