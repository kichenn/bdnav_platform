package com.bdxh.school.vo;

import com.bdxh.common.helper.tree.bean.TreeBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SchoolClassTreeVo extends TreeBean {


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

    @ApiModelProperty("备注")
    private String remark;
}
