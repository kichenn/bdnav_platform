package com.bdxh.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class TeacherDeptDto implements Serializable {


    private static final long serialVersionUID = -8127703419855697533L;
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 老师工号
     */
    @ApiModelProperty("老师工号")
    private String cardNumber;

    /**
     * 老师id
     */
    @ApiModelProperty("老师id")
    private Long teacherId;

    /**
     * 组织架构id
     */
    @ApiModelProperty("组织架构id")
    private Long deptId;

    /**
     * 组织架构名称
     */
    @ApiModelProperty("组织架构名称")
    private String deptName;

    /**
     * 组织架构ids
     */
    @ApiModelProperty("组织架构ids")
    private String deptIds;

    /**
     * 组织架构names
     */
    @ApiModelProperty("组织架构names")
    private String deptNames;

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
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operator;

    /**
     * 操作人姓名
     */
    @ApiModelProperty("操作人姓名")
    private String operatorName;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}