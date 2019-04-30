package com.bdxh.user.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-17 18:59
 **/
@Data
public class AddFenceAlarmDto implements Serializable {
    private static final long serialVersionUID = -4378044104909154928L;

    /**
     * 围栏ID
     */
    @ApiModelProperty("围栏ID")
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "围栏ID不能为空")
    private Integer fenceId;
    /**
     * 围栏名称
     */
    @ApiModelProperty("围栏名称")
    @NotEmpty(message = "围栏名称不能为空")
    private String fenceName;
    /**
     * 消息类型
     */
    @ApiModelProperty("消息类型")
    @NotNull(message = "消息类型不能为空")
    private Byte type;
    /**
     * 监控对象
     */
    @ApiModelProperty("监控对象")
    @NotNull(message = "监控对象不能为空")
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
     * 学校ID
     */
    @ApiModelProperty("学校ID")
    @NotNull(message = "学校ID不能为空")
    private Long schoolId;
    /**
     * 学校CODE
     */
    @ApiModelProperty("学校CODE")
    @NotNull(message = "学校CODE不能为空")
    private String schoolCode;

    /**
     * 学校名称
     */
    @ApiModelProperty("学校名称")
    private String schoolName;
    /**
     * 学生ID
     */
    @ApiModelProperty("学生ID")
    @NotNull(message = "学生ID不能为空")
    private Long studentId;
    /**
     * 学生卡号
     */
    @ApiModelProperty("学生卡号")
    @NotEmpty(message = "学生卡号不能为空")
    private String cardNumber;
    /**
     * 学生名称
     */
    @ApiModelProperty("学生名称")
    private String studentName;

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
     *
     */
    @ApiModelProperty("备注")
    private String remark;

}