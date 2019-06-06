package com.bdxh.school.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 学校院系关系修改dto
 * @Author: Kang
 * @Date: 2019/2/27 16:06
 */
@Data
public class SchoolClassModifyDto {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("父级id")
    private Long parentId;

    @ApiModelProperty("父级ids")
    private String parentIds;

    @ApiModelProperty("父级names")
    private String parentNames;

    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("班级名称")
    private String name;

    @ApiModelProperty("类型 1 学院 2 系 3 专业 4 年级 5 班级")
    private Byte type;

    @ApiModelProperty("层级")
    private Byte level;

    @ApiModelProperty("排序")
    private Integer sort;

//    @ApiModelProperty("创建时间") 
//    private Date createDate;
//
    @ApiModelProperty("修改时间")
    private Date updateDate;
//
    @ApiModelProperty("操作人")
    private Long operator;

    @ApiModelProperty("操作人姓名")
    private String operatorName;

    @ApiModelProperty("备注")
    private String remark;

}
