package com.bdxh.school.vo;

import com.bdxh.common.helper.tree.bean.TreeBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @description:
 * @author: binzh
 * @create: 2019-06-03 09:41
 **/
@Data
public class SchoolOrgTreeVo extends TreeBean {

    @ApiModelProperty("父级names")
    private String parentNames;

    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("班级名称")
    private String name;

    @ApiModelProperty("类型:1 学院,2 系,3 专业,4 年级,5 班级,6 行政,7 党团,8 教学,9 后勤,10其他")
    private Byte orgType;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否展开直子节点")
    private Boolean expand=Boolean.TRUE;
}