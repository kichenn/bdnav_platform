package com.bdxh.user.dto;

import com.bdxh.common.base.page.Query;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-17 19:10
 **/
@Data
public class VisitLogsQueryDto extends Query implements Serializable {
    private static final long serialVersionUID = 2568343079579673024L;


    /**
     * 学校姓名
     */
    @ApiModelProperty("学校姓名")
    private String schoolName;


    /**
     * 学生姓名
     */
    @ApiModelProperty("学生姓名")
    private String userName;


    /**
     * 状态，是否拦截： 0未拦截  1拦截
     */
    @ApiModelProperty("状态，是否拦截： 1未拦截  2拦截")
    private Byte status;

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