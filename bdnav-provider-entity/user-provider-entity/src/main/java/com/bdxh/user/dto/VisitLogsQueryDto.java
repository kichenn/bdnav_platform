package com.bdxh.user.dto;

import com.bdxh.common.base.page.Query;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-17 19:10
 **/
public class VisitLogsQueryDto extends Query implements Serializable {
    private static final long serialVersionUID = 2568343079579673024L;


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

    /**
     * 学生姓名
     */
    @ApiModelProperty("学生姓名")
    private String userName;



    /**
     * 访问时间
     */
    @ApiModelProperty("访问时间")
    private Date createDate;

    /**
     * 状态，是否拦截： 0未拦截  1拦截
     */
    @ApiModelProperty("状态，是否拦截： 0未拦截  1拦截")
    private Byte status;


}