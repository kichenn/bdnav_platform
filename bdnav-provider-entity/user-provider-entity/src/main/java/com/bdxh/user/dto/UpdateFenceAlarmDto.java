package com.bdxh.user.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-17 18:59
 **/
@Data
public class UpdateFenceAlarmDto implements Serializable {
    private static final long serialVersionUID = -4082066846146017279L;
    /**
     * id
     */
    @ApiModelProperty("id")
    @JsonSerialize(using= ToStringSerializer.class)
    private String id;
    /**
     * 围栏ID
     */
    @ApiModelProperty("围栏ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Integer fenceId;
    /**
     * 围栏名称
     */
    @ApiModelProperty("围栏名称")
    private String fenceName;
    /**
     * 消息类型
     */
    @ApiModelProperty("消息类型")
    private Byte type;
    /**
     * 监控对象
     */
    @ApiModelProperty("监控对象")
    private String monitoredPerson;
    /**
     * 出入动作
     */
    @ApiModelProperty("出入动作")
    private String action;
    /**
     * 报警轨迹点
     */
    @ApiModelProperty("报警轨迹点")
    private String alarmPoint;
    /**
     * 报警前轨迹点
     */
    @ApiModelProperty("报警前轨迹点")
    private String prePoint;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date updateDate;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 学校Code
     */
    @ApiModelProperty("学校Code")
    private String schoolCode;

    /**
     * 学生学号
     */
    @ApiModelProperty("学生学号")
    private String cardNumber;
}