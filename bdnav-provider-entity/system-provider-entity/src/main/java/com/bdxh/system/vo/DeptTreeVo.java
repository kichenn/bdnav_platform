package com.bdxh.system.vo;

import com.bdxh.common.helper.tree.bean.TreeBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



@Data
public class DeptTreeVo extends TreeBean{

    @ApiModelProperty("父级部门ids")
    private String parentIds;

    @ApiModelProperty("部门全称")
    private String deptFullName;


    @ApiModelProperty("备注")
    private String remark;
}
