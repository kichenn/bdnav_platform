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
 * @create: 2019-04-17 19:10
 **/
@Data
public class UpdateVisitLogsDto implements Serializable {
    private static final long serialVersionUID = 5501087422192873102L;

    /**
     * id
     */
    @ApiModelProperty("id")
    @JsonSerialize(using= ToStringSerializer.class)
    private String id;
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
    /**
     * 访问的URL
     */
    @ApiModelProperty("访问的URL")
    private String url;

    /**
     * 访问时间
     */
    @ApiModelProperty("访问时间")
    private Date createDate;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateDate;

    /**
     * 状态，是否拦截： 0未拦截  1拦截
     */
    @ApiModelProperty("状态，是否拦截： 0未拦截  1拦截")
    private Byte status;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}