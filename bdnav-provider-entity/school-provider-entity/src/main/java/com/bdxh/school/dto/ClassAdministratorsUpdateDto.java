package com.bdxh.school.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ClassAdministratorsUpdateDto {
    /**
     * 主键
     */
    @ApiModelProperty()
    private Long id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date creatDate;
    /**
     * 类型 1 学院 2 系 3 专业 4 年级 5 班级,6 行政,7 党团 8 教学 9 后勤 10其他
     */
    @ApiModelProperty(value = "类型 1 学院 2 系 3 专业 4 年级 5 班级,6 行政,7 党团 8 教学 9 后勤 10其他")
    private Byte orgType;

    /**
     * 管理员Id
     */
    @ApiModelProperty(value = "管理员Id")
    private Long manageId;

    /**
     * 管理员微校卡号
     */
    @ApiModelProperty(value = "管理员微校卡号")
    private String manageCardNumber;

    /**
     * 管理员名称
     */
    @ApiModelProperty(value = "管理员名称")
    private String manageName;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateDate;

    /**
     * 操作人id
     */
    @ApiModelProperty(value = "操作人id")
    private Long operator;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String operatorName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
