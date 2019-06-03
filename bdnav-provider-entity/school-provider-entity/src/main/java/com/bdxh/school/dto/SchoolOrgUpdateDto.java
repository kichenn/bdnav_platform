package com.bdxh.school.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @description:
 * @author: binzh
 * @create: 2019-06-01 17:05
 **/
public class SchoolOrgUpdateDto {

    /**
     * 主键
     */
    @NotNull(message = "主键ID不能为空")
    @ApiModelProperty("主键ID")
    private Long id;

    /**
     * 组织名称
     */
    private String orgName;


    /**
     * 层级
     */
    private Byte level;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 管理员Id
     */
    private Long manageId;

    /**
     * 管理员微校卡号
     */
    private String manageCardNumber;

    /**
     * 管理员名称
     */
    private String manageName;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 操作人id
     */
    private Long operator;

    /**
     * 操作人
     */
    private String operatorName;

    /**
     * 备注
     */
    private String remark;
}