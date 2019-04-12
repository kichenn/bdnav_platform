package com.bdxh.user.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-11 09:59
 **/
@Data
public class FamilyFenceQueryDto extends Query implements Serializable {
    private static final long serialVersionUID = 1229759301996642082L;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;
    /**
     * 家长号
     */
    @ApiModelProperty("家长号")
    private String cardNumber;

    /**
     * 学生姓名
     */
    @ApiModelProperty("学生姓名")
    private String studentName;

    /**
     * 学生学号
     */
    @ApiModelProperty("学生学号")
    private String studentNumber;

    /**
     * 围栏名称
     */
    @ApiModelProperty("围栏名称")
    private String fenceName;

    /**
     * 状态 1 启用 2 禁用
     */
    @ApiModelProperty("状态 1 启用 2 禁用")
    private Byte status;




}