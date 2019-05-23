package com.bdxh.user.dto;

import com.bdxh.common.base.page.Query;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-17 18:58
 **/
@Data
public class FenceAlarmQueryDto extends Query implements Serializable {
    private static final long serialVersionUID = -6943761111879222942L;


    /**
     * 出入类型
     */
    @ApiModelProperty("出入类型")
    private String action;

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
     * 学校CODE
     */
    @ApiModelProperty("学校CODE")
    private String schoolCode;
    /**
     * 学校名称
     */
    @ApiModelProperty("学校名称")
    private String schoolName;
    /**
     * 学生姓名
     */
    @ApiModelProperty("学生姓名")
    private String studentName;

    /**
     * 学生卡号
     */
    @ApiModelProperty("学生卡号")
    private String cardNumber;

    /**
     * 本地围栏表ID不是百度返回的
     */
    @ApiModelProperty("本地围栏表ID")
    private String fenceId;
}