package com.bdxh.school.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SchoolDeptVo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("父级部门id")
    private Long parentId;

    @ApiModelProperty("父级部门ids")
    private String parentIds;

    @ApiModelProperty("父级部门names")
    private String parentNames;

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

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("修改时间")
    private Date updateDate;

    @ApiModelProperty("操作人")
    private Long operator;

    @ApiModelProperty("操作人姓名")
    private String operatorName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("子组织")
    private List<SchoolDeptVo> schoolDeptVos;
}
