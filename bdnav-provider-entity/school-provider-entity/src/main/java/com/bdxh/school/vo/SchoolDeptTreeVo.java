package com.bdxh.school.vo;

import com.bdxh.common.helper.tree.bean.TreeBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SchoolDeptTreeVo extends TreeBean {

    @ApiModelProperty("父级部门names")
    private String parentNames;

    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("部门名称")
    private String name;

//
//    @ApiModelProperty("修改时间")
//    private Date updateDate;
//
//    @ApiModelProperty("操作人")
//    private Long operator;
//
//    @ApiModelProperty("操作人姓名")
//    private String operatorName;

    @ApiModelProperty("备注")
    private String remark;
}
