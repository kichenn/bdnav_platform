package com.bdxh.school.dto;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import java.util.Date;

/**
 * @description:
 * @author: binzh
 * @create: 2019-06-01 17:05
 **/
@Data
public class SchoolOrgUpdateDto {

    /**
     * 主键
     */
    @ApiModelProperty()
    private Long id;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id")
    private Long parentId;

    /**
     * 父级ids
     */
    @ApiModelProperty(value = "父级ids")
    private String parentIds;

    /**
     * 父级names
     */
    @ApiModelProperty(value = "父级names")
    private String parentNames;

    /**
     * 组织架构路径
     */
    @ApiModelProperty(value = "组织架构路径")
    private String thisUrl;

    /**
     * 学校id
     */
    @ApiModelProperty(value = "学校id")
    private Long schoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty(value = "学校编码")
    private String schoolCode;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    private String orgName;

    /**
     * 类型 1 学院 2 系 3 专业 4 年级 5 班级,6 行政,7 党团 8 教学 9 后勤 10其他
     */
    @ApiModelProperty(value = "类型 1 学院 2 系 3 专业 4 年级 5 班级,6 行政,7 党团 8 教学 9 后勤 10其他")
    private Byte orgType;

    /**
     * 层级
     */
    @ApiModelProperty(value = "层级")
    private Byte level;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

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