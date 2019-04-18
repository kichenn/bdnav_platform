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
     * 学生卡号
     */
    @ApiModelProperty("学生卡号")
    private String cardNumber;




}