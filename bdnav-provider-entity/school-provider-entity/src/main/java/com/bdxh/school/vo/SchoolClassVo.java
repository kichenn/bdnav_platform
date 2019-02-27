package com.bdxh.school.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SchoolClassVo {


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

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("子院系信息")
    private List<SchoolClassVo> schoolClassVos;
}
