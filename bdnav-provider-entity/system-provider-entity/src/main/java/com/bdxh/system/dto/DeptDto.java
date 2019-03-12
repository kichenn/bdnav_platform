package com.bdxh.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeptDto implements Serializable {

    private static final long serialVersionUID = -6350638650907752885L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 父级部门id
     */
    @ApiModelProperty("id")
    private Long parentId;

    /**
     * 父级部门ids
     */
    @ApiModelProperty("父级部门ids")
    private String parentIds;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private Long deptName;

    /**
     * 部门全称
     */
    @ApiModelProperty("部门全称")
    private String deptFullName;

    /**
     * 排序
     */
    @ApiModelProperty("部门排序")
    private Integer sort;

    /**
     * 部门层级
     */
    @ApiModelProperty("部门层级")
    private Byte level;

    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operator;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
