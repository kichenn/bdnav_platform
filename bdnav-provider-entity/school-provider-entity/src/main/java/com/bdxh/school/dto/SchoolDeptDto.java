package com.bdxh.school.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * @Description: 学校部门新增 dto
 * @Author: Kang
 * @Date: 2019/2/27 16:09
 */
@Data
public class SchoolDeptDto  {


    @ApiModelProperty("父级部门id")
    private Long parentId;

    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("部门名称")
    private String name;

    @ApiModelProperty("层级")
    private Byte level;

    @ApiModelProperty("排序")
    private Integer sort;

//    @ApiModelProperty("创建时间")
//    private Date createDate;
//
//    @ApiModelProperty("修改时间")
//    private Date updateDate;
//
    @ApiModelProperty("操作人")
    private Long operator;

    @ApiModelProperty("操作人姓名")
    private String operatorName;

    @ApiModelProperty("备注")
    private String remark;

}
