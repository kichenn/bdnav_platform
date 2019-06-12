package com.bdxh.school.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: binzh
 * @create: 2019-06-01 15:09
 **/
@Data
public class SchoolOrgQueryDto {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("父级组织ID")
    private Long parentId;

    @ApiModelProperty("学校Code")
    private String schoolCode;

    @ApiModelProperty("父级组织名称")
    private String parentNames;

    @ApiModelProperty("父级组织Id")
    private String parentIds;

    @ApiModelProperty("当前组织URL")
    private String thisUrl;

    @ApiModelProperty("学校ID")
    private Long schoolId;

    @ApiModelProperty("组织名称")
    private String orgName;

    @ApiModelProperty("组织类型： 1 学院 2 系 3 专业 4 年级 5 班级,6 行政,7 党团 8 教学 9 后勤 10其他")
    private Byte orgType;

    @ApiModelProperty("管理员ID")
    private Long manageId;

    @ApiModelProperty("管理员名称")
    private String manageName;
}